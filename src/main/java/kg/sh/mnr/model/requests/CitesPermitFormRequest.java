package kg.sh.mnr.model.requests;

import kg.sh.mnr.model.enums.DocStatus;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class CitesPermitFormRequest {

    private String issueDateString;  // Строка для даты выдачи
    private String expiryDateString; // Строка для даты окончания

    private LocalDate issueDate;
    private LocalDate expiryDate;

    private String companyName;

    private String object;

    private UUID objectId;
    private Integer unitsId;
    private UUID importId;
    private UUID exportId;
    private String type;

    private Double quantity;

    private Double limiter;

    private String importerCountry;

    private String exporterCountry;

    private String purpose;

    private String remarks;

    private String protectionMarkNumber;

    private DocStatus status; // Enum: used, unused, canceled

    public CitesPermitFormRequest() {}

    public CitesPermitFormRequest(LocalDate issueDate, LocalDate expiryDate, String companyName, String object, Double quantity, String importerCountry, String exporterCountry, String purpose, String remarks, String protectionMarkNumber, DocStatus status) {
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
        this.companyName = companyName;
        this.object = object;
        this.quantity = quantity;
        this.importerCountry = importerCountry;
        this.exporterCountry = exporterCountry;
        this.purpose = purpose;
        this.remarks = remarks;
        this.protectionMarkNumber = protectionMarkNumber;
        this.status = status;
    }

    public CitesPermitFormRequest(String issueDateString, String expiryDateString, LocalDate issueDate, LocalDate expiryDate, String companyName, String object, Double quantity, String importerCountry, String exporterCountry, String purpose, String remarks, String protectionMarkNumber, DocStatus status) {
        this.issueDateString = issueDateString;
        this.expiryDateString = expiryDateString;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
        this.companyName = companyName;
        this.object = object;
        this.quantity = quantity;
        this.importerCountry = importerCountry;
        this.exporterCountry = exporterCountry;
        this.purpose = purpose;
        this.remarks = remarks;
        this.protectionMarkNumber = protectionMarkNumber;
        this.status = status;
    }
}