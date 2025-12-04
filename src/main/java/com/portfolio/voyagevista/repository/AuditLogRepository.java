package com.portfolio.voyagevista.repository;

import com.portfolio.voyagevista.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findAllBySessionId(String sessionId);

    @Modifying
    @Transactional
    @Query("delete from AuditLog a where a.sessionId = :sessionId")
    int deleteBySessionId(@Param("sessionId") String sessionId);
}
