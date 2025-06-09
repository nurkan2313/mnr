package kg.sh.mnr.mapper;

import jakarta.persistence.EntityNotFoundException;
import kg.sh.mnr.entity.Incident;
import kg.sh.mnr.entity.dict.Country;
import kg.sh.mnr.entity.dict.UnitOfMeasurement;
import kg.sh.mnr.model.requests.IncidentFormRequest;
import kg.sh.mnr.repository.UnitOfMeasurementRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UnitOfMeasurementRepository.class})
public interface IncidentMapper {
    IncidentFormRequest toIncidentFormRequest(Incident incident);
    @Mapping(source = "unitOfMeasurementId", target = "unitOfMeasurement.id")
    @Mapping(source = "authorityId", target = "authority.id")
    @Mapping(source = "discoveryMethodId", target = "discoveryMethod.id")
    @Mapping(source = "reasonForSeizureId", target = "reasonForSeizure.id")
    @Mapping(source = "transportMethodId", target = "transportMethod.id")
    @Mapping(source = "tradeDirectionId", target = "tradeDirection.id")
    Incident toIncident(IncidentFormRequest incidentFormRequest);

    default UnitOfMeasurement mapUnitOfMeasurement(Long id, @Context UnitOfMeasurementRepository repository) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("UnitOfMeasurement not found with id: " + id));
    }

    default String map(Country country) {
        return country != null ? country.getName() : null;
    }

    default Country map(String countryName) {
        if (countryName == null) return null;
        Country country = new Country();
        country.setName(countryName);
        return country;
    }

}
