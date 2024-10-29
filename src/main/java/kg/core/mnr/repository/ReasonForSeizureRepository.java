package kg.core.mnr.repository;

import kg.core.mnr.models.entity.dict.ReasonForSeizure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReasonForSeizureRepository extends JpaRepository<ReasonForSeizure, Long> {
}
