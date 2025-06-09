package kg.sh.mnr.repository;

import kg.sh.mnr.entity.dict.DiscoveryMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscoveryMethodRepository extends JpaRepository<DiscoveryMethod, Long>
{

}
