package kg.sh.mnr.entity.dict;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "discovery_method")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscoveryMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "method", nullable = false, unique = true)
    private String method;
}
