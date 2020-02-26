package com.smoothstack.lms.common.service;

import com.smoothstack.lms.common.model.Loans;
import com.smoothstack.lms.common.repository.LoansCommonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.Validator;

public class LoansCommonService implements CommonService<Loans, Long> {


    private LoansCommonRepository authorCommonRepository;

    private Validator validator;

    @Autowired
    public final void setLoansCommonRepository(LoansCommonRepository authorCommonRepository) {
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
    public JpaRepository<Loans, Long> getJpaRepository() {
        return authorCommonRepository;
    }
}

