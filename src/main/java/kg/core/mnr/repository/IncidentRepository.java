package kg.core.mnr.repository;

import kg.core.mnr.models.entity.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IncidentRepository extends JpaRepository<Incident, UUID> {
    @Query(value = "SELECT COUNT(*) AS totalCount FROM incident where registered_at between :from and :to "
            ,nativeQuery = true)
    Integer getAllByRegisteredAtBetween(LocalDateTime from, LocalDateTime to);
}
