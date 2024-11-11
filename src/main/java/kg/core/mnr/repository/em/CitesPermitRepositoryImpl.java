package kg.core.mnr.repository.em;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import kg.core.mnr.models.dto.enums.DocStatus;
import kg.core.mnr.models.entity.CitesPermit;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class CitesPermitRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public List<CitesPermit> filterPermits(
            String permitNumber,
            String protectionNumber,
            String companyName,
            String object,
            Double quantity,
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
        if (quantity != null) {
            predicates.add(cb.equal(permit.get("quantity"), quantity));
        }
        if (startDate != null && endDate != null) {
            predicates.add(cb.between(permit.get("issueDate"), startDate.atStartOfDay(), endDate.atTime(23, 59, 59)));
        }

        query.select(permit).where(cb.and(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(query).getResultList();
    }

    public List<CitesPermit> findByCriteria(String importerCountry, String exporterCountry, String object, LocalDateTime startDate, LocalDateTime endDate) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CitesPermit> query = cb.createQuery(CitesPermit.class);
        Root<CitesPermit> root = query.from(CitesPermit.class);

        List<Predicate> predicates = new ArrayList<>();

        // Добавляем условия для фильтрации
        if (importerCountry != null && !importerCountry.isEmpty()) {
            predicates.add(cb.equal(root.get("importerCountry"), importerCountry));
        }
        if (exporterCountry != null && !exporterCountry.isEmpty()) {
            predicates.add(cb.equal(root.get("exporterCountry"), exporterCountry));
        }
        if (object != null && !object.isEmpty()) {
            predicates.add(cb.like(root.get("object"), "%" + object + "%"));
        }
        if (startDate != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("issueDate"), startDate));
        }
        if (endDate != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("expiryDate"), endDate));
        }

        query.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(query).getResultList();
    }
}
