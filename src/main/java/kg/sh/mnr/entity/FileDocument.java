package kg.sh.mnr.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "files")
@Data
public class FileDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String filePath;
    private Long size;
    private String uploadedBy;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder;
}