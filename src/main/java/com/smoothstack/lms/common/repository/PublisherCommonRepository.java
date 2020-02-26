package com.smoothstack.lms.common.repository;

import com.smoothstack.lms.common.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublisherCommonRepository extends JpaRepository<Publisher, Long> {

    Optional<Publisher> findFirstByPublisherNameIgnoreCase(String publisherName);
}
