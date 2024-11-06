package kg.core.mnr.models.entity.dict;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    private UUID id;

    @Column(name = "description", unique = true)
    private String description;

    @Column(name = "code")
    private String code;

    @Column(name = "preferred_unit")
    private String preferredUnit;

    @Column(name = "alternative_block")
    private String alternativeBlock;

    @Column(name = "explanation")
    private String explanation;

    @Column(name = "image_path")
    private String imagePath;
}
