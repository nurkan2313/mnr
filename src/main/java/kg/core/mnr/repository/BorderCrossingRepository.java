package kg.core.mnr.repository;

import kg.core.mnr.models.entity.BorderCrossing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BorderCrossingRepository extends JpaRepository<BorderCrossing, UUID> {
    // Дополнительные методы при необходимости
    List<BorderCrossing> findByPermitId(UUID permitId);
}
