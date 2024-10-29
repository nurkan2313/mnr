package kg.core.mnr.models.entity;

import jakarta.persistence.*;
import kg.core.mnr.models.dto.enums.DocStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cites_permit")
public class CitesPermit {
    @Id
    private UUID id;
    @Column(name = "issue_date")
    private LocalDateTime issueDate;
    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;
    @Column(name = "company_name")
    private String companyName;
    private String object;
    @Column(name = "object_id")
    private UUID objectId;
    private Double quantity;
    private Double limiter;
    @Column(name = "importer_country")
    private String importerCountry;
    @Column(name = "exporter_country")
    private String exporterCountry;
    private String purpose;
    private String remarks;
    @Column(name = "protection_mark_number")
    private String protectionMarkNumber;
    // Геттер для статуса
    @Setter
    @Getter
    @Enumerated(EnumType.STRING)
    private DocStatus status; // used, unused, canceled
    @Column(name = "pdf_file_name")
    private String pdfFileName;

    @Setter
    @Getter
    @Transient
    private boolean isUsed;
    @Setter
    @Getter
    @Transient
    private boolean isUnused;

    @Setter
    @Getter
    @Transient
    private String statusDescription; // Описание статуса

}
