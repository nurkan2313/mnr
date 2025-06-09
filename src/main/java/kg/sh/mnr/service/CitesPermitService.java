package kg.sh.mnr.service;

import kg.sh.mnr.entity.CitesPermit;
import kg.sh.mnr.model.CitesPermitUpdateDTO;
import kg.sh.mnr.model.PermitViewDto;
import kg.sh.mnr.repository.CitesPermitRepository;
import kg.sh.mnr.repository.em.CitesPermitRepositoryImpl;
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
            String quantity,
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
            String quantity,
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
            permit.setIssueDate(dto.getIssueDate());
            permit.setExpiryDate(dto.getExpiryDate());
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
        permit.setId(UUID.randomUUID().toString());
        citesPermitRepository.save(permit);
    }

    public Optional<CitesPermit> findByKeyFields(String protectionMarkNumber) {
        return citesPermitRepository.findByProtectionNumber(protectionMarkNumber);
    }

    @Transactional
    public void saveOrUpdate(CitesPermit permit) {
        if (permit.getId() == null) {
            permit.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        }
        citesPermitRepository.save(permit);
    }

    public PermitViewDto mapToDto(CitesPermit permit) {
        PermitViewDto dto = new PermitViewDto();
        dto.setId(permit.getId());
        dto.setCompanyName(permit.getCompanyName());
        dto.setObject(permit.getObject());
        dto.setQuantity(permit.getQuantity());
        dto.setProtectionMarkNumber(permit.getProtectionMarkNumber());
        dto.setType(permit.getType());
        dto.setIssueDate(permit.getIssueDate());
        dto.setExpiryDate(permit.getExpiryDate());
        dto.setStatusDescription(permit.getStatus().getDescription());
        dto.setPurpose(permit.getPurpose());
        dto.setRemarks(permit.getRemarks());
        dto.setImporterCountry(permit.getImporterCountry());
        dto.setExporterCountry(permit.getExporterCountry());
        dto.setHasCrossing(permit.getBorderCrossings() != null && !permit.getBorderCrossings().isEmpty());
        return dto;
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
