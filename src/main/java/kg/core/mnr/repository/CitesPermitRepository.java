package kg.core.mnr.repository;

import kg.core.mnr.models.entity.CitesPermit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface CitesPermitRepository extends JpaRepository<CitesPermit, String> {

    @Query("select count(c.importerCountry) from CitesPermit c ")
    Long countByImporter();

    @Query(value = "SELECT object AS species, COUNT(*) AS totalCount FROM cites_permit GROUP BY object ORDER BY totalCount DESC",
            countQuery = "SELECT COUNT(DISTINCT object) FROM cites_permit ",
            nativeQuery = true)
    Page<Map<String, Object>> getCitesPermitByCountry(Pageable pageable);

    @Query(value = "SELECT * FROM cites_permit"
            ,nativeQuery = true)
    List<CitesPermit> getPermitesReport();

    @Query(value = "SELECT * FROM cites_permit  WHERE importer_country = :importerCountry AND exporter_country = :exporterCountry AND object = :object AND issue_date >= :startDate AND expiry_date <= :endDate",
        nativeQuery = true
    )
    List<CitesPermit> findByCriteria(@Param("importerCountry") String importerCountry,
                                     @Param("exporterCountry") String exporterCountry,
                                     @Param("object") String object,
                                     @Param("startDate") LocalDateTime startDate,
                                     @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COUNT(c) FROM CitesPermit c WHERE c.issueDate >= :fromDate")
    long countByIssueDateAfter(@Param("fromDate") LocalDate fromDate);

    @Query("SELECT COALESCE(SUM(CAST(c.quantity AS double)), 0) FROM CitesPermit c WHERE c.issueDate >= :fromDate")
    double sumImportVolumeSince(@Param("fromDate") LocalDate fromDate);

    Page<CitesPermit> findAll(Pageable pageable);

}