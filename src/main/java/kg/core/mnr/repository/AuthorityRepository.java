package kg.core.mnr.repository;

import kg.core.mnr.models.entity.dict.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
