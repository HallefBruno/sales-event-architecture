package com.sud.sales.api.repository;

import com.sud.sales.api.model.Outbox;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutboxRepository extends JpaRepository<Outbox, Long> {
    List<Outbox> findByProcessedFalse();
}