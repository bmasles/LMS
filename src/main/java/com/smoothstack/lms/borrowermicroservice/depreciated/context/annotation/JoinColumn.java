package com.smoothstack.lms.borrowermicroservice.depreciated.context.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface JoinColumn {

    String name();
    String referencedFieldName();
    String referencedColumnName() default "";
}
