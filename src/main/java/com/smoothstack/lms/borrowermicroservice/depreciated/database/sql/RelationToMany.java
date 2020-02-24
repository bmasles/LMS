package com.smoothstack.lms.borrowermicroservice.depreciated.database.sql;

import java.util.function.Function;

public class RelationToMany<T> extends CachedMany<T> {

    public RelationToMany(Function<IntegerList, T> fetchFunction) {
        super(fetchFunction);
    }


}
