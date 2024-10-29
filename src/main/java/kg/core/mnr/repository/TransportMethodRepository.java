package kg.core.mnr.repository;

import kg.core.mnr.models.entity.dict.TransportMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportMethodRepository extends JpaRepository<TransportMethod, Long> {
}
