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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class CitesPermitRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public List<CitesPermit> filterPermits(
            String permitNumber,
            String companyName,
            String protectionNumber,
            String object,
            Double quantity,
            LocalDate startDate, LocalDate endDate) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CitesPermit> query = cb.createQuery(CitesPermit.class);
        Root<CitesPermit> permit = query.from(CitesPermit.class);

        List<Predicate> predicates = new ArrayList<>();

        if (permitNumber != null && !permitNumber.isEmpty()) {
            predicates.add(cb.equal(permit.get("id"), UUID.fromString(permitNumber)));
        }
        if (companyName != null && !companyName.isEmpty()) {
            predicates.add(cb.like(permit.get("companyName"), "%" + companyName + "%"));
        }
        if (protectionNumber != null && !protectionNumber.isEmpty()) {
            predicates.add(cb.equal(permit.get("protectionMarkNumber"), protectionNumber));
        }
        if (object != null && !object.isEmpty()) {
            predicates.add(cb.equal(permit.get("object"), object));
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
}
