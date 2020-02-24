package com.smoothstack.lms.borrowermicroservice.depreciated.database.sql;

import com.smoothstack.lms.borrowermicroservice.depreciated.context.util.EntityClassInfo;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;

public class CachedMany<T> {


    private final HashMap<IntegerList, CachedOne<T>> cache;
    Function<IntegerList, T> fetchFunction;

    public CachedMany(Function<IntegerList, T> fetchFunction) {
        this.fetchFunction = fetchFunction;
        this.cache = new HashMap<IntegerList, CachedOne<T>>();
    }

    public static <R> CachedMany<R> of(Class<R> clazz, Function<IntegerList, R> fetchFunction) {
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

        IntegerList ids = new IntegerList(EntityClassInfo.of(value.getClass()).getIds(value));
        CachedOne<T> cachedOne = cache.get(ids);

        if (cachedOne == null)
            cache.put(ids, CachedOne.of(value, fetchFunction));
        else
            cachedOne.set(value);
    }

    public HashMap<IntegerList, CachedOne<T>> getCache() {
        return cache;
    }
}
