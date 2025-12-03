package com.portfolio.voyagevista;

import com.portfolio.voyagevista.model.AuditLog;
import com.portfolio.voyagevista.model.Voyage;
import com.portfolio.voyagevista.repository.AuditLogRepository;
import com.portfolio.voyagevista.repository.VoyageRepository;
import com.portfolio.voyagevista.service.VoyageService;
import com.project1.auditsystem.AuditSystemApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AuditSystemApplication.class)
class VoyageServiceIntegrationTest {

    @Autowired
    private VoyageService voyageService;

    @Autowired
    private VoyageRepository voyageRepository;

    @Autowired
    private AuditLogRepository auditLogRepository;

    @BeforeEach
    void clean() {
        auditLogRepository.deleteAll();
        voyageRepository.deleteAll();
    }

    @Test
    void createVoyage_shouldPersist_andWriteAuditLog() {
        Voyage v = new Voyage();
        v.setShipName("Disney Wish");
        v.setDeparturePort("Port Canaveral");
        v.setDestinationPort("Castaway Cay");
        v.setDepartureDate(LocalDate.now());
        v.setStatus("Scheduled");

        Voyage saved = voyageService.createVoyage(v);
        assertThat(saved.getId()).isNotNull();

        List<Voyage> voyages = voyageService.getAllVoyages();
        assertThat(voyages).hasSize(1);

        List<AuditLog> logs = voyageService.getAuditLogs();
        assertThat(logs).isNotEmpty();
        assertThat(logs.get(0).getAction()).isEqualTo("CREATE");
    }

    @Test
    void updateVoyage_shouldChangeFields_andWriteUpdateAuditLog() {
        // create
        Voyage v = new Voyage();
        v.setShipName("Disney Magic");
        v.setDeparturePort("Miami");
        v.setDepartureDate(LocalDate.now());
        v.setStatus("Scheduled");
        Voyage saved = voyageService.createVoyage(v);

        // update
        Voyage upd = new Voyage();
        upd.setShipName("Disney Magic");
        upd.setDeparturePort("Miami");
        upd.setDestinationPort("Nassau");
        upd.setDepartureDate(v.getDepartureDate().plusDays(1));
        upd.setReturnDate(v.getDepartureDate().plusDays(4));
        upd.setStatus("Active");

        Voyage updated = voyageService.updateVoyage(saved.getId(), upd);
        assertThat(updated.getStatus()).isEqualTo("Active");
        assertThat(updated.getDestinationPort()).isEqualTo("Nassau");

        List<AuditLog> logs = voyageService.getAuditLogs();
        assertThat(logs.stream().anyMatch(l -> "UPDATE".equals(l.getAction()))).isTrue();
    }
}
