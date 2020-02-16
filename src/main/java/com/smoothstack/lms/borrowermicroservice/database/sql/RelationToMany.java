package com.smoothstack.lms.borrowermicroservice.database.sql;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class RelationToMany<T> extends HashMap<Integer, CachedOne<T>> {

    Function<Integer, T> fetchFunction;

    public RelationToMany(int initialCapacity, float loadFactor, Function<Integer, T> fetchFunction) {
        super(initialCapacity, loadFactor);
        this.fetchFunction = fetchFunction;
    }

    public RelationToMany(int initialCapacity, Function<Integer, T> fetchFunction) {
        super(initialCapacity);
        this.fetchFunction = fetchFunction;
    }

    public RelationToMany(Function<Integer, T> fetchFunction) {
        this.fetchFunction = fetchFunction;
    }

    public RelationToMany(Map<? extends Integer, ? extends CachedOne<T>> m, Function<Integer, T> fetchFunction) {
        super(m);
        this.fetchFunction = fetchFunction;
    }
}
