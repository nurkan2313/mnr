package kg.sh.mnr.controller;

import kg.sh.mnr.entity.BorderCrossing;
import kg.sh.mnr.repository.BorderCrossingRepository;
import kg.sh.mnr.service.BorderCrossingService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/api/crossings")
public class BorderCrossingController {

    private final BorderCrossingService borderCrossingService;
    private final BorderCrossingRepository borderCrossingRepository;

    public BorderCrossingController(BorderCrossingService borderCrossingService, BorderCrossingRepository borderCrossingRepository) {
        this.borderCrossingService = borderCrossingService;
        this.borderCrossingRepository = borderCrossingRepository;
    }

    // Запись новой отметки о пересечении границы
    @PostMapping("/{permitId}")
    public ResponseEntity<BorderCrossing> recordCrossing(
            @PathVariable String permitId,
            @RequestParam UUID checkpointId) {
        
        BorderCrossing crossing = borderCrossingService.recordCrossing(permitId, checkpointId);
        return ResponseEntity.ok(crossing);
    }

    // Получение всех пересечений по конкретному разрешению
    @GetMapping("/permit/{permitId}")
    public ResponseEntity<List<BorderCrossing>> getCrossingsByPermit(@PathVariable String permitId) {
        List<BorderCrossing> crossings = borderCrossingRepository.findByPermitId(permitId);
        return ResponseEntity.ok(crossings);
    }
}
