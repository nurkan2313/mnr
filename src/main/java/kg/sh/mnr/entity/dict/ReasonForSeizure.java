package kg.sh.mnr.entity.dict;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reason_for_seizure")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReasonForSeizure {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "reason", nullable = false, unique = true)
    private String reason;
}
