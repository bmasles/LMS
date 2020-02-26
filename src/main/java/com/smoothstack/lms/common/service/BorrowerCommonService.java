package com.smoothstack.lms.common.service;

import com.smoothstack.lms.common.model.Borrower;
import com.smoothstack.lms.common.repository.BorrowerCommonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.Validator;

public class BorrowerCommonService implements CommonService<Borrower, Long> {


    private BorrowerCommonRepository authorCommonRepository;

    private Validator validator;

    @Autowired
    public final void setBorrowerCommonRepository(BorrowerCommonRepository authorCommonRepository) {
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
    public JpaRepository<Borrower, Long> getJpaRepository() {
        return authorCommonRepository;
    }
}
