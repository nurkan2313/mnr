package kg.core.mnr.models.entity;

import jakarta.persistence.*;
import kg.core.mnr.models.dto.enums.DocStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cites_permit")
public class CitesPermit {
    @Id
    private String id;
    @Column(name = "issue_date")
    private LocalDate issueDate;
    @Column(name = "expiry_date")
    private LocalDate expiryDate;
    @Column(name = "company_name")
    private String companyName;
    private String object;
    @Column(name = "object_id")
    private UUID objectId;
    private String type;
    private String quantity;
    private String measure;
    private Double limiter;
    @Column(name = "importer_country")
    private String importerCountry;
    @Column(name = "import_id")
    private UUID importId;
    @Column(name = "export_id")
    private UUID exportId;
    @Column(name = "exporter_country")
    private String exporterCountry;
    private String purpose;
    private String remarks;
    @Column(name = "protection_mark_number")
    private String protectionMarkNumber;
    @Column(name = "units_id")
    private Integer unitsId;
    @OneToMany(mappedBy = "permit")
    private List<BorderCrossing> borderCrossings;
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
    private String statusDescription; // Описание с// татуса

    public CitesPermit(String number, LocalDate now, LocalDate localDateTime, String s, String flora, String number1, String kg, String usa, String canada, DocStatus docStatus, String research, String none, String number2) {
        this.id = UUID.randomUUID().toString();
        this.issueDate = now;
        this.expiryDate = localDateTime;
        this.companyName = s;
        this.object = "Сибирский козерог";
        this.quantity = number;
        this.measure = s;
        this.limiter = Double.parseDouble(flora);
        this.importerCountry = usa;
        this.importId = UUID.randomUUID();
        this.exportId = UUID.randomUUID();
        this.exporterCountry = kg;
        this.purpose = s;
        this.remarks = s;
        this.protectionMarkNumber = number1;
        this.unitsId = Integer.parseInt(number2);
        this.status = docStatus;
    }
}
