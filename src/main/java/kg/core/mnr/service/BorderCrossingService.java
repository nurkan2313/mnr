package kg.core.mnr.service;

import kg.core.mnr.models.dto.enums.DocStatus;
import kg.core.mnr.models.entity.BorderCrossing;
import kg.core.mnr.models.entity.CitesPermit;
import kg.core.mnr.models.entity.dict.BorderCheckpoint;
import kg.core.mnr.repository.BorderCheckpointRepository;
import kg.core.mnr.repository.BorderCrossingRepository;
import kg.core.mnr.repository.CitesPermitRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class BorderCrossingService {

    private final BorderCrossingRepository borderCrossingRepository;
    private final CitesPermitRepository citesPermitRepository;
    private final BorderCheckpointRepository borderCheckpointRepository;

    public BorderCrossingService(BorderCrossingRepository borderCrossingRepository, CitesPermitRepository citesPermitRepository, BorderCheckpointRepository borderCheckpointRepository) {
        this.borderCrossingRepository = borderCrossingRepository;
        this.citesPermitRepository = citesPermitRepository;
        this.borderCheckpointRepository = borderCheckpointRepository;
    }

    public BorderCrossing recordCrossing(String permitId, UUID checkpointId) {
        CitesPermit permit = citesPermitRepository.findById(permitId)
                .orElseThrow(() -> new RuntimeException("Permit not found"));

        BorderCheckpoint checkpoint = borderCheckpointRepository.findById(checkpointId)
                .orElseThrow(() -> new RuntimeException("Checkpoint not found"));

        BorderCrossing crossing = new BorderCrossing();
        crossing.setId(UUID.randomUUID());
        crossing.setPermit(permit);
        crossing.setCheckpoint(checkpoint);
        crossing.setCrossingDate(LocalDateTime.now());

        permit.setUsed(true);
        permit.setStatus(DocStatus.USED);

        citesPermitRepository.save(permit);
        return borderCrossingRepository.save(crossing);
    }
}
