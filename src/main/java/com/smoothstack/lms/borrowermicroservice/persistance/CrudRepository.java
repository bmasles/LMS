package com.smoothstack.lms.borrowermicroservice.persistance;

import com.smoothstack.lms.borrowermicroservice.Debug;
import com.smoothstack.lms.borrowermicroservice.context.util.ClassInfo;
import com.smoothstack.lms.borrowermicroservice.context.util.EntityClassInfo;
import com.smoothstack.lms.borrowermicroservice.context.util.EntityFieldInfo;
import com.smoothstack.lms.borrowermicroservice.context.util.FieldInfo;
import com.smoothstack.lms.borrowermicroservice.database.ConnectionFactory;
import com.smoothstack.lms.borrowermicroservice.database.DataAccess;
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

    private String tableName;
    private String idFieldName;

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
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

    public static <R> Optional<R> findById(Connection connection, Class<R> entityClass, Integer id) {
        Debug.printMethodInfo();
        try  {

            String tableName = EntityClassInfo.of(entityClass).getTableName();
            String idFieldName = EntityClassInfo.of(entityClass).getIdField().getColumnName();
            Objects.requireNonNull(id);

            DataAccess dataAccess = new DataAccess(connection);

            List<R> resultList =  dataAccess.executeQuery("SELECT * FROM ? WHERE ? = ? ",
                    resultSet -> CrudRepository.extractValue(entityClass, resultSet), tableName, idFieldName, id);

            if (resultList.size() > 1) {
                throw new SQLDataException("Expected single result, got "+resultList.size());
            } else if (resultList.size() == 0 )
                throw new SQLDataException("Lookup "+ tableName + ", not found id='"+id.toString()+"'");

            return Optional.of(resultList.get(0));

        } catch (NullPointerException | SQLException e) {
            Debug.printException(e);
        }

        return Optional.empty();
    }

    public Optional<T> findById(Integer id) {
        Debug.printMethodInfo();
        try (Connection connection = connectionFactory.getConnection()) {

            return findById(connection, entityClass, id);
        } catch (NullPointerException | SQLException e) {
            Debug.printException(e);
        }
        return Optional.empty();
    }

    public static <R> Optional<R> deleteById(Connection connection, Class<R> entityClass, Integer id) {

        AtomicReference<R> atomicReference = new AtomicReference<>();
        findById(connection, entityClass, id).ifPresent(entity -> {
        String tableName = EntityClassInfo.of(entityClass).getTableName();
        String idFieldName = EntityClassInfo.of(entityClass).getIdField().getColumnName();
        DataAccess dataAccess = new DataAccess(connection);
        try {
            dataAccess.executeUpdate("DELETE FROM ? WHERE ? = ? ",
                    tableName, idFieldName, id);
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
            Objects.requireNonNull(tableName);
            Objects.requireNonNull(idFieldName);
            Objects.requireNonNull(id);

            return deleteById(connection, entityClass, id);

        } catch (NullPointerException | SQLException e) {
            Debug.printException(e);
        }

        return Optional.empty();
    }

    public static <R> Optional<R> insert(Connection connection, Class<R> entityClass, R entity) {


        String tableName = EntityClassInfo.of(entityClass).getTableName();
        String idFieldName = EntityClassInfo.of(entityClass).getIdField().getColumnName();
        DataAccess dataAccess = new DataAccess(connection);


        SQLDataMap entityData = EntityClassInfo.of(entityClass).getSQLDataMap(entity);

        SQLDataMap dataMap = new SQLDataMap();
        dataMap.put("tableName",tableName);
        dataMap.putAll(entityData);

        try {

            PreparedStatement preparedStatement =
                    dataAccess.executeUpdate(String.format("INSERT INTO ? (%s) VALUES %s "
                                ,entityData.keySetCsv(), entityData.valueReferenceCsv())
                                ,dataMap);


            ResultSet keys = preparedStatement.getGeneratedKeys();

            if (keys.next())
                EntityClassInfo.of(entityClass).getIdField().set(entity,keys.getInt(1) );

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
            EntityFieldInfo fieldInfo;
            if (null != (fieldInfo = EntityClassInfo.of(entity.getClass()).getIdField())) {

                Integer id = fieldInfo.get(entity);

//                if (findById(connection, entityClass, id).isPresent())
//                    return update(connection, entityClass, entity);
//                else
//                    return insert(connection, entityClass, entity);
            }

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
            Map<String, FieldInfo> columnMap = ClassInfo.of(entityClass).getDeclaredFields().stream()
                    .map(EntityFieldInfo::new)
                    .collect(Collectors.toMap(EntityFieldInfo::getColumnName, Function.identity()));

            object = entityClass.newInstance();

            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                String columnName = resultSetMetaData.getColumnName(i);
                FieldInfo fieldInfo = columnMap.get(columnName);
                if (fieldInfo != null) {
                    Field field = fieldInfo.getField();
                    boolean accessible = field.isAccessible();
                    field.setAccessible(true);
                    field.set(object,
                        resultSet.getObject(columnName, fieldInfo.getField().getType()));
                    field.setAccessible(accessible);
                }
            }


        } catch (IllegalAccessException | InstantiationException | SQLException e) {
            Debug.printException(e);
        }

        return object;
    }

    protected T extractValue(ResultSet resultSet) {
        return extractValue(entityClass, resultSet);
    }



    public Function<Integer, T> getFetchFunction() {

        return (id) -> this.findById(id).orElse(null);
    }


}
