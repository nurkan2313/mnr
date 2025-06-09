package kg.sh.mnr.repository;

import kg.sh.mnr.entity.dict.TransportMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportMethodRepository extends JpaRepository<TransportMethod, Long> {
}
