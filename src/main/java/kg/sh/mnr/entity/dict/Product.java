package kg.sh.mnr.entity.dict;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "latin_name")
    private String latinName;

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

    @Column(name = "type")
    private String type;
}
