package kg.core.mnr.repository.em;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import kg.core.mnr.models.entity.CitesPermit;
import kg.core.mnr.models.entity.dict.Country;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CitesPermitRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public List<CitesPermit> filterPermits(
            String permitNumber,
            String protectionNumber,
            String companyName,
            String object,
            String quantity,
            LocalDate startDate, LocalDate endDate,
            String type) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CitesPermit> query = cb.createQuery(CitesPermit.class);
        Root<CitesPermit> permit = query.from(CitesPermit.class);

        List<Predicate> predicates = new ArrayList<>();

        if (permitNumber != null && !permitNumber.isEmpty()) {
            predicates.add(cb.equal(permit.get("id"), permitNumber));
        }
        if (protectionNumber != null && !protectionNumber.isEmpty()) {
            predicates.add(cb.equal(permit.get("protectionMarkNumber"), protectionNumber));
        }
        if (companyName != null && !companyName.isEmpty()) {
            predicates.add(cb.like(cb.lower(permit.get("companyName")), "%" + companyName.toLowerCase() + "%"));
        }
        if (object != null && !object.isEmpty()) {
            predicates.add(cb.like(cb.lower(permit.get("object")), "%" + object.toLowerCase() + "%"));
        }
        if (quantity != null && !quantity.isEmpty()) {
            predicates.add(cb.equal(permit.get("quantity"), quantity));
        }
        if (startDate != null && endDate != null) {
            predicates.add(cb.between(permit.get("issueDate"), startDate, endDate));
        }
        if (type != null && !type.isEmpty()) {
            predicates.add(cb.equal(cb.lower(permit.get("type")), type.toLowerCase()));
        }

        query.select(permit).where(cb.and(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(query).getResultList();
    }

    public List<CitesPermit> filterPermits(
            String permitNumber,
            String protectionNumber,
            String companyName,
            String object,
            String quantity,
            LocalDate startDate, LocalDate endDate) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CitesPermit> query = cb.createQuery(CitesPermit.class);
        Root<CitesPermit> permit = query.from(CitesPermit.class);

        List<Predicate> predicates = new ArrayList<>();

        if (permitNumber != null && !permitNumber.isEmpty()) {
            predicates.add(cb.equal(permit.get("id"), permitNumber));
        }
        if (protectionNumber != null && !protectionNumber.isEmpty()) {
            predicates.add(cb.equal(permit.get("protectionMarkNumber"), protectionNumber));
        }
        if (companyName != null && !companyName.isEmpty()) {
            predicates.add(cb.like(cb.lower(permit.get("companyName")), "%" + companyName.toLowerCase() + "%"));
        }
        if (object != null && !object.isEmpty()) {
            predicates.add(cb.like(cb.lower(permit.get("object")), "%" + object.toLowerCase() + "%"));
        }
        if (quantity != null && !quantity.isEmpty()) {
            predicates.add(cb.equal(permit.get("quantity"), quantity));
        }
        if (startDate != null) {
            Predicate issueDatePredicate = cb.or(
                    cb.isNull(permit.get("issueDate")), // Если дата выпуска NULL, включить
                    cb.greaterThanOrEqualTo(permit.get("issueDate"), startDate) // Или >= startDate
            );
            predicates.add(issueDatePredicate);
        }
        if (endDate != null) {
            Predicate expiryDatePredicate = cb.or(
                    cb.isNull(permit.get("expiryDate")), // Учет NULL значений
                    cb.lessThanOrEqualTo(permit.get("expiryDate"), endDate)
            );
            predicates.add(expiryDatePredicate);
        }

        query.select(permit).where(cb.and(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(query).getResultList();
    }

    public List<CitesPermit> findByCriteria(String region, String importerCountry, String exporterCountry, String object, String exporter, LocalDate startDate, LocalDate endDate) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CitesPermit> query = cb.createQuery(CitesPermit.class);
        Root<CitesPermit> root = query.from(CitesPermit.class);
        List<Predicate> predicates = new ArrayList<>();

        // Filter by region
        if (region != null && !region.isEmpty()) {
            Subquery<String> subquery = query.subquery(String.class);
            Root<Country> countryRoot = subquery.from(Country.class);
            subquery.select(countryRoot.get("name"))
                    .where(cb.equal(cb.lower(countryRoot.get("region")), region.toLowerCase()));

            predicates.add(cb.or(root.get("importerCountry").in(subquery)));
        }

        if (importerCountry != null && !importerCountry.isEmpty()) {
            predicates.add(cb.equal(cb.lower(root.get("importerCountry")), importerCountry.toLowerCase()));
        }
        if (exporterCountry != null && !exporterCountry.isEmpty()) {
            predicates.add(cb.equal(cb.lower(root.get("exporterCountry")), exporterCountry.toLowerCase()));
        }
        if (object != null && !object.isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("object")), "%" + object.toLowerCase() + "%"));
        }
        if (exporter != null && !exporter.isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("companyName")), "%" + exporter.toLowerCase() + "%"));
        }

        // Filter by startDate
        if (startDate != null) {
            Predicate issueDatePredicate = cb.or(
                    cb.isNull(root.get("issueDate")), // Include if issueDate is NULL
                    cb.greaterThanOrEqualTo(root.get("issueDate"), startDate) // Or >= startDate
            );
            predicates.add(issueDatePredicate);
        }

        // Filter by endDate
        if (endDate != null) {
            Predicate expiryDatePredicate = cb.or(
                    cb.isNull(root.get("expiryDate")), // Include if expiryDate is NULL
                    cb.lessThanOrEqualTo(root.get("expiryDate"), endDate) // Or <= endDate
            );
            predicates.add(expiryDatePredicate);
        }

        query.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(query).getResultList();
    }

}
