package kg.core.mnr.service;

import kg.core.mnr.models.entity.dict.UnitOfMeasurement;
import kg.core.mnr.repository.UnitOfMeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitOfMeasurementService {

    @Autowired
    private UnitOfMeasurementRepository repository;

    public List<UnitOfMeasurement> searchUnits(String query) {
        return repository.findByUnitContainingIgnoreCase(query);
    }

    public UnitOfMeasurement addUnit(UnitOfMeasurement unit) {
        return repository.save(unit);
    }

    public List<UnitOfMeasurement> getAllUnits() {
        return repository.findAll();
    }

    public UnitOfMeasurement getUnitById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteUnit(Long id) {
        repository.deleteById(id);
    }

    public UnitOfMeasurement findUnitByName(String name) {
        return repository.findByUnit(name);
    }
}
