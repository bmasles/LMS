package com.smoothstack.lms.borrowermicroservice.database.sql;

import com.smoothstack.lms.borrowermicroservice.Debug;
import com.smoothstack.lms.borrowermicroservice.context.util.EntityClassInfo;

import java.util.Objects;
import java.util.function.Function;

public class LazyFetch<T> {

    private T object = null;
    private Integer reference = 0;
    private Function<Integer, T> fetchFunction;

    public LazyFetch(Integer reference, Function<Integer, T> fetchFunction) {
        setReference(reference);
        setFetchFunction(fetchFunction);
    }

    public LazyFetch(T object, Function<Integer, T> fetchFunction) {
        set(object);
        setFetchFunction(fetchFunction);
    }

    public LazyFetch(Function<Integer, T> fetchFunction) {
        setFetchFunction(fetchFunction);
    }

    T get() {

        if (object != null) {
            return object;
        } if (reference != 0) {
            set(fetchFunction.apply(reference));
            return object;
        }

        return null;
    }

    void set(T entity) {

        object = entity;

        if (object != null) {
            reference =  EntityClassInfo.of(object.getClass()).getIdField().get(object);
        }
        else {
            setReference(0);
        }

    }

    public Integer getReference() {

        return reference;

    }

    public void setReference(Integer reference) {
        this.reference = reference==null?0:reference;

        //Invalidate the object
        object = null;
    }

    public Function<Integer, T> getFetchFunction() {
        return fetchFunction;
    }

    public void setFetchFunction(Function<Integer, T> fetchFunction) {
        Objects.requireNonNull(fetchFunction);

        this.fetchFunction = fetchFunction;
    }

    @Override
    public String toString() {
        return String.format("%d:%s", reference, object.toString());
    }

    @Override
    public int hashCode() {
        return object.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return object.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        LazyFetch<T> newObject = null;
        try {
            newObject =  (LazyFetch<T>) this.getClass().newInstance();
            newObject.object = this.object;
            newObject.reference = this.reference;
            newObject.fetchFunction = this.fetchFunction;
        } catch (InstantiationException | IllegalAccessException e) {
            Debug.printException(e);
            throw new CloneNotSupportedException(e.getMessage());
        }


        return newObject;
    }
}
