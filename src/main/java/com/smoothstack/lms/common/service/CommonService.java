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

    default Optional<T> findById(ID id)  {
        return getJpaRepository().findById(id);
    };

    default T findByIdOrThrow(ID id){
        return findById(id).orElseThrow(RecordNotFoundException::new);
    };

    default boolean beforeSave(T object) {return true;};
    default void afterSave(T object) {};

    default T save(T object)  {
        if (!beforeSave(object)) return object;
        getJpaRepository().save(object);
        afterSave(object);
        return (object);
    };


    default boolean beforeDelete(T object) { return true; }
    default void afterDelete(T object) {}

    default void delete(T object) {
        if (!beforeDelete(object)) return;
        getJpaRepository().delete(object);
        afterDelete(object);
    }

    default Set<ConstraintViolation<T>> validate(T object) {
        return getValidator().validate(object);
    }

    default boolean isValid(T object){
        return validate(object).isEmpty();
    }

}
