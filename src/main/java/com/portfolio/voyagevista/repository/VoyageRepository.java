package com.portfolio.voyagevista.repository;

import com.portfolio.voyagevista.model.Voyage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoyageRepository extends JpaRepository<Voyage, Long> {
}
