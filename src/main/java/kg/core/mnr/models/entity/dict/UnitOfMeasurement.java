package kg.core.mnr.models.entity.dict;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "unit_of_measurement")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UnitOfMeasurement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unit_of_measurement_gen")
    @SequenceGenerator(name = "unit_of_measurement_gen", sequenceName = "unit_of_measurement_seq", allocationSize = 1)
    private Long id;

    @Column(name = "unit", nullable = false, unique = true)
    private String unit;
}
