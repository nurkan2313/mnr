package kg.sh.mnr.mapper;

import kg.sh.mnr.entity.dict.Product;
import kg.sh.mnr.model.requests.ProductRequest;
import org.mapstruct.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(ProductRequest productRequest);

    default byte[] multipartFileToByteArray(MultipartFile file) {
        try {
            return file != null && !file.isEmpty() ? file.getBytes() : null;
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert MultipartFile to byte array", e);
        }
    }
}
