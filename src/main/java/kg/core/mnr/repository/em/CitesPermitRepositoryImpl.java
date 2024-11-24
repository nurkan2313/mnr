package kg.core.mnr.repository.em;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import kg.core.mnr.models.entity.CitesPermit;
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

    public List<CitesPermit> findByCriteria(String importerCountry, String exporterCountry, String object, String exporter, LocalDate startDate, LocalDate endDate) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CitesPermit> query = cb.createQuery(CitesPermit.class);
        Root<CitesPermit> root = query.from(CitesPermit.class);

        List<Predicate> predicates = new ArrayList<>();

        // Добавляем условия для фильтрации
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
        if (startDate != null) {
            Predicate issueDatePredicate = cb.or(
                    cb.isNull(root.get("issueDate")), // Если дата выпуска NULL, включить
                    cb.greaterThanOrEqualTo(root.get("issueDate"), startDate) // Или >= startDate
            );
            predicates.add(issueDatePredicate);
        }
        if (endDate != null) {
            Predicate expiryDatePredicate = cb.or(
                    cb.isNull(root.get("expiryDate")), // Учет NULL значений
                    cb.lessThanOrEqualTo(root.get("expiryDate"), endDate)
            );
            predicates.add(expiryDatePredicate);
        }

        query.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(query).getResultList();
    }
}
