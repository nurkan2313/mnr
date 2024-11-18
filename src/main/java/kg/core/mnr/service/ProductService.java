package kg.core.mnr.service;

import jakarta.persistence.EntityNotFoundException;
import kg.core.mnr.models.entity.dict.Product;
import kg.core.mnr.repository.ProductRepository;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(UUID id) {
        return productRepository.findById(id);
    }

    @Transactional
    public void update(UUID id, Product product) {
        Optional<Product> existingProduct = findById(id);

        if (existingProduct.isPresent()) {
            Product productToUpdate = existingProduct.get();

            // Обновляем только те поля, которые переданы в новом объекте
            productToUpdate.setExplanation(product.getExplanation());
            productToUpdate.setPreferredUnit(product.getPreferredUnit());
            productToUpdate.setDescription(product.getDescription());

            // Сохраняем обновления
            productRepository.saveAndFlush(productToUpdate);
        } else {
            throw new EntityNotFoundException("Product with id " + id + " not found");
        }
    }

    public Product save(Product product) {
        product.setId(UUID.randomUUID());
        return productRepository.save(product);
    }

    public void deleteById(UUID id) {
        productRepository.deleteById(id);
    }

    public UUID checkAndCreateProductByDescription(String description) {
        if (description == null || description.isEmpty()) return null;

        // Проверка существования продукта по описанию
        Optional<Product> existingProduct = productRepository.findByDescription(description);

        if (existingProduct.isPresent()) {
            return existingProduct.get().getId();
        } else {
            // Создаем новый продукт
            UUID id = UUID.randomUUID();
            Product newProduct = new Product();
            newProduct.setId(id);
            newProduct.setDescription(description);

            productRepository.save(newProduct);
            return id;
        }
    }

    public String saveImage(MultipartFile imageFile) throws IOException {
        // Генерируем уникальное имя для файла
        String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();

        // Указываем путь для сохранения файла
        Path filePath = Paths.get("src/main/resources/static/uploads/images", fileName);

        // Создаем директории, если их ещё нет
        Files.createDirectories(filePath.getParent());

        // Сохраняем файл на указанный путь
        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Возвращаем относительный путь к файлу
        return "/uploads/images/" + fileName;
    }

}
