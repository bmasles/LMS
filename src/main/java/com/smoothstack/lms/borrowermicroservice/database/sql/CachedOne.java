package com.smoothstack.lms.borrowermicroservice.database.sql;

import java.util.Optional;
import java.util.function.Function;

public class CachedOne<T> {

    private final LazyFetch<T> cache;

    public CachedOne(Function<Integer, T> fetchFunction) {
        this.cache = new LazyFetch<T>(fetchFunction);
    }

    public static <R> CachedOne<R> of(Class<R> clazz, Function<Integer, R> fetchFunction) {
        return new CachedOne<R> (fetchFunction);
    }

    public static <R> CachedOne<R> of(R value, Function<Integer, R> fetchFunction) {
        Class<R> clazz = (Class<R>) value.getClass();
        return CachedOne.of(clazz, fetchFunction);
    }

    public Optional<T> get() {
        return Optional.ofNullable(cache.get());
    }


    public void set(T value) {
        cache.set(value);
    }

    public LazyFetch<T> getCache() {
        return cache;
    }
}
