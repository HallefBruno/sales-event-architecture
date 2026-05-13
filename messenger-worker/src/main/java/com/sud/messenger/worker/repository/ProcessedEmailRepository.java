package com.sud.messenger.worker.repository;

import com.sud.messenger.worker.model.ProcessedEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedEmailRepository extends JpaRepository<ProcessedEmail, Long> {

}
