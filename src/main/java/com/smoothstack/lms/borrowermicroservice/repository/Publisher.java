package com.smoothstack.lms.borrowermicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Publisher extends JpaRepository<Publisher,Long> {
}
