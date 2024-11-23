package kg.core.mnr.models.dto.requests;

import kg.core.mnr.models.dto.enums.DocStatus;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class CitesPermitFormRequest {

    private String issueDateString;  // Строка для даты выдачи
    private String expiryDateString; // Строка для даты окончания

    private LocalDate issueDate;
    private LocalDate expiryDate;

    @NotBlank(message = "Название компании не может быть пустым")
    private String companyName;

    @NotBlank(message = "Описание объекта не может быть пустым")
    private String object;

    private UUID objectId;
    private Integer unitsId;
    private UUID importId;
    private UUID exportId;
    private String type;

    @NotNull(message = "Количество обязательно")
    @Positive(message = "Количество должно быть положительным числом")
    private Double quantity;

    @NotNull(message = "Количество обязательно")
    @Positive(message = "Количество должно быть положительным числом")
    private Double limiter;

    @NotBlank(message = "Страна импортера обязательна")
    private String importerCountry;

    @NotBlank(message = "Страна экспортера обязательна")
    private String exporterCountry;

    @NotBlank(message = "Цель обязательна")
    private String purpose;

    private String remarks;

    @NotBlank(message = "Номер охранной марки обязателен")
    private String protectionMarkNumber;

    @NotNull(message = "Статус документа обязателен")
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