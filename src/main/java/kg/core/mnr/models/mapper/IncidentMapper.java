package kg.core.mnr.models.mapper;

import jakarta.persistence.EntityNotFoundException;
import kg.core.mnr.models.dto.requests.IncidentFormRequest;
import kg.core.mnr.models.entity.Incident;
import kg.core.mnr.models.entity.dict.UnitOfMeasurement;
import kg.core.mnr.repository.UnitOfMeasurementRepository;
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
}
