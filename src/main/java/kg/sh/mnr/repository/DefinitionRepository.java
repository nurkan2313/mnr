package kg.sh.mnr.repository;

import kg.sh.mnr.entity.dict.Definition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface DefinitionRepository extends JpaRepository<Definition, UUID> {
    @Query(value = "SELECT * FROM definitions WHERE LOWER(name) LIKE LOWER(CONCAT('%', :name, '%')) LIMIT 1", nativeQuery = true)
    Optional<Definition> findFirstByNameLikeIgnoreCase(@Param("name") String name);

}
