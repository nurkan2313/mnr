package kg.core.mnr.models.mapper;

import kg.core.mnr.models.dto.requests.ProductRequest;
import kg.core.mnr.models.entity.dict.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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
