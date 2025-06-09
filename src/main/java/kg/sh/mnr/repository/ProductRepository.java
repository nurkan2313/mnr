package kg.sh.mnr.repository;

import kg.sh.mnr.entity.dict.Product;
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
public interface ProductRepository extends JpaRepository<Product, UUID> {
    @Query(value = "SELECT * FROM products WHERE LOWER(description) LIKE LOWER(CONCAT('%', :species, '%')) LIMIT 10", nativeQuery = true)
    List<Product> findSimilarProducts(@Param("species") String species);

    Page<Product> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM products WHERE " +
            "(LOWER(description) LIKE LOWER(CONCAT('%', :species, '%')) OR :species IS NULL) " +
            "AND (country_id = :country OR :country IS NULL) LIMIT 10", nativeQuery = true)
    List<Product> searchProductsAndCountries(@Param("species") String species, @Param("country") String country);

    // Фильтрация по описанию и коду
    Page<Product> findByDescriptionContainingAndCodeContaining(String description, String code, Pageable pageable);

    // Фильтрация только по описанию
    @Query(value = "SELECT * FROM products WHERE " +
            "(LOWER(description) LIKE LOWER(CONCAT('%', :description, '%')) OR :description IS NULL) ", nativeQuery = true)
    Page<Product> findByDescriptionContaining(String description, Pageable pageable);

    // Фильтрация только по коду
    Page<Product> findByCodeContaining(String code, Pageable pageable);

    @Query(value = "SELECT * FROM products WHERE " +
            "(LOWER(description) LIKE LOWER(CONCAT('%', :description, '%')) OR :description IS NULL) ", nativeQuery = true)
    Optional<Product> findByDescription(String description);

    @Query(value = "SELECT * FROM products WHERE LOWER(description) LIKE LOWER(CONCAT('%', :description, '%')) LIMIT 1", nativeQuery = true)
    Optional<Product> findFirstByDescription(@Param("description") String description);

}