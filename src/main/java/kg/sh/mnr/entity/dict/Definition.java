package kg.sh.mnr.entity.dict;

import jakarta.persistence.*;
import kg.sh.mnr.model.enums.ObjectType;
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
@Table(name = "definitions")
public class Definition {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "type")
    private String type;

    @Column(name = "latin_name")
    private String latinName;

    @Column(name = "name")
    private String name;

    @Column(name = "areal")
    private String areal;

    @Column(name = "cites_application")
    private String citesApplication;

    @Enumerated(EnumType.STRING)
    @Column(name = "object")
    private ObjectType object;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "export_country")
    private String exportCountry;

    @Column(name = "import_country")
    private String importCountry;

    @Column(name = "transport")
    private String transport;

    @Column(name = "trade")
    private String trade;

    @Column(name = "check_process")
    private String checkProcess;
}