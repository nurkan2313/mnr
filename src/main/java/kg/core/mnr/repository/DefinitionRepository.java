package kg.core.mnr.repository;

import kg.core.mnr.models.entity.dict.Definition;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface DefinitionRepository extends JpaRepository<Definition, UUID> {
}
