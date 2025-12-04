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

    @GetMapping("/voyages")
    public List<Voyage> getVoyages() {
        return voyageService.getAllVoyages();
    }

    @PostMapping("/voyages")
    public ResponseEntity<Voyage> createVoyage(@RequestBody Voyage voyage) {
        return ResponseEntity.ok(voyageService.createVoyage(voyage));
    }

    @PutMapping("/voyages/{id}")
    public ResponseEntity<Voyage> updateVoyage(@PathVariable Long id, @RequestBody Voyage voyage) {
        return ResponseEntity.ok(voyageService.updateVoyage(id, voyage));
    }

    @DeleteMapping("/voyages")
    public ResponseEntity<Void> deleteAllVoyages() {
        voyageService.deleteAllVoyages();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/audit-logs")
    public List<AuditLog> getAuditLogs() {
        return voyageService.getAuditLogs();
    }

    @DeleteMapping("/audit-logs")
    public ResponseEntity<Void> deleteAllAuditLogs() {
        voyageService.deleteAllAuditLogs();
        return ResponseEntity.noContent().build();
    }
}
