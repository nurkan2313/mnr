package kg.sh.mnr.controller;

import kg.sh.mnr.entity.BorderCrossing;
import kg.sh.mnr.entity.CitesPermit;
import kg.sh.mnr.entity.dict.BorderCheckpoint;
import kg.sh.mnr.model.enums.DocStatus;
import kg.sh.mnr.repository.BorderCheckpointRepository;
import kg.sh.mnr.repository.BorderCrossingRepository;
import kg.sh.mnr.repository.CitesPermitRepository;
import kg.sh.mnr.service.BorderCrossingService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/api/crossings")
public class BorderCrossingController {

    private final BorderCrossingService borderCrossingService;
    private final BorderCheckpointRepository borderCheckpointRepository;
    private final CitesPermitRepository citesPermitRepository;
    private final BorderCrossingRepository borderCrossingRepository;

    public BorderCrossingController(BorderCrossingService borderCrossingService, BorderCheckpointRepository borderCheckpointRepository, CitesPermitRepository citesPermitRepository, BorderCrossingRepository borderCrossingRepository) {
        this.borderCrossingService = borderCrossingService;
        this.borderCheckpointRepository = borderCheckpointRepository;
        this.citesPermitRepository = citesPermitRepository;
        this.borderCrossingRepository = borderCrossingRepository;
    }

    // Запись новой отметки о пересечении границы
    @PostMapping("/{permitId}")
    public ResponseEntity<BorderCrossing> recordCrossing(
            @PathVariable String permitId,
            @RequestParam(required = false) UUID checkpointId,
            @RequestParam(required = false) String checkpointName) {

        BorderCheckpoint checkpoint;

        if ("other".equalsIgnoreCase(String.valueOf(checkpointId)) || checkpointId == null) {
            if (checkpointName == null || checkpointName.isBlank()) {
                throw new IllegalArgumentException("Название КПП обязательно при выборе 'Другое'");
            }

            checkpoint = new BorderCheckpoint();
            checkpoint.setId(UUID.randomUUID());
            checkpoint.setName(checkpointName);
            checkpoint.setActive(true);
            borderCheckpointRepository.save(checkpoint);
        } else {
            checkpoint = borderCheckpointRepository.findById(checkpointId)
                    .orElseThrow(() -> new RuntimeException("КПП не найден"));
        }

        CitesPermit permit = citesPermitRepository.findById(permitId)
                .orElseThrow(() -> new RuntimeException("Разрешение не найдено"));

        BorderCrossing crossing = new BorderCrossing();
        crossing.setId(UUID.randomUUID());
        crossing.setPermit(permit);
        crossing.setCheckpoint(checkpoint);
        crossing.setCrossingDate(LocalDateTime.now());

        permit.setUsed(true);
        permit.setStatus(DocStatus.USED);

        citesPermitRepository.save(permit);
        return ResponseEntity.ok(borderCrossingRepository.save(crossing));
    }


    // Получение всех пересечений по конкретному разрешению
    @GetMapping("/permit/{permitId}")
    public ResponseEntity<List<BorderCrossing>> getCrossingsByPermit(@PathVariable String permitId) {
        List<BorderCrossing> crossings = borderCrossingRepository.findByPermitId(permitId);
        return ResponseEntity.ok(crossings);
    }
}
