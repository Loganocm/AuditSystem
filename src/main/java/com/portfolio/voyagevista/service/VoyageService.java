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

    private static final String DEFAULT_SESSION = "public";

    @Autowired
    private VoyageRepository voyageRepository;

    @Autowired
    private AuditLogRepository auditLogRepository;

    // ---- Session-aware methods ----
    public List<Voyage> getAllVoyages(String sessionId) {
        return voyageRepository.findAllBySessionId(sessionOrDefault(sessionId));
    }

    public Voyage createVoyage(String sessionId, Voyage voyage) {
        String sid = sessionOrDefault(sessionId);
        voyage.setSessionId(sid);
        Voyage savedVoyage = voyageRepository.save(voyage);
        logChange(sid, "Voyage", savedVoyage.getId(), "CREATE", "Created new voyage for " + savedVoyage.getShipName());
        return savedVoyage;
    }

    public Voyage updateVoyage(String sessionId, Long id, Voyage details) {
        String sid = sessionOrDefault(sessionId);
        Voyage existing = voyageRepository.findByIdAndSessionId(id, sid).orElseThrow();
        String oldStatus = existing.getStatus();

        existing.setShipName(details.getShipName());
        existing.setStatus(details.getStatus());
        existing.setDepartureDate(details.getDepartureDate());
        existing.setDeparturePort(details.getDeparturePort());
        existing.setDestinationPort(details.getDestinationPort());
        existing.setReturnDate(details.getReturnDate());

        Voyage updated = voyageRepository.save(existing);

        // This logic directly answers the "Traceability" requirement
        logChange(sid, "Voyage", updated.getId(), "UPDATE",
                "Updated status from " + oldStatus + " to " + updated.getStatus());

        return updated;
    }

    public void deleteAllVoyages(String sessionId) {
        String sid = sessionOrDefault(sessionId);
        voyageRepository.deleteBySessionId(sid);
        // Log a high-level system action for traceability
        logChange(sid, "Voyage", 0L, "DELETE_ALL", "Cleared all voyages");
    }

    public void deleteAllAuditLogs(String sessionId) {
        String sid = sessionOrDefault(sessionId);
        auditLogRepository.deleteBySessionId(sid);
        // Do not log here since logs are being cleared
    }

    private void logChange(String sessionId, String entity, Long id, String action, String detail) {
        AuditLog log = new AuditLog();
        log.setSessionId(sessionId);
        log.setEntityName(entity);
        log.setEntityId(id);
        log.setAction(action);
        log.setChangedBy("SystemAdmin"); // In a real app, pull from SecurityContext
        log.setTimestamp(LocalDateTime.now());
        log.setDetails(detail);
        auditLogRepository.save(log);
    }

    public List<AuditLog> getAuditLogs(String sessionId) {
        return auditLogRepository.findAllBySessionId(sessionOrDefault(sessionId));
    }

    // ---- Backwards-compatible methods (used by existing tests) ----
    public List<Voyage> getAllVoyages() { return getAllVoyages(DEFAULT_SESSION); }
    public Voyage createVoyage(Voyage voyage) { return createVoyage(DEFAULT_SESSION, voyage); }
    public Voyage updateVoyage(Long id, Voyage details) { return updateVoyage(DEFAULT_SESSION, id, details); }
    public void deleteAllVoyages() { deleteAllVoyages(DEFAULT_SESSION); }
    public void deleteAllAuditLogs() { deleteAllAuditLogs(DEFAULT_SESSION); }
    public List<AuditLog> getAuditLogs() { return getAuditLogs(DEFAULT_SESSION); }

    private String sessionOrDefault(String sessionId) {
        return (sessionId == null || sessionId.isBlank()) ? DEFAULT_SESSION : sessionId;
    }
}
