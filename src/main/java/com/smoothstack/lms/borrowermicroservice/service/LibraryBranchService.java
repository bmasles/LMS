package com.smoothstack.lms.borrowermicroservice.service;

import com.smoothstack.lms.borrowermicroservice.common.model.LibraryBranch;
import com.smoothstack.lms.borrowermicroservice.common.repository.LibraryBranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibraryBranchService {

    @Autowired
    LibraryBranchRepository libraryBranchRepository;

    @Transactional
    public LibraryBranch buildAndSave(LibraryBranch libraryBranch) {

        libraryBranchRepository.save(libraryBranch);
        libraryBranchRepository.flush();
        return libraryBranch;
    }

}
