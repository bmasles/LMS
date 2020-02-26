package com.smoothstack.lms.common.service;

import com.smoothstack.lms.common.model.Genre;
import com.smoothstack.lms.common.repository.GenreCommonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.Validator;

public class GenreCommonService implements CommonService<Genre, Long> {


    private GenreCommonRepository authorCommonRepository;

    private Validator validator;

    @Autowired
    public final void setGenreCommonRepository(GenreCommonRepository authorCommonRepository) {
        this.authorCommonRepository = authorCommonRepository;
    }

    @Autowired
    public void setValidator(@Qualifier("DefaultValidator") Validator validator) {
        this.validator = validator;
    }

    @Override
    public Validator getValidator() {
        return validator;
    }

    @Override
    public JpaRepository<Genre, Long> getJpaRepository() {
        return authorCommonRepository;
    }
}

