package com.smoothstack.lms.common.repository;

import com.smoothstack.lms.common.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchCommonRepository extends JpaRepository<Branch, Long> {

    boolean existsByBranchNameIgnoreCase(String branchName);
}
