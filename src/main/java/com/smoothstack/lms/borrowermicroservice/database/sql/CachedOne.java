package com.smoothstack.lms.borrowermicroservice.database.sql;

import com.smoothstack.lms.borrowermicroservice.Debug;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class CachedOne<T> {

    private final LazyFetch<T> cache;

    public CachedOne(Function<IntegerList, T> fetchFunction) {
        Debug.printMethodInfo();
        this.cache = new LazyFetch<T>(fetchFunction);
    }
    public static <R> CachedOne<R> of(Function<IntegerList, R> fetchFunction) {
        return  CachedOne.of(null, fetchFunction);
    }

    public static <R> CachedOne<R> of(Object value, Function<IntegerList, R> fetchFunction) {
        CachedOne<R> cachedOne = new CachedOne<>(fetchFunction);
        if (!(value instanceof Class) && (value != null))
            cachedOne.set((R) value);
        return  cachedOne;
    }
    public Optional<T> get() {
        return Optional.ofNullable(cache.get());
    }

    public void set(T value) {
        cache.set(value);
    }

    public void setReference(IntegerList reference) {
        cache.setReference(reference);
    }

    public IntegerList  getReference() {
        return cache.getReference();
    }

    public void invalidate() {
        cache.invalidate();
    }
    public LazyFetch<T> getCache() {
        return cache;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CachedOne{");
        sb.append("cache=").append(cache==null?"<null>":cache.toString());
        sb.append('}');
        return sb.toString();
    }
}
