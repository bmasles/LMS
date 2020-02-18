package com.smoothstack.lms.borrowermicroservice.database.sql;

import com.smoothstack.lms.borrowermicroservice.Debug;
import com.smoothstack.lms.borrowermicroservice.context.util.EntityClassInfo;

import java.util.Objects;
import java.util.function.Function;

public class LazyFetch<T> {

    private T object = null;
    private IntegerList reference = IntegerList.emptyList();
    private Function<IntegerList, T> fetchFunction;

    public LazyFetch(IntegerList reference, Function<IntegerList, T> fetchFunction) {
        Debug.printMethodInfo();
        setReference(reference);
        setFetchFunction(fetchFunction);
    }

    public LazyFetch(T object, Function<IntegerList, T> fetchFunction) {
        Debug.printMethodInfo();
        set(object);
        setFetchFunction(fetchFunction);
    }

    public LazyFetch(Function<IntegerList, T> fetchFunction) {
        Debug.printMethodInfo();
        setFetchFunction(fetchFunction);
    }

    public T get() {

        if (object != null) {
            return object;
        } if (reference.size() != 0 && fetchFunction != null) {
            Debug.printf("Fetch to cache %s\n", reference.toString());
            object = fetchFunction.apply(reference);
            return object;
        }

        return null;
    }

    public void set(T entity) {



        if (entity != null) {
            reference =  EntityClassInfo.of(entity.getClass()).getIds(entity);
        }
        else {

            Debug.printf("@ LazyFetch.set(null) on Ref#%s\n", reference);
            Debug.printStackTrace();
            //setReference(IntegerList.emptyList());
        }

        object = entity;
    }

    public void invalidate() {

        object=null;
    }

    public IntegerList getReference() {

        return reference;

    }

    public void setReference(IntegerList reference) {
       // Debug.printStack();
        this.reference = reference==null? IntegerList.emptyList() :reference;
        Debug.printf("LazyFetch Set reference to %s\n", this.reference.toString());
        //Invalidate the object
        object = null;
    }

    public Function<IntegerList, T> getFetchFunction() {
        return fetchFunction;
    }

    public void setFetchFunction(Function<IntegerList, T> fetchFunction) {
        Objects.requireNonNull(fetchFunction);

        this.fetchFunction = fetchFunction;
    }

    @Override
    public String toString() {
        return String.format("(%s:%s)", reference, object==null?"null":object.toString());
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
