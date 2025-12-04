package com.portfolio.voyagevista.repository;

import com.portfolio.voyagevista.model.Voyage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VoyageRepository extends JpaRepository<Voyage, Long> {
    List<Voyage> findAllBySessionId(String sessionId);
    void deleteAllBySessionId(String sessionId);
    Optional<Voyage> findByIdAndSessionId(Long id, String sessionId);
}
