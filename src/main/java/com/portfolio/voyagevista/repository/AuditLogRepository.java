package com.portfolio.voyagevista.repository;

import com.portfolio.voyagevista.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findAllBySessionId(String sessionId);
    void deleteAllBySessionId(String sessionId);
}
