package kg.core.mnr.models.entity;

import jakarta.persistence.*;
import kg.core.mnr.models.entity.dict.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Table(name = "incident")
public class Incident {
    @Id
    UUID id;
    @Column(name = "registered_at")
    LocalDate registeredAt;
    @Column(name = "species")
    String species;
    @Column(name = "description")
    String description;
    @Column(name = "count")
    Double count;

    @ManyToOne
    @JoinColumn(name = "unit_of_measurement_id")
    UnitOfMeasurement unitOfMeasurement;  // Связь с таблицей единиц измерения

    @ManyToOne
    @JoinColumn(name = "authority_id")
    Authority authority;

    @ManyToOne
    @JoinColumn(name = "discovery_method_id")
    DiscoveryMethod discoveryMethod;

    @ManyToOne
    @JoinColumn(name = "reason_for_seizure_id")
    ReasonForSeizure reasonForSeizure;

    @ManyToOne
    @JoinColumn(name = "transport_method_id")
    TransportMethod transportMethod;

    @ManyToOne
    @JoinColumn(name = "trade_direction_id")
    TradeDirection tradeDirection;

    @Column(name = "hiding_method")
    private String hidingMethod; // Способ сокрытия

    @Column(name = "suspected_origin_country")
    private String suspectedOriginCountry; // Предполагаемая страна происхождения

    @Column(name = "transit_countries")
    private String transitCountries;

    @Column(name = "final_destination")
    private String finalDestination; // Предполагаемый конечный пункт назначения

    @Column(name = "additional_info")
    private String additionalInfo; // Дополнительная информация

}
