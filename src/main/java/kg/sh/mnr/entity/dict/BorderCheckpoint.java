package kg.sh.mnr.entity.dict;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "border_checkpoint")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BorderCheckpoint {

    @Id
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "code")
    private String code;

    @Column(name = "is_active")
    private boolean isActive;
}
