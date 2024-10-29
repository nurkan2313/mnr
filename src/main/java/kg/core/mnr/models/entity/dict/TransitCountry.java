package kg.core.mnr.models.entity.dict;

import jakarta.persistence.*;
import kg.core.mnr.models.entity.Incident;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "transit_country")
public class TransitCountry {

    @Id
    private Long id;

    @Column(name = "country_name")
    private String countryName;

    @ManyToOne
    @JoinColumn(name = "incident_id")
    private Incident incident;

}
