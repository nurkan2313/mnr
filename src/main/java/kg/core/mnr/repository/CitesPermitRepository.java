package kg.core.mnr.repository;

import kg.core.mnr.models.entity.CitesPermit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CitesPermitRepository extends JpaRepository<CitesPermit, UUID> {

    @Query("select count(c.importerCountry) from CitesPermit c where c.issueDate between :from and :to")
    Long countByImporter(LocalDateTime from, LocalDateTime to);

    @Query(value = "SELECT object AS species, COUNT(*) AS totalCount FROM cites_permit WHERE issue_date BETWEEN :from AND :to GROUP BY object ORDER BY totalCount DESC",
            countQuery = "SELECT COUNT(DISTINCT object) FROM cites_permit WHERE issue_date BETWEEN :from AND :to",
            nativeQuery = true)
    Page<Map<String, Object>> getCitesPermitByCountry(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to, Pageable pageable);

    @Query(value = "SELECT * FROM cites_permit"
            ,nativeQuery = true)
    List<CitesPermit> getPermitesReport();

    Page<CitesPermit> findAllByIssueDateBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

}
