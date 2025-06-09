package kg.sh.mnr.repository;

import kg.sh.mnr.entity.dict.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
