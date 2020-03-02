package com.smoothstack.lms.common.service;

import com.smoothstack.lms.common.exception.RecordNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CommonService<T, ID> {

    Validator getValidator();

    JpaRepository<T, ID> getJpaRepository();

    default List<T> findAll() {
        return getJpaRepository().findAll();
    };

    default Optional<T> findById(ID... ids)  {
        return getJpaRepository().findById(ids[0]);
    };

    default T findByIdOrThrow(ID... ids){
        return findById(ids).orElseThrow(RecordNotFoundException::new);
    };

    default boolean beforeSave(T object) {return true;};
    default void afterSave(T object) {};

    default T save(T object)  {
        if (!this.beforeSave(object)) return object;
        getJpaRepository().save(object);
        this.afterSave(object);
        return (object);
    };


    default boolean beforeDelete(T object) { return true; }
    default void afterDelete(T object) {}

    default void delete(T object) {
        if (!this.beforeDelete(object)) return;
        getJpaRepository().delete(object);
        this.afterDelete(object);
    }

    default Set<ConstraintViolation<T>> validate(T object) {
        return getValidator().validate(object);
    }

    default boolean isValid(T object){
        return validate(object).isEmpty();
    }

}
