package com.smoothstack.lms.borrowermicroservice.depreciated.context.util;

import com.smoothstack.lms.borrowermicroservice.Debug;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public class ClassInfo {
    private final Class clazz;

    public ClassInfo(Class clazz) {
        Objects.requireNonNull(clazz);
        this.clazz = clazz;
    }

    public static ClassInfo of(Class clazz) {
        return new ClassInfo(clazz);
    }

    @SuppressWarnings("unchecked")
    public static <A extends Annotation> Optional<A> getAnnotaton(Class clazz, Class<A> annotationClass) {
        Objects.requireNonNull(clazz);
        Objects.requireNonNull(annotationClass);
        try {
            if (clazz.isAnnotationPresent(annotationClass)) {
                A annotation = (A) clazz.getAnnotation(annotationClass);
                return Optional.of(annotation);
            }
        } catch (Exception e) {
            Debug.printException(e);
        }

        return Optional.empty();
    }

    public <T extends  Annotation> Optional<T> getAnnotation(Class<T> annotationClass) {
        return getAnnotaton(clazz, annotationClass);
    }

    public Class getClazz() {
        return clazz;
    }

    public static List<Field> getDeclaredFieldsAsList(Class clazz) {
        Objects.requireNonNull(clazz);
        return new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));

    }

    public List<Field> getDeclaredFields() {
        return getDeclaredFieldsAsList(clazz);
    }

    public Field getDeclaredField(String name) {
        try {
            return clazz.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            Debug.printException(e);
            return null;
        }

    }


}
