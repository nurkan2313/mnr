package kg.sh.mnr.repository;

import kg.sh.mnr.entity.FileDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileDocument, Long> {}
