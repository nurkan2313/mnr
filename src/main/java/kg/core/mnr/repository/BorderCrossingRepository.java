package kg.core.mnr.repository;

import kg.core.mnr.models.entity.BorderCrossing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface BorderCrossingRepository extends JpaRepository<BorderCrossing, UUID> {
    // Дополнительные методы при необходимости
    List<BorderCrossing> findByPermitId(String permitId);

    @Query("SELECT b FROM BorderCrossing b WHERE " +
            "(:permitId IS NULL OR b.permit.id = :permitId) " +
            "AND (:checkpoint IS NULL OR b.checkpoint = :checkpoint) " +
            "AND (:startDate IS NULL OR b.crossingDate >= :startDate) " +
            "AND (:endDate IS NULL OR b.crossingDate <= :endDate)")
    List<BorderCrossing> findByCriteria(@Param("permitId") String permitId,
                                        @Param("checkpoint") String checkpoint,
                                        @Param("startDate") LocalDateTime startDate,
                                        @Param("endDate") LocalDateTime endDate);
}
