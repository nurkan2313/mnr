package kg.core.mnr.service;

import kg.core.mnr.models.dto.CitesPermitUpdateDTO;
import kg.core.mnr.models.entity.CitesPermit;
import kg.core.mnr.repository.CitesPermitRepository;
import kg.core.mnr.repository.em.CitesPermitRepositoryImpl;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class CitesPermitService {

    private CitesPermitRepository citesPermitRepository;
    private CitesPermitRepositoryImpl citesPermitRepositoryI;

    @Transactional
    public void saveAll(List<CitesPermit> citesPermits) {
        for (CitesPermit citesPermit : citesPermits) {
            if (citesPermit.getId() == null) {
                citesPermit.setId(UUID.randomUUID().toString());
            }
            citesPermit.setId(citesPermit.getId());
        }
        citesPermitRepository.saveAll(citesPermits);
    }

    public List<CitesPermit> filterPermits(
            String permitNumber,
            String protectionNumber,
            String companyName,
            String object,
            Double quantity,
            LocalDate startDate,
            LocalDate endDate,
            String type) {
        // Логика фильтрации
        return citesPermitRepositoryI.filterPermits(
                permitNumber,
                protectionNumber,
                companyName,
                object,
                quantity,
                startDate,
                endDate,
                type);
    }

    public List<CitesPermit> filterPermits(
            String permitNumber,
            String protectionNumber,
            String companyName,
            String object,
            Double quantity,
            LocalDate startDate,
            LocalDate endDate
           ) {
        // Логика фильтрации
        return citesPermitRepositoryI.filterPermits(
                permitNumber,
                protectionNumber,
                companyName,
                object,
                quantity,
                startDate,
                endDate
                );
    }

    public Page<CitesPermit> getAllPermits(Pageable pageable) {
        return citesPermitRepository.findAll(pageable);
    }

    public CitesPermit getPermitById(String id) {
        return citesPermitRepository.findById(id).orElse(null);
    }

    @Transactional
    public CitesPermit updateCitesPermit(CitesPermitUpdateDTO dto) {
        Optional<CitesPermit> optionalPermit = citesPermitRepository.findById(dto.getId());

        if (optionalPermit.isPresent()) {
            CitesPermit permit = optionalPermit.get();

            // Обновляем поля
            permit.setIssueDate(dto.getIssueDate().atStartOfDay());
            permit.setExpiryDate(dto.getExpiryDate().atStartOfDay());
            permit.setCompanyName(dto.getCompanyName());
            permit.setObject(dto.getObject());
            permit.setQuantity(dto.getQuantity());
            permit.setImporterCountry(dto.getImporterCountry());
            permit.setExporterCountry(dto.getExporterCountry());
            permit.setPurpose(dto.getPurpose());
            permit.setRemarks(dto.getRemarks());
            permit.setProtectionMarkNumber(dto.getProtectionMarkNumber());
            permit.setStatus(dto.getStatus());

            // Сохраняем обновленное разрешение
            return citesPermitRepository.save(permit);
        } else {
            throw new RuntimeException("Разрешение с ID " + dto.getId() + " не найдено.");
        }
    }

    @Transactional
    public void createPermit(CitesPermit permit) {
        // Получаем все записи и берём последнюю
        List<CitesPermit> all = citesPermitRepository.findAll();

        String lastId = all.isEmpty() ? "24KG000" : all.get(all.size() - 1).getId();

        // Увеличиваем значение id
        String newId = incrementId(lastId);

        // Присваиваем новое id объекту permit
        permit.setId(newId);
        citesPermitRepository.save(permit);
    }

    // Метод для увеличения значения id
    private String incrementId(String lastId) {
        // Извлекаем числовую часть id
        String prefix = lastId.substring(0, 5); // "23KG"
        String numericPart = lastId.substring(5); // "001"

        // Увеличиваем числовую часть на 1
        int numericValue = Integer.parseInt(numericPart) + 1;

        // Форматируем обратно в строку с ведущими нулями
        String newNumericPart = String.format("%03d", numericValue); // сохраняем 3 цифры, например "002"

        return prefix + newNumericPart;
    }

}
