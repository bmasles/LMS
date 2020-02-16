package com.smoothstack.lms.borrowermicroservice.context.util;

import com.smoothstack.lms.borrowermicroservice.Debug;
import com.smoothstack.lms.borrowermicroservice.context.annotation.Column;
import com.smoothstack.lms.borrowermicroservice.context.annotation.Id;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Optional;

public class FieldInfo {

    private final Field field;

    public FieldInfo(Field field) {
        Objects.requireNonNull(field);
        this.field = field;
    }

    public static FieldInfo of(Field field) {
        return new FieldInfo(field);
    }

    public static <T extends  Annotation> Optional<T> getAnnotaton(Field field, Class<T> annotationClass) {
        try {
            if (field.isAnnotationPresent(annotationClass)) {
                T annotation = field.getAnnotation(annotationClass);
                return Optional.of(annotation);
            }
        } catch (Exception e) {
            Debug.printException(e);
        }

        return Optional.empty();
    }

    public <T extends  Annotation> Optional<T> getAnnotation(Class<T> annotationClass) {
        return getAnnotaton(field, annotationClass);
    }

    @SuppressWarnings("unchecked")
    public <T, R> R get(T obj) {
        try {
            boolean access = getField().isAccessible();
            getField().setAccessible(true);
            Object object = getField().get(obj);
            getField().setAccessible(access);



            return (R) object;
        } catch (IllegalAccessException e) {
            Debug.printException(e);
        }
        return null;
    }

    public <T> void set(T obj, Object value) {
        try {
            boolean access = getField().isAccessible();
            getField().setAccessible(true);
            getField().set(obj, value);
            getField().setAccessible(access);
        } catch (IllegalAccessException e) {
            Debug.printException(e);
        }
    }

    public Field getField() {
        return field;
    }
}
