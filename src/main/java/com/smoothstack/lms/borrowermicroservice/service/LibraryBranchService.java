package com.smoothstack.lms.borrowermicroservice.service;

import com.smoothstack.lms.common.model.Branch;
import com.smoothstack.lms.common.repository.LibraryBranchCommonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibraryBranchService {

    @Autowired
    LibraryBranchCommonRepository libraryBranchRepository;

    @Transactional
    public Branch buildAndSave(Branch branch) {

        libraryBranchRepository.save(branch);
        libraryBranchRepository.flush();
        return branch;
    }

}
