package kg.sh.mnr.repository;

import kg.sh.mnr.entity.dict.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CountryRepository extends JpaRepository<Country, UUID> {
    @Query(value = """
            select u from Country u
            where u.name like %:name% or u.shortName like %:name% or u.code like %:name%
            """)
    Page<Country> searchBy(String name, Pageable pageable);

    @Query(value = "SELECT * FROM country WHERE LOWER(name) LIKE LOWER(CONCAT('%', :country, '%')) LIMIT 10", nativeQuery = true)
    List<Country> getSimilarCountries(@Param("country") String country);

    @Query("SELECT c FROM Country c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Country> searchCountriesByName(@Param("query") String query);

    Optional<Country> findByName(String query);

    @Query("SELECT c.region FROM Country c WHERE c.name = :name")
    String findRegionByCountryName(@Param("name") String name);

    List<Country> findTop5ByNameContainingIgnoreCase(String name);

}