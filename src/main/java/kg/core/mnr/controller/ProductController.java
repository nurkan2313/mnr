package kg.core.mnr.controller;

import kg.core.mnr.models.entity.dict.Product;
import kg.core.mnr.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable UUID id,
                                                 @RequestBody Product updatedProduct,
                                                 @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        Optional<Product> existingProduct = productService.findById(id);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setDescription(updatedProduct.getDescription());
            product.setPreferredUnit(updatedProduct.getPreferredUnit());
            product.setExplanation(updatedProduct.getExplanation());
            productService.save(product);
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/upload-image")
    public ResponseEntity<String> uploadImage(@PathVariable UUID id, @RequestParam("file") MultipartFile file) {
        Optional<Product> productOptional = productService.findById(id);
        if (productOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            // Сохраняем файл и возвращаем путь
            String imagePath = productService.saveImage(file);
            Product product = productOptional.get();
            product.setImagePath(imagePath);
            productService.save(product);

            return ResponseEntity.ok("Изображение успешно загружено: " + imagePath);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Ошибка загрузки изображения: " + e.getMessage());
        }
    }


    // Получение всех продуктов
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    // Получение продукта по ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable UUID id) {
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Создание нового продукта
    @PostMapping
    public ModelAndView createProduct(@ModelAttribute Product product) {
        productService.save(product);
        return new ModelAndView("redirect:/products");
    }

    @PostMapping("/new")
    @ResponseBody
    public Product createProduct(@RequestBody Map<String, String> request) {
        String description = request.get("description");
        UUID productId = productService.checkAndCreateProductByDescription(description);
        return productService.findById(productId).orElseThrow();
    }

    // Удаление продукта
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            productService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
