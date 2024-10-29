package kg.core.mnr.models.entity.dict;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "trade_direction")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TradeDirection {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "direction", nullable = false, unique = true)
    private String direction;
}
