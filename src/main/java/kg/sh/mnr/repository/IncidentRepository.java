package kg.sh.mnr.repository;

import kg.sh.mnr.entity.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.UUID;

public interface IncidentRepository extends JpaRepository<Incident, UUID> {
    @Query(value = "SELECT COUNT(*) AS totalCount FROM incident "
            ,nativeQuery = true)
    Integer getAllByRegisteredAtBetween();

    @Query("SELECT COUNT(i) FROM Incident i WHERE i.registeredAt >= :fromDate")
    long countByRegisteredAtAfter(@Param("fromDate") LocalDate fromDate);

}
