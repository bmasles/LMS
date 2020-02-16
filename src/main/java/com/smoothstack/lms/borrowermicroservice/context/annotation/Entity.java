package com.smoothstack.lms.borrowermicroservice.context.annotation;

import com.smoothstack.lms.borrowermicroservice.persistance.CrudRepository;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)

public @interface Entity {
    Class<? extends CrudRepository> defaultRepository() default CrudRepository.class;
}
