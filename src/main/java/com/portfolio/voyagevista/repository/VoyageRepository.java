package com.portfolio.voyagevista.repository;

import com.portfolio.voyagevista.model.Voyage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface VoyageRepository extends JpaRepository<Voyage, Long> {
    List<Voyage> findAllBySessionId(String sessionId);

    @Modifying
    @Transactional
    @Query("delete from Voyage v where v.sessionId = :sessionId")
    int deleteBySessionId(@Param("sessionId") String sessionId);

    Optional<Voyage> findByIdAndSessionId(Long id, String sessionId);
}
