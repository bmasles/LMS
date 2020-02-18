package com.smoothstack.lms.borrowermicroservice.context.util;

import com.smoothstack.lms.borrowermicroservice.Debug;
import com.smoothstack.lms.borrowermicroservice.context.annotation.Id;
import com.smoothstack.lms.borrowermicroservice.context.annotation.JoinColumn;
import com.smoothstack.lms.borrowermicroservice.context.annotation.Table;
import com.smoothstack.lms.borrowermicroservice.database.sql.IntegerList;
import com.smoothstack.lms.borrowermicroservice.database.sql.RelationToOne;
import com.smoothstack.lms.borrowermicroservice.database.sql.SQLDataMap;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class EntityClassInfo extends ClassInfo{

    public EntityClassInfo(Class clazz) {
        super(clazz);
    }

    public static EntityClassInfo of(Class clazz) {
        return new EntityClassInfo(clazz);
    }

    public List<EntityFieldInfo> getIdFields() {
        Map<Integer, EntityFieldInfo> idFieldsMap = new HashMap<>();
        for (Field field : getDeclaredFieldsAsList(getClazz())) {
            EntityFieldInfo fieldInfo = EntityFieldInfo.of(field);
            Optional<Id> id = fieldInfo.getAnnotation(Id.class);
            if (id.isPresent()) {
                int idIndex = id.get().value();

                if (idFieldsMap.containsKey(idIndex)) {
                    Debug.println("Duplicated ID, must use @Id(#number) for multi id Entity;");
                }
                else {
                    idFieldsMap.put(idIndex, fieldInfo);
                }
            }
        }

        return idFieldsMap.keySet().stream().sorted().map(idFieldsMap::get).collect(Collectors.toList());
    }

    public String getTableName() {
        return getAnnotation(Table.class).map(Table::name).orElse(getClazz().getSimpleName());
    }

    public IntegerList getIds(Object object) {
        return new IntegerList(getIdFields().stream().map(
                idf -> {
                        if (object == null)
                            return 0;

                        Object obj = idf.get(object);

                        if (obj == null)
                            return 0;
                        else if (obj instanceof Number)
                            return (Integer) obj;
                        else
                            return Integer.valueOf(obj.toString());

                        }).collect(Collectors.toList()));
    }

    public SQLDataMap getSQLDataMap(Object object) {
       Debug.printMethodInfo();
       Debug.printf(" with object=%s\n", object);
       return (SQLDataMap) getDeclaredFieldsAsList(getClazz()).stream()
               .map(EntityFieldInfo::new)
               .filter(efi-> efi.get(object) != null)
               .collect(Collectors.toMap(
                     EntityFieldInfo::getColumnName
                    , efi->efi.getColumnValue(object)
                    , SQLDataMap::onDuplicatedKeyDrop
                    , SQLDataMap::new));

    }

    public EntityFieldInfo getFieldInfo(String name) {
        return EntityFieldInfo.of(getDeclaredField(name));
    }
}
