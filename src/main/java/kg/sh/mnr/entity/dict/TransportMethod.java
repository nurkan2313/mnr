package kg.sh.mnr.entity.dict;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "transport_method")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransportMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "method", nullable = false, unique = true)
    private String method;
}
