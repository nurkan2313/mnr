package kg.core.mnr.repository.em;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import kg.core.mnr.models.entity.Incident;
import kg.core.mnr.repository.IncidentRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class IncidentRepositoryImpl {
    @PersistenceContext
    private EntityManager entityManager;

    public Page<Incident> filterByCriteria(String species, String authority, String transportMethod,
                                           String suspectedOriginCountry, String finalDestination,
                                           LocalDateTime registeredAt, Pageable pageable) {

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
            // Создаем диапазон времени для дня (с начала дня до конца дня)
            LocalDateTime startOfDay = registeredAt.toLocalDate().atStartOfDay();
            LocalDateTime endOfDay = registeredAt.toLocalDate().atTime(23, 59, 59);
            predicates.add(cb.between(incident.get("registeredAt"), startOfDay, endOfDay));
        }

        // Применение фильтров
        query.select(incident).where(cb.and(predicates.toArray(new Predicate[0])));

        // Выполнение запроса с пагинацией
        List<Incident> resultList = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())   // Начало страницы
                .setMaxResults(pageable.getPageSize())        // Количество элементов на странице
                .getResultList();

        // Получение общего количества записей для пагинации
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Incident> countRoot = countQuery.from(Incident.class);
        countQuery.select(cb.count(countRoot)).where(cb.and(predicates.toArray(new Predicate[0])));
        Long totalElements = entityManager.createQuery(countQuery).getSingleResult();

        // Возвращаем объект Page
        return new PageImpl<>(resultList, pageable, totalElements);
    }
}