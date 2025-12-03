package com.portfolio.voyagevista.repository;

import com.portfolio.voyagevista.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
