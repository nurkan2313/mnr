package kg.core.mnr.service;

import kg.core.mnr.models.entity.dict.Definition;
import kg.core.mnr.repository.DefinitionRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class DefinitionService {
    private final DefinitionRepository repository;

    public DefinitionService(DefinitionRepository repository) {
        this.repository = repository;
    }

    public List<Definition> getAll() {
        return repository.findAll();
    }

    public Definition getById(UUID id) {
        return repository.findById(id).orElseThrow();
    }

    public Definition save(Definition definition) {
        return repository.save(definition);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
