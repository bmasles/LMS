package com.smoothstack.lms.common.service;

import com.smoothstack.lms.common.exception.DependencyException;
import com.smoothstack.lms.common.model.Branch;
import com.smoothstack.lms.common.repository.BranchCommonRepository;
import com.smoothstack.lms.common.repository.CopiesCommonRepository;
import com.smoothstack.lms.common.repository.LoansCommonRepository;
import com.smoothstack.lms.common.repository.RepositoryAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.Validator;

public abstract class BranchCommonService implements CommonService<Branch, Long> {


    private BranchCommonRepository authorCommonRepository;

    private Validator validator;

    @Autowired
    public final void setBranchCommonRepository(BranchCommonRepository authorCommonRepository) {
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
    public JpaRepository<Branch, Long> getJpaRepository() {
        return authorCommonRepository;
    }

    @Override
    public boolean beforeDelete(Branch branch) {
        if (((LoansCommonRepository) RepositoryAdapter.getLoansRepository()).existsByBranch(branch)) {
            throw new DependencyException("Cannot delete branch, all checkouted books must be assigned to other branch before deletion.");
        }

        if (((CopiesCommonRepository) RepositoryAdapter.getCopiesRepository()).existsByBranch(branch)) {
            throw new DependencyException("Cannot delete branch, all books must be assigned to other branch before deletion.");
        }

        return true;

    }
}

