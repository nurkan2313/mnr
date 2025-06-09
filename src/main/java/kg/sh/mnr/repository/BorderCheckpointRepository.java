package kg.sh.mnr.repository;

import kg.sh.mnr.entity.dict.BorderCheckpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BorderCheckpointRepository extends JpaRepository<BorderCheckpoint, UUID> {
    // Дополнительные методы при необходимости, например:
    BorderCheckpoint findByName(String name);
}
