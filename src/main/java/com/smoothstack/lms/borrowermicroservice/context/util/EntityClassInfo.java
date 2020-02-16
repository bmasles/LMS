package com.smoothstack.lms.borrowermicroservice.context.util;

import com.smoothstack.lms.borrowermicroservice.context.annotation.Table;
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

    public EntityFieldInfo getIdField() {
        for (Field field : getDeclaredFieldsAsList(getClazz())) {
            EntityFieldInfo fieldInfo = EntityFieldInfo.of(field);
            if (fieldInfo.isId()) return fieldInfo;
        }

        return null;
    }

    public String getTableName() {
        return getAnnotation(Table.class).map(Table::name).orElse(getClazz().getSimpleName());
    }


    public SQLDataMap getSQLDataMap(Object object) {
       return (SQLDataMap) getDeclaredFieldsAsList(getClazz()).stream().map(EntityFieldInfo::new)
               .collect(Collectors.toMap(EntityFieldInfo::getColumnName, efi->efi.get(object)
                       , (v1,v2)->{return v2;},LinkedHashMap::new));

    }
}
