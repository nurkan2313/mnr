package kg.sh.mnr.service;

import kg.sh.mnr.entity.Incident;
import kg.sh.mnr.entity.dict.Country;
import kg.sh.mnr.entity.dict.UnitOfMeasurement;
import kg.sh.mnr.repository.CountryRepository;
import kg.sh.mnr.repository.IncidentRepository;
import kg.sh.mnr.repository.UnitOfMeasurementRepository;
import kg.sh.mnr.repository.em.IncidentRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class IncidentService {

    @Autowired
    private IncidentRepository incidentRepository;
    @Autowired
    private IncidentRepositoryImpl incidentRepositoryImpl;
    @Autowired
    private UnitOfMeasurementRepository unitOfMeasurementRepository;
    @Autowired
    private CountryRepository countryRepository;

    @Transactional(readOnly = true)
    public Page<Incident> getAllIncidents(Pageable pageable) {
        if (pageable == null) {
            return Page.empty();
        }
        return incidentRepository.findAll(pageable);
    }

    public Page<Incident> filterIncidents(String species,
                                          String authority,
                                          String transportMethod,
                                          String suspectedOriginCountry,
                                          String finalDestination,
                                          LocalDate registeredAt,
                                          Pageable pageable) {
        return incidentRepositoryImpl.filterByCriteria(species, authority, transportMethod, suspectedOriginCountry, finalDestination, registeredAt, pageable);
    }

    public Incident getIncidentById(String id) {
        return incidentRepository.findById(UUID.fromString(id)).orElse(null);
    }

    public Incident createIncident(Incident incident) {
        incident.setId(UUID.randomUUID());
        UnitOfMeasurement unitOfMeasurement = incident.getUnitOfMeasurement();

        unitOfMeasurementRepository.findById(unitOfMeasurement.getId()).orElse(null);
        if (unitOfMeasurement.getId() == null) {
            unitOfMeasurementRepository.save(unitOfMeasurement);
        }
        UUID countryId = UUID.fromString(incident.getSuspectedOriginCountry().getName());
        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new IllegalArgumentException("Страна не найдена: " + countryId));
        incident.setSuspectedOriginCountry(country);

        incidentRepository.save(incident);
        return incidentRepository.save(incident);
    }

    public void deleteIncident(UUID id) {
        incidentRepository.deleteById(id);
    }

}
