package com.smoothstack.lms.borrowermicroservice.database.sql;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CachedOneTest {

    @Test
    void cachedOneMustReturnValueGeneratedFromFetcherLambdaFunctionWhenGet() {
        CachedOne<String> stringCached = CachedOne.of(il->{
            return "This is value from fetchFunction "+il.toString();
        });

        stringCached.getCache().setReference(IntegerList.of(11,12,14,15));

        assertEquals("This is value from fetchFunction [11,12,14,15]", stringCached.get().orElse("fetchFunction does not work!"));
    }

    @Test
    void cachedOneMustNotFetchWhenValueWasSet() {
        CachedOne<String> stringCached = CachedOne.of(il->{
            return "Should not be this value "+il.toString();
        });

        stringCached.getCache().setReference(IntegerList.of(11,12,14,15));

        stringCached.set("Should be this value");

        assertEquals("Should be this value", stringCached.get().orElse("fetchFunction does not work!"));
    }


}