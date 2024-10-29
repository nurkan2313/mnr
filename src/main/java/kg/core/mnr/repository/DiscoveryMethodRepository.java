package kg.core.mnr.repository;

import kg.core.mnr.models.entity.dict.DiscoveryMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscoveryMethodRepository extends JpaRepository<DiscoveryMethod, Long>
{

}
