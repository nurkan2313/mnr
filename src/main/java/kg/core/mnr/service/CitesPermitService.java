package kg.core.mnr.service;

import kg.core.mnr.models.dto.CitesPermitUpdateDTO;
import kg.core.mnr.models.dto.enums.DocStatus;
import kg.core.mnr.models.entity.CitesPermit;
import kg.core.mnr.repository.CitesPermitRepository;
import kg.core.mnr.repository.em.CitesPermitRepositoryImpl;
import kg.core.mnr.utils.ImageCompressor;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class CitesPermitService {

    private CitesPermitRepository citesPermitRepository;
    private CitesPermitRepositoryImpl citesPermitRepositoryI;

    public List<CitesPermit> filterPermits(
            String permitNumber,
            String protectionNumber,
            String companyName,
            String object,
            Double quantity,
            LocalDate startDate,
            LocalDate endDate) {
        // Логика фильтрации
        return citesPermitRepositoryI.filterPermits(
                permitNumber,
                companyName,
                protectionNumber,
                object,
                quantity,
                startDate,
                endDate);
    }

    public Page<CitesPermit> getAllPermits(Pageable pageable) {
        return citesPermitRepository.findAll(pageable);
    }

    public CitesPermit getPermitById(UUID id) {
        return citesPermitRepository.findById(id).orElse(null);
    }

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

    public void createPermit(CitesPermit permit) {
        permit.setId(UUID.randomUUID());
        citesPermitRepository.save(permit);
    }

    public void saveImage(UUID permitId) throws IOException {
        Optional<CitesPermit> permitOptional = citesPermitRepository.findById(permitId);
        if (permitOptional.isPresent()) {
            CitesPermit permit = permitOptional.get();
            citesPermitRepository.save(permit);
        } else {
            throw new RuntimeException("Permit not found");
        }
    }

}
