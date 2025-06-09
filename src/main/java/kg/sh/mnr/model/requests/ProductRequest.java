package kg.sh.mnr.model.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Getter
@Setter
public class ProductRequest {
    private String description;
    private String code;
    private String preferredUnit;
    private String alternativeBlock;
    private String explanation;
    private String latinName;
    private MultipartFile image;
}
