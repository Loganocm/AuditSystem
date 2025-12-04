package com.portfolio.voyagevista.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String entityName; // e.g., "Voyage"
    private Long entityId;     // e.g., 101
    private String action;     // "CREATE", "UPDATE", "DELETE"
    private String changedBy;  // Mock user for this demo
    private LocalDateTime timestamp;
    private String details;    // "Changed status from Active to Cancelled"

    // Session isolation key
    private String sessionId;
}
