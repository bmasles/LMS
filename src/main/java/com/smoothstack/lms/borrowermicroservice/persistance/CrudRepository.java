package com.smoothstack.lms.borrowermicroservice.persistance;

import com.smoothstack.lms.borrowermicroservice.Debug;
import com.smoothstack.lms.borrowermicroservice.context.annotation.ToOne;
import com.smoothstack.lms.borrowermicroservice.context.util.ClassInfo;
import com.smoothstack.lms.borrowermicroservice.context.util.EntityClassInfo;
import com.smoothstack.lms.borrowermicroservice.context.util.EntityFieldInfo;
import com.smoothstack.lms.borrowermicroservice.database.ConnectionFactory;
import com.smoothstack.lms.borrowermicroservice.database.DataAccess;
import com.smoothstack.lms.borrowermicroservice.database.sql.CachedOne;
import com.smoothstack.lms.borrowermicroservice.database.sql.IntegerList;
import com.smoothstack.lms.borrowermicroservice.database.sql.RelationToOne;
import com.smoothstack.lms.borrowermicroservice.database.sql.SQLDataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class CrudRepository<T>  {

    private ConnectionFactory connectionFactory;
    private Class<T> entityClass;

    public CrudRepository() {
    }

    public CrudRepository(Class<T> entityClass) {
        this.entityClass =  entityClass;
    }

    public void init(Class entityClass) {
        this.entityClass =  entityClass;
    }
    public static <R> CrudRepository<R> of(Class<R> entityClass) {
        return new CrudRepository<R>(entityClass);
    }

    public ConnectionFactory getConnectionFactory() {


        return connectionFactory==null?connectionFactory=new ConnectionFactory("config.xml"):connectionFactory;
    }

    @Autowired
    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public static <R> List<R> findAll(Connection connection, Class<R> entityClass) {

        try {
            String tableName = EntityClassInfo.of(entityClass).getTableName();

            DataAccess dataAccess = new DataAccess(connection);

            List<R> resultList = dataAccess.executeQuery("SELECT * FROM ? ",
                    resultSet -> CrudRepository.extractValue(entityClass, resultSet), tableName);

            if (resultList.size() == 0)
                return Collections.emptyList();

            return resultList;

        } catch (SQLException e) {
            Debug.printException(e);
        }

        return Collections.emptyList();
    }

    public List<T> findAll() {

        Debug.printMethodInfo();
        try (Connection connection = connectionFactory.getConnection()) {

            return findAll(connection, entityClass);

        } catch (SQLException e) {
            Debug.printException(e);
        }

        return Collections.emptyList();
    }

    public static <R> Optional<R> findByIds(Connection connection, Class<R> entityClass, Integer... id) {
        Debug.printMethodInfo();
        try  {

            String tableName = EntityClassInfo.of(entityClass).getTableName();
            List<EntityFieldInfo> idFields = EntityClassInfo.of(entityClass).getIdFields();
            if (id == null || idFields.size() != id.length)
                throw new NullPointerException("findById(id1, id2, ...): Number of ID mismatched!, require "+idFields.size() );

            DataAccess dataAccess = new DataAccess(connection);

            String[] idFieldsRef = new String[idFields.size()];

            SQLDataMap dataMap = new SQLDataMap();

            int i = 0;
            for (EntityFieldInfo idField : idFields) {
                idFieldsRef[i] = String.format("%s = ?", idField.getColumnName());
                dataMap.put(idField.getColumnName(), id[i]);
                ++i;
            }
            List<R> resultList =  dataAccess.executeQuery(
                    String.format("SELECT * FROM %s WHERE %s",tableName, String.join(" AND ", idFieldsRef)),
                    resultSet -> CrudRepository.extractValue(entityClass, resultSet), dataMap);

            if (resultList.size() > 1) {
                throw new SQLDataException("Expected single result, got "+resultList.size());
            } else if (resultList.size() == 0 )
                throw new SQLDataException("Lookup "+ tableName + ", not found id='"+ Arrays.toString(id) +"'");

            Debug.println("DONE findByIds");
            return Optional.of(resultList.get(0));

        } catch (NullPointerException | SQLException e) {
            Debug.printException(e);
        }

        return Optional.empty();
    }

    public Optional<T> findByIds(Integer... id) {
        Debug.printMethodInfo();


        try (Connection connection = getConnectionFactory().getConnection()) {
            return findByIds(connection, entityClass, id);
        } catch (NullPointerException | SQLException e) {
            Debug.printException(e);
        }
        return Optional.empty();
    }


    public static <R> Optional<R> deleteById(Connection connection, Class<R> entityClass, Integer... id) {

        AtomicReference<R> atomicReference = new AtomicReference<>();
        findByIds(connection, entityClass, id).ifPresent(entity -> {
        String tableName = EntityClassInfo.of(entityClass).getTableName();
        List<EntityFieldInfo> idFields = EntityClassInfo.of(entityClass).getIdFields();
        if (id == null || idFields.size() != id.length)
            throw new NullPointerException("Number of ID mismatched!, require "+idFields.size() );

        DataAccess dataAccess = new DataAccess(connection);

        String[] idFieldsRef = new String[idFields.size()];

        SQLDataMap dataMap = new SQLDataMap();

        dataMap.put("tableName", tableName);

        int i = 0;
        for (EntityFieldInfo idField : idFields) {
            idFieldsRef[i] = String.format("%s = ?", idField.getColumnName());
            dataMap.put(idField.getColumnName(), id[i]);
            ++i;
        }
        try {
            dataAccess.executeUpdate(
                    String.format("DELETE FROM ? WHERE %s ",String.join(" AND ", idFieldsRef)),
                    dataMap);
            atomicReference.set(entity);
        } catch (SQLException e) {
            Debug.printException(e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                Debug.printException(ex);
            }
        }

        });

        return Optional.ofNullable(atomicReference.get());
    }

    public Optional<T> deleteById(Integer id) {
        Debug.printMethodInfo();
        try (Connection connection = connectionFactory.getConnection()) {
            Objects.requireNonNull(connectionFactory);
            Objects.requireNonNull(id);

            return deleteById(connection, entityClass, id);

        } catch (NullPointerException | SQLException e) {
            Debug.printException(e);
        }

        return Optional.empty();
    }

    public static <R> Optional<R> insert(Connection connection, Class<R> entityClass, R entity) {


        String tableName = EntityClassInfo.of(entityClass).getTableName();

        DataAccess dataAccess = new DataAccess(connection);

        SQLDataMap entityData = EntityClassInfo.of(entityClass).getSQLDataMap(entity);

        SQLDataMap dataMap = new SQLDataMap();
        dataMap.putAll(entityData);

        List<EntityFieldInfo> idFields = EntityClassInfo.of(entityClass).getIdFields();

        try {

            PreparedStatement preparedStatement =
                    dataAccess.executeUpdate(String.format("INSERT INTO %s (%s) VALUES (%s) "
                                ,tableName
                                ,entityData.keySetCsv(), entityData.valueReferenceCsv())
                                ,idFields.size()>0?Statement.RETURN_GENERATED_KEYS:Statement.NO_GENERATED_KEYS
                                ,dataMap);


            ResultSet keys = preparedStatement.getGeneratedKeys();

            if (keys.next()) {
                for (EntityFieldInfo idField : idFields) {
                    Integer generatedId = keys.getInt(1);
                    Debug.printf("  ID Generated %s -> %d\n", idField.getColumnName(), generatedId);
                    idField.set(entity, generatedId);
                }

            }


            return Optional.of(entity);
        } catch (SQLIntegrityConstraintViolationException e) {
            Debug.println("HINT!: Did you try to save a new record which it's related entity was not in database yet or has id = 0 or null)?");
            Debug.printException(e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                Debug.printException(ex);
            }
        }

        catch (SQLException e) {
            Debug.printException(e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                Debug.printException(ex);
            }
        }

        return Optional.empty();
    }

    public static <R> Optional<R> update(Connection connection, Class<R> entityClass, R entity) {


        String tableName = EntityClassInfo.of(entityClass).getTableName();
        List<EntityFieldInfo> idFields = EntityClassInfo.of(entityClass).getIdFields();
        DataAccess dataAccess = new DataAccess(connection);

        String[] idFieldsRef = new String[idFields.size()];
        SQLDataMap idMap = new SQLDataMap();
        int i = 0;
        for (EntityFieldInfo idField : idFields) {
            idFieldsRef[i] = String.format("%s = ?", idField.getColumnName());
            idMap.put(String.format("%s.%s", tableName,idField.getColumnName()), idField.get(entity));
            ++i;
        }

        SQLDataMap entityData = EntityClassInfo.of(entityClass).getSQLDataMap(entity);

        SQLDataMap dataMap = new SQLDataMap();

        dataMap.putAll(entityData);
        dataMap.putAll(idMap);

        try {

            PreparedStatement preparedStatement =
                    dataAccess.executeUpdate(String.format("UPDATE %s SET %s WHERE %s"
                            ,tableName
                            ,entityData.keyValueReferenceCsv()
                            ,idMap.keyValueReferenceCsv()
                            )
                            ,dataMap);

            return Optional.of(entity);
        } catch (SQLException e) {
            Debug.printException(e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                Debug.printException(ex);
            }
        }

        return Optional.empty();
    }

    public Optional<T> save(T entity) {
        Debug.printMethodInfo();
        try (Connection connection = connectionFactory.getConnection()) {

            Integer[] ids = EntityClassInfo.of(entityClass).getIds(entity).toArray(new Integer[0]);
            Optional<T> object;
            if (findByIds(connection, entityClass, ids).isPresent())
                object = update(connection, entityClass, entity);
            else
                object = insert(connection, entityClass, entity);
            connection.commit();
            Debug.println("Committed!");
            return object;
        } catch (NullPointerException | SQLException e) {
            Debug.printException(e);
        }

        return Optional.empty();
    }

    /**
     * Extract an Object from resultSet
     * <P>Support only field-base access</P>
     * @param resultSet the resultSet containing an object
     * @param <R> Object type
     * @return Object extracted from ResultSet
     * @author Krissada Jindanupajit
     */
    public static <R> R extractValue(Class<R> entityClass, ResultSet resultSet) {
        Debug.printMethodInfo();
        Objects.requireNonNull(entityClass);
        R object = null;

        try {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            Map<String, EntityFieldInfo> columnMap = ClassInfo.of(entityClass).getDeclaredFields().stream()
                    .map(EntityFieldInfo::new)
                    .collect(Collectors.toMap(EntityFieldInfo::getColumnName, Function.identity()));

            object = entityClass.newInstance();

            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                String columnName = resultSetMetaData.getColumnName(i);
                EntityFieldInfo fieldInfo = columnMap.get(columnName);
                Debug.printf("Extract %s:\n", columnName);
                if (fieldInfo != null) {
                    Field field = fieldInfo.getField();
                    boolean accessible = field.isAccessible();
                    field.setAccessible(true);

                    Optional<ToOne> annotationToOne = fieldInfo.getAnnotation(ToOne.class);

//                    if (fieldInfo.getField().getType().isAssignableFrom(RelationToOne.class)) {
//
//                        Optional<ToOne> oneToOne = fieldInfo.getAnnotation(ToOne.class);
//                        //TODO: Optional.get null check !!!
//                        CrudRepository repo = CrudRepositoryFactory.getRepository(oneToOne.get().value());
//                        Debug.printf("Relation %s->%s : %s.class\n", columnName, fieldInfo.getField().getName(), oneToOne.get().value().getSimpleName());
//                        if (repo != null) {
//                            RelationToOne r ;
//                            field.set(object, r = repo.relationToId(resultSet.getInt(columnName)));
//
//                            Debug.printf("Verify %s\n", r.getReference());
//                        }
//                        else
//                            Debug.printf("Repo for %s is null", oneToOne.get().value());
//                    }
//                    else

                    if (annotationToOne.isPresent()) {
                        Class clazz = annotationToOne.get().value();
                        Object obj = clazz.newInstance();
                        List<EntityFieldInfo> idFieldInfo = EntityClassInfo.of(clazz).getIdFields();
                        if (idFieldInfo.size() > 0)
                        idFieldInfo.get(0).set(obj, resultSet.getObject(columnName, idFieldInfo.get(0).getField().getType()));
                        field.set(object, obj);
                    }
                    else {
                        Debug.printf("%s->%s : %s\n",columnName, fieldInfo.getField().getName(), fieldInfo.getField().getType());

                        field.set(object,
                                resultSet.getObject(columnName, fieldInfo.getField().getType()));
                    }
                    field.setAccessible(accessible);
                }
                else {
                    Debug.println("FieldInfo is null");
                }
            }


        } catch (IllegalAccessException | InstantiationException | SQLException e) {
            Debug.printException(e);
        }

        Debug.println("::: Done Extraction :::");
        return object;
    }

    protected T extractValue(ResultSet resultSet) {
        return extractValue(entityClass, resultSet);
    }



    public Function<IntegerList, T> getFetchFunction() {
        Debug.printMethodInfo();
        return (idList) -> {
            Debug.println("CrudRepository.fetchFunction:");
            Debug.printf("findByIds(%s)\n", idList.toString());

            return this.findByIds(idList.toArray(new Integer[0])).orElse(null);
        };
    }

    public CachedOne<T> cacheOfId(Integer... ids) {
        Debug.printf("Create cache to %s\n", Arrays.toString(ids));
        CachedOne<T> cache = new CachedOne<>(this.getFetchFunction());
        IntegerList idList = IntegerList.of(ids);
        Debug.printf("Saving %s\n", idList);
        cache.setReference(idList);
        Debug.printf("Return cache %s\n", cache);
        return cache;
    }

    public RelationToOne<T> relationToId(Integer... ids) {
        Debug.printf("Create relation to %s\n", Arrays.toString(ids));
        RelationToOne<T> relation = new RelationToOne<>(this.getFetchFunction());
        IntegerList idList = IntegerList.of(ids);
        Debug.printf("Saving %s\n", idList);
        relation.setReference(idList);
        Debug.printf("Return relation %s\n", relation);
        return relation;
    }

}
