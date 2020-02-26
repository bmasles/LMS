package com.smoothstack.lms.common.service;

import com.smoothstack.lms.common.model.Book;
import com.smoothstack.lms.common.repository.BookCommonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.Validator;

public class BookCommonService implements CommonService<Book, Long> {


    private BookCommonRepository authorCommonRepository;

    private Validator validator;

    @Autowired
    public final void setBookCommonRepository(BookCommonRepository authorCommonRepository) {
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
    public JpaRepository<Book, Long> getJpaRepository() {
        return authorCommonRepository;
    }
}
