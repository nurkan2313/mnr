package kg.core.mnr.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PermitViewDto {
    private String id;
    private String companyName;
    private String object;
    private String quantity;
    private String protectionMarkNumber;
    private String type;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String statusDescription;
    private String purpose;
    private String remarks;
    private String importerCountry;
    private String exporterCountry;
    private boolean hasCrossing;
}
