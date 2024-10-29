package kg.core.mnr.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "border_crossing")
public class BorderCrossing {
    @Id
    private UUID id;

    @Column(name = "crossing_date", nullable = false)
    private LocalDateTime crossingDate;

    @Column(name = "checkpoint", nullable = false)
    private String checkpoint;

    @ManyToOne
    @JoinColumn(name = "permit_id", nullable = false)
    private CitesPermit permit;
}
