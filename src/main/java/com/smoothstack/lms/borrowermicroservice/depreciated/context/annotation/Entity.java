package com.smoothstack.lms.borrowermicroservice.depreciated.context.annotation;

import com.smoothstack.lms.borrowermicroservice.depreciated.persistance.CrudRepository;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)

public @interface Entity {
    Class<? extends CrudRepository> repository() default CrudRepository.class;
}
