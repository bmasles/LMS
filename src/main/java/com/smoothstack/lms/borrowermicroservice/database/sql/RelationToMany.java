package com.smoothstack.lms.borrowermicroservice.database.sql;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class RelationToMany<T> extends CachedMany<T> {

    public RelationToMany(Function<IntegerList, T> fetchFunction) {
        super(fetchFunction);
    }


}
