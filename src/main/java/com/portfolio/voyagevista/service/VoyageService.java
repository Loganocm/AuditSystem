package com.portfolio.voyagevista.service;

import com.portfolio.voyagevista.model.AuditLog;
import com.portfolio.voyagevista.model.Voyage;
import com.portfolio.voyagevista.repository.AuditLogRepository;
import com.portfolio.voyagevista.repository.VoyageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VoyageService {

    @Autowired
    private VoyageRepository voyageRepository;

    @Autowired
    private AuditLogRepository auditLogRepository;

    public List<Voyage> getAllVoyages() {
        return voyageRepository.findAll();
    }

    public Voyage createVoyage(Voyage voyage) {
        Voyage savedVoyage = voyageRepository.save(voyage);
        logChange("Voyage", savedVoyage.getId(), "CREATE", "Created new voyage for " + savedVoyage.getShipName());
        return savedVoyage;
    }

    public Voyage updateVoyage(Long id, Voyage details) {
        Voyage existing = voyageRepository.findById(id).orElseThrow();
        String oldStatus = existing.getStatus();

        existing.setShipName(details.getShipName());
        existing.setStatus(details.getStatus());
        existing.setDepartureDate(details.getDepartureDate());
        existing.setDeparturePort(details.getDeparturePort());
        existing.setDestinationPort(details.getDestinationPort());
        existing.setReturnDate(details.getReturnDate());

        Voyage updated = voyageRepository.save(existing);

        // This logic directly answers the "Traceability" requirement
        logChange("Voyage", updated.getId(), "UPDATE",
                "Updated status from " + oldStatus + " to " + updated.getStatus());

        return updated;
    }

    private void logChange(String entity, Long id, String action, String detail) {
        AuditLog log = new AuditLog();
        log.setEntityName(entity);
        log.setEntityId(id);
        log.setAction(action);
        log.setChangedBy("SystemAdmin"); // In a real app, pull from SecurityContext
        log.setTimestamp(LocalDateTime.now());
        log.setDetails(detail);
        auditLogRepository.save(log);
    }

    public List<AuditLog> getAuditLogs() {
        return auditLogRepository.findAll();
    }
}
