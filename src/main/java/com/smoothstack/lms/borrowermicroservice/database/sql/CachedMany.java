package com.smoothstack.lms.borrowermicroservice.database.sql;

import com.smoothstack.lms.borrowermicroservice.context.util.EntityClassInfo;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;

public class CachedMany<T> {

    private final HashMap<Integer, CachedOne<T>> cache;
    Function<Integer, T> fetchFunction;

    public CachedMany(Function<Integer, T> fetchFunction) {
        this.fetchFunction = fetchFunction;
        this.cache = new HashMap<Integer, CachedOne<T>>();
    }

    public static <R> CachedMany<R> of(Class<R> clazz, Function<Integer, R> fetchFunction) {
        return new CachedMany<R> (fetchFunction);
    }

    public Optional<T> get(int i) {
        CachedOne<T> cachedOne = cache.get(i);
        if (cachedOne == null)
            return Optional.empty();
        else
            return cachedOne.get();
    }


    public void set(T value) {
        if (value == null) return;

        int id = EntityClassInfo.of(value.getClass()).getIdField().get(value);
        CachedOne<T> cachedOne = cache.get(id);

        if (cachedOne == null)
            cache.put(id, CachedOne.of(value, fetchFunction));
        else
            cachedOne.set(value);
    }

    public HashMap<Integer, CachedOne<T>> getCache() {
        return cache;
    }
}
