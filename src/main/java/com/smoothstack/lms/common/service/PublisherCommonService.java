package com.smoothstack.lms.common.service;

import com.smoothstack.lms.common.model.Publisher;
import com.smoothstack.lms.common.repository.PublisherCommonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.Validator;

public class PublisherCommonService implements CommonService<Publisher, Long> {


    private PublisherCommonRepository authorCommonRepository;

    private Validator validator;

    @Autowired
    public final void setPublisherCommonRepository(PublisherCommonRepository authorCommonRepository) {
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
    public JpaRepository<Publisher, Long> getJpaRepository() {
        return authorCommonRepository;
    }
}
