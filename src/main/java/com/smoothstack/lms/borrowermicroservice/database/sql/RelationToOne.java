package com.smoothstack.lms.borrowermicroservice.database.sql;

import java.util.Optional;
import java.util.function.Function;

public class RelationToOne<T> extends CachedOne<T> {

    public RelationToOne(Function<Integer, T> fetchFunction) {
        super(fetchFunction);
    }
}
