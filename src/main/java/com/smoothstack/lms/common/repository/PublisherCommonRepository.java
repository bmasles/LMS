package com.smoothstack.lms.common.repository;

import com.smoothstack.lms.common.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublisherCommonRepository extends JpaRepository<Publisher, Long> {

    Optional<Publisher> findFirstByPublisherNameIgnoreCase(String publisherName);
}
