package kg.core.mnr.repository;

import kg.core.mnr.models.entity.dict.BorderCheckpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface BorderCheckpointRepository extends JpaRepository<BorderCheckpoint, UUID> {
    // Дополнительные методы при необходимости, например:
    BorderCheckpoint findByName(String name);
}
