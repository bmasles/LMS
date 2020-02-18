package com.smoothstack.lms.borrowermicroservice.context.util;

import com.smoothstack.lms.borrowermicroservice.Debug;
import com.smoothstack.lms.borrowermicroservice.context.annotation.Column;
import com.smoothstack.lms.borrowermicroservice.context.annotation.Id;
import com.smoothstack.lms.borrowermicroservice.context.annotation.JoinColumn;
import com.smoothstack.lms.borrowermicroservice.database.sql.RelationToOne;

import java.lang.reflect.Field;
import java.util.Optional;

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

        return  getAnnotation(Column.class).map(Column::name).orElse(
                    getAnnotation(JoinColumn.class).map(JoinColumn::name).orElse(
                        getField().getName()
                    )
                );
    }

    public <O, R> R getColumnValue(O object) {
        Debug.printMethodInfo();
        Debug.printf(" Get %s from %s on %s\n",  getColumnName(), getField().getName(), object);
            Object value = get(object);
            if (getField().getType().isAssignableFrom(RelationToOne.class)) {
                // value is entity
                Optional<JoinColumn> joinColumn = getAnnotation(JoinColumn.class);

                if (joinColumn.isPresent()) {
                    String referencedFieldName = joinColumn.get().referencedFieldName();
                    Debug.printf(" Map %s -> %s on %s\n",  getColumnName(), referencedFieldName, value);
                    EntityClassInfo targetEci = EntityClassInfo.of(value.getClass());
                    Object actualValue = targetEci.getFieldInfo(referencedFieldName).get(value);
                    Debug.printf("       actualValue -> '%s'\n", actualValue==null?"null":actualValue);
                    return (R) (actualValue==null?0:actualValue);
                }
                else {
                    Debug.printf("Relation required @JoinColumn(referencedFieldName=\"fieldName\", ...) \n");
                    throw new NullPointerException("@JoinColumn(referencedFieldName=\"fieldName\", ...) missing.");
                }
            }

            Debug.printf("       value -> '%s'\n", value==null?"null":value);
            return (R) value;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T, R> R get(T entity) {
        Object fieldValue = super.get(entity);

        if (fieldValue instanceof RelationToOne) {
            Optional<R> cachedValue =  ((RelationToOne) fieldValue).get();
            return cachedValue.orElse(null);
        }
        else {
            return (R) fieldValue;
        }

    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> void set(T entity, Object value) {
        Object fieldValue = super.get(entity);

        if (fieldValue instanceof RelationToOne) {
            ((RelationToOne) fieldValue).set(value);

        }
        else {
            super.set(entity, value);
        }
    }
}
