package kg.sh.mnr.service;

import kg.sh.mnr.entity.BorderCrossing;
import kg.sh.mnr.entity.CitesPermit;
import kg.sh.mnr.entity.dict.BorderCheckpoint;
import kg.sh.mnr.model.enums.DocStatus;
import kg.sh.mnr.repository.BorderCheckpointRepository;
import kg.sh.mnr.repository.BorderCrossingRepository;
import kg.sh.mnr.repository.CitesPermitRepository;
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
