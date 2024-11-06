package kg.core.mnr.repository;

import kg.core.mnr.models.entity.dict.UnitOfMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitOfMeasurementRepository extends JpaRepository<UnitOfMeasurement, Long> {
    @Query("SELECT u FROM UnitOfMeasurement u WHERE LOWER(u.unit) LIKE LOWER(CONCAT('%', :unit, '%'))")
    List<UnitOfMeasurement> searchUnitsByName(@Param("unit") String unit);

    UnitOfMeasurement findByUnit(String name);

    List<UnitOfMeasurement> findByUnitContainingIgnoreCase(String unit);

}
