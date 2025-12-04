package com.portfolio.voyagevista.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Voyage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shipName;
    private String departurePort;
    private String destinationPort;
    private LocalDate departureDate;
    private LocalDate returnDate;

    // "Active", "Completed", "Cancelled", "Scheduled"
    private String status;

    // Session isolation key
    private String sessionId;
}
