package com.smoothstack.lms.borrowermicroservice.database.sql;

import com.smoothstack.lms.borrowermicroservice.Debug;

import java.util.Optional;
import java.util.function.Function;

public class RelationToOne<T> extends CachedOne<T> {

    public RelationToOne(Function<IntegerList, T> fetchFunction) {
        super(fetchFunction);
        Debug.printMethodInfo();
    }

    public static <R> RelationToOne<R> of(Function<IntegerList, R> fetchFunction) {
        Debug.printMethodInfo();
        return  of(null, fetchFunction);
    }

    public static <R> RelationToOne<R> of(Object value, Function<IntegerList, R> fetchFunction) {
        RelationToOne<R> relation = new RelationToOne<>(fetchFunction);
        if (!(value instanceof Class) && (value != null))
            relation.set((R) value);
        return  relation;
    }

    public Object getFieldValue(String fieldName) {
        Optional<T> optionalTarget = get();
        if (optionalTarget.isPresent()) {
            T target = optionalTarget.get();

            try {
                boolean access = target.getClass().getField(fieldName).isAccessible();
                target.getClass().getField(fieldName).setAccessible(true);
                Object value = target.getClass().getField(fieldName).get(target);
                target.getClass().getField(fieldName).setAccessible(access);
                return value;
            } catch (IllegalAccessException | NoSuchFieldException e) {
                Debug.printException(e);
            }
        }
        return null;
    }


}
