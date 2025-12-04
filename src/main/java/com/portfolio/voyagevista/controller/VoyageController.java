package com.portfolio.voyagevista.controller;

import com.portfolio.voyagevista.model.AuditLog;
import com.portfolio.voyagevista.model.Voyage;
import com.portfolio.voyagevista.service.VoyageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class VoyageController {

    @Autowired
    private VoyageService voyageService;

    private String sid(String header) { return (header == null || header.isBlank()) ? "public" : header; }

    @GetMapping("/voyages")
    public List<Voyage> getVoyages(@RequestHeader(value = "X-Session-Id", required = false) String sessionId) {
        return voyageService.getAllVoyages(sid(sessionId));
    }

    @PostMapping("/voyages")
    public ResponseEntity<Voyage> createVoyage(@RequestHeader(value = "X-Session-Id", required = false) String sessionId,
                                               @RequestBody Voyage voyage) {
        return ResponseEntity.ok(voyageService.createVoyage(sid(sessionId), voyage));
    }

    @PutMapping("/voyages/{id}")
    public ResponseEntity<Voyage> updateVoyage(@RequestHeader(value = "X-Session-Id", required = false) String sessionId,
                                               @PathVariable Long id, @RequestBody Voyage voyage) {
        return ResponseEntity.ok(voyageService.updateVoyage(sid(sessionId), id, voyage));
    }

    @DeleteMapping("/voyages")
    public ResponseEntity<Void> deleteAllVoyages(@RequestHeader(value = "X-Session-Id", required = false) String sessionId) {
        voyageService.deleteAllVoyages(sid(sessionId));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/audit-logs")
    public List<AuditLog> getAuditLogs(@RequestHeader(value = "X-Session-Id", required = false) String sessionId) {
        return voyageService.getAuditLogs(sid(sessionId));
    }

    @DeleteMapping("/audit-logs")
    public ResponseEntity<Void> deleteAllAuditLogs(@RequestHeader(value = "X-Session-Id", required = false) String sessionId) {
        voyageService.deleteAllAuditLogs(sid(sessionId));
        return ResponseEntity.noContent().build();
    }
}
