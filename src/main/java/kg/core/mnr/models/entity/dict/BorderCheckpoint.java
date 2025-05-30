package kg.core.mnr.models.entity.dict;

import jakarta.persistence.*;
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
