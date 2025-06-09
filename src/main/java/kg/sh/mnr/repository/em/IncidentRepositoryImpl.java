package kg.sh.mnr.repository.em;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import kg.sh.mnr.entity.Incident;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class IncidentRepositoryImpl {
    @PersistenceContext
    private EntityManager entityManager;

    public Page<Incident> filterByCriteria(String species, String authority, String transportMethod,
                                           String suspectedOriginCountry, String finalDestination,
                                           LocalDate registeredAt, Pageable pageable) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Incident> query = cb.createQuery(Incident.class);
        Root<Incident> incident = query.from(Incident.class);

        List<Predicate> predicates = new ArrayList<>();

        // Добавляем фильтры, если параметры не null
        if (species != null && !species.isEmpty()) {
            predicates.add(cb.equal(incident.get("species"), species));
        }
        if (authority != null && !authority.isEmpty()) {
            try {
                // Пытаемся преобразовать authority в целое число для поиска по ID
                Integer authorityId = Integer.parseInt(authority);
                predicates.add(cb.equal(incident.get("authority").get("id"), authorityId));
            } catch (NumberFormatException e) {
                // Если authority не является числом, возможно, это имя или другая строка
                predicates.add(cb.equal(incident.get("authority").get("name"), authority));
            }
        }
        if (transportMethod != null && !transportMethod.isEmpty()) {
            predicates.add(cb.equal(incident.get("transportMethod").get("method"), transportMethod));
        }
        if (suspectedOriginCountry != null && !suspectedOriginCountry.isEmpty()) {
            predicates.add(cb.equal(incident.get("suspectedOriginCountry"), suspectedOriginCountry));
        }
        if (finalDestination != null && !finalDestination.isEmpty()) {
            predicates.add(cb.equal(incident.get("finalDestination"), finalDestination));
        }
        if (registeredAt != null) {
            // Используем LocalDate для диапазона
            predicates.add(cb.equal(cb.function("DATE", LocalDate.class, incident.get("registeredAt")), registeredAt));
        }


        // Применение фильтров
        query.select(incident).where(cb.and(predicates.toArray(new Predicate[0])));

        // Выполнение запроса с пагинацией
        List<Incident> resultList = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())   // Начало страницы
                .setMaxResults(pageable.getPageSize())        // Количество элементов на странице
                .getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Incident> countRoot = countQuery.from(Incident.class);
        countQuery.select(cb.count(countRoot)).where(cb.and(predicates.toArray(new Predicate[0])));

        Long totalElements;
        try {
            totalElements = entityManager.createQuery(countQuery).getSingleResult();
        } catch (NoResultException e) {
            totalElements = 0L; // Если результатов нет
        }


        // Возвращаем объект Page
        return new PageImpl<>(resultList, pageable, totalElements);
    }
}