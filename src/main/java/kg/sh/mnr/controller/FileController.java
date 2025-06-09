package kg.sh.mnr.controller;

import kg.sh.mnr.entity.FileDocument;
import kg.sh.mnr.entity.Folder;
import kg.sh.mnr.entity.Project;
import kg.sh.mnr.repository.FileRepository;
import kg.sh.mnr.repository.FolderRepository;
import kg.sh.mnr.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final ProjectRepository projectRepository;
    private final FolderRepository folderRepository;
    private final FileRepository fileRepository;

    @PostMapping("/create-folder")
    public ResponseEntity<?> createFolder(@RequestParam Long projectId, @RequestParam String folderName) {
        return projectRepository.findById(projectId).map(project -> {
            Folder folder = new Folder();
            folder.setName(folderName);
            folder.setProject(project);
            folderRepository.save(folder);
            return ResponseEntity.status(201).body("Folder created");
        }).orElse(ResponseEntity.status(404).body("Project not found"));
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(
            @RequestParam Long projectId,
            @RequestParam(required = false) Long folderId,
            @RequestParam("file") MultipartFile file) throws IOException {

        Optional<Project> projectOpt = projectRepository.findById(projectId);
        if (projectOpt.isEmpty()) return ResponseEntity.status(404).body("Project not found");

        Project project = projectOpt.get();
        FileDocument doc = new FileDocument();
        doc.setName(file.getOriginalFilename());
        doc.setFilePath("/uploads/" + file.getOriginalFilename());
        doc.setSize(file.getSize());
        doc.setProject(project);

        if (folderId != null) {
            folderRepository.findById(folderId).ifPresent(doc::setFolder);
        }

        fileRepository.save(doc);
        return ResponseEntity.ok("Uploaded");
    }
}
