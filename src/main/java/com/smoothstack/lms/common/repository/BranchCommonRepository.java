package com.smoothstack.lms.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smoothstack.lms.common.model.Branch;

public interface BranchCommonRepository extends JpaRepository<Branch, Long> {

	boolean existsByBranchNameIgnoreCase(String branchName);
}
