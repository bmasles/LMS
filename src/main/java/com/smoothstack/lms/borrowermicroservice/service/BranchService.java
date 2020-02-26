package com.smoothstack.lms.borrowermicroservice.service;

import com.smoothstack.lms.common.model.Branch;
import com.smoothstack.lms.common.service.BranchCommonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BranchService extends BranchCommonService {

    @Transactional
    public Branch buildAndSave(Branch branch) {

        save(branch);

        return branch;
    }

}
