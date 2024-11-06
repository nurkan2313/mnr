package kg.core.mnr.models.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kg.core.mnr.models.dto.enums.DocStatus;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class CitesPermitUpdateDTO {
    private String id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) // Указываем формат для LocalDateTime
    private LocalDate issueDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) // Указываем формат для LocalDateTime
    private LocalDate expiryDate;
    private String companyName;
    private String object;
    private Integer unitsId;
    private String quantity;
    private String importerCountry;
    private String exporterCountry;
    private String purpose;
    private String remarks;
    private String protectionMarkNumber;
    @Enumerated(EnumType.STRING)
    private DocStatus status; // Enum for status (used, unused, canceled)
}
