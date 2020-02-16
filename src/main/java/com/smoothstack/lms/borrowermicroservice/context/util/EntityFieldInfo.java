package com.smoothstack.lms.borrowermicroservice.context.util;

import com.smoothstack.lms.borrowermicroservice.context.annotation.Column;
import com.smoothstack.lms.borrowermicroservice.context.annotation.Id;
import com.smoothstack.lms.borrowermicroservice.context.annotation.OneToOne;
import com.smoothstack.lms.borrowermicroservice.database.sql.LazyFetch;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.function.Function;

public class EntityFieldInfo extends FieldInfo {

    public EntityFieldInfo(Field field) {
        super(field);
    }

    public static EntityFieldInfo of(Field field) {
        return new EntityFieldInfo(field);
    }

    public boolean isId() {
        return getField().isAnnotationPresent(Id.class);
    }

    public String getColumnName() {
        return  getAnnotation(Column.class).map(Column::name).orElse(getField().getName());
    }

}
