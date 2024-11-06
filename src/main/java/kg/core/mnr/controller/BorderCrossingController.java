package kg.core.mnr.controller;

import kg.core.mnr.models.entity.BorderCrossing;
import kg.core.mnr.repository.BorderCrossingRepository;
import kg.core.mnr.service.BorderCrossingService;
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
            @RequestParam String checkpoint) {
        
        BorderCrossing crossing = borderCrossingService.recordCrossing(permitId, checkpoint);
        return ResponseEntity.ok(crossing);
    }

    // Получение всех пересечений по конкретному разрешению
    @GetMapping("/permit/{permitId}")
    public ResponseEntity<List<BorderCrossing>> getCrossingsByPermit(@PathVariable String permitId) {
        List<BorderCrossing> crossings = borderCrossingRepository.findByPermitId(permitId);
        return ResponseEntity.ok(crossings);
    }
}
