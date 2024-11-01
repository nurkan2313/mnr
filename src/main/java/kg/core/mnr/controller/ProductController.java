package kg.core.mnr.controller;

import kg.core.mnr.models.entity.dict.Product;
import kg.core.mnr.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

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

    // Обновление продукта
//    @PutMapping("/{id}")
//    public ResponseEntity<Product> updateProduct(@PathVariable UUID id, @RequestBody Product productDetails) {
//        Optional<Product> product = productService.findById(id);
//        if (product.isPresent()) {
//            Product existingProduct = product.get();
//            existingProduct.setDescription(productDetails.getDescription());
//            existingProduct.setCode(productDetails.getCode());
//            existingProduct.setPreferredUnit(productDetails.getPreferredUnit());
//            existingProduct.setAlternativeBlock(productDetails.getAlternativeBlock());
//            existingProduct.setExplanation(productDetails.getExplanation());
//
//            return ResponseEntity.ok(productService.save(existingProduct));
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

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
