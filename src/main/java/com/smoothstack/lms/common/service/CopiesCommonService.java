package com.smoothstack.lms.common.service;

import com.smoothstack.lms.common.exception.DependencyException;
import com.smoothstack.lms.common.model.Copies;
import com.smoothstack.lms.common.repository.CopiesCommonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.Validator;
import java.util.Optional;

public abstract class CopiesCommonService implements CommonService<Copies, Long> {


    private CopiesCommonRepository copiesCommonRepository;

    private Validator validator;



    @Autowired
    public final void setCopiesCommonRepository(CopiesCommonRepository authorCommonRepository) {
        this.copiesCommonRepository = authorCommonRepository;
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
        return copiesCommonRepository;
    }

    @Override
    public Optional<Copies> findById(Long... id)  {
        if (id.length < 2)
            throw new IllegalArgumentException("Need two ids");

        return copiesCommonRepository.findById(id[0], id[1]);
    };




    @Override
    public boolean beforeDelete(Copies copies) {
        if (copies.getCopiesAmount() != 0)
            throw new DependencyException("The copiesAmount must be zero before deletion.");

        return true;
    }
}

