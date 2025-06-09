package kg.sh.mnr.repository;


import kg.sh.mnr.entity.Incident;

import java.time.LocalDateTime;
import java.util.List;

public interface IncidentRepositoryCustom {
    List<Incident> filterByCriteria(String species, String authority, String transportMethod,
                                    String suspectedOriginCountry, String finalDestination, LocalDateTime registeredAt);
}