package kg.sh.mnr.repository;

import kg.sh.mnr.entity.dict.ReasonForSeizure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReasonForSeizureRepository extends JpaRepository<ReasonForSeizure, Long> {
}
