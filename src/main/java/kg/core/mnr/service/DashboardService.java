package kg.core.mnr.service;

import kg.core.mnr.models.entity.CitesPermit;
import kg.core.mnr.repository.CitesPermitRepository;
import kg.core.mnr.repository.IncidentRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class DashboardService {

    private final CitesPermitRepository citesPermitRepository;
    private final IncidentRepository incidentRepository;

    public long getTotalPermits() {
        return citesPermitRepository.count();
    }

    public long getTotalIncidents() {
        return incidentRepository.getAllByRegisteredAtBetween();
    }

    public Long getImportReportByCountry() {
        return citesPermitRepository.countByImporter();
    }

    public Page<Map<String, Object>> getTopExportedSpecies(Pageable pageable) {
        return citesPermitRepository.getCitesPermitByCountry(pageable);
    }

    public List<CitesPermit> getCitesPermitByCountry() {
        return citesPermitRepository.getPermitesReport();
    }

    public Page<CitesPermit> getPermitsByDateRange(Pageable pageable) {
        return citesPermitRepository.findAll(pageable); // Assuming a JPA repository
    }

    public long countPermitsFrom(LocalDate fromDate) {
        return citesPermitRepository.countByIssueDateAfter(fromDate);
    }

    public long countIncidentsFrom(LocalDate fromDate) {
        return incidentRepository.countByRegisteredAtAfter(fromDate);
    }

    public double countImportVolumeFrom(LocalDate fromDate) {
        return citesPermitRepository.sumImportVolumeSince(fromDate);
    }


    public double calculatePercentageChange(long current, long previous) {
        if (previous == 0) {
            return 100.0; // If previous is 0, return 100% change
        }
        return ((double) (current - previous) / previous) * 100;
    }
}
