package com.smoothstack.lms.common.service;

import com.smoothstack.lms.common.model.Copies;
import com.smoothstack.lms.common.repository.CopiesCommonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.Validator;

public class CopiesCommonService implements CommonService<Copies, Long> {


    private CopiesCommonRepository authorCommonRepository;

    private Validator validator;

    @Autowired
    public final void setCopiesCommonRepository(CopiesCommonRepository authorCommonRepository) {
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
    public JpaRepository<Copies, Long> getJpaRepository() {
        return authorCommonRepository;
    }
}

