package kg.sh.mnr.service;

import kg.sh.mnr.entity.dict.Definition;
import kg.sh.mnr.repository.DefinitionRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public Optional<Definition> findById(UUID id) {return  repository.findById(id);}

    public Definition save(Definition definition) {
        return repository.save(definition);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public Optional<Definition> findFirstByNameLikeIgnoreCase(String name) {
        return repository.findFirstByNameLikeIgnoreCase(name);
    }
}
