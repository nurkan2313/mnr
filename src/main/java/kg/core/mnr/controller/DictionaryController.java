package kg.core.mnr.controller;

import kg.core.mnr.models.breadcrumbs.Breadcrumb;
import kg.core.mnr.models.dto.requests.ProductRequest;
import kg.core.mnr.models.entity.dict.Product;
import kg.core.mnr.models.mapper.ProductMapper;
import kg.core.mnr.repository.ProductRepository;
import kg.core.mnr.service.DictionaryService;
import kg.core.mnr.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@AllArgsConstructor
@Controller
@PreAuthorize("hasAnyRole('ADMIN', 'BORDER')")
@RequestMapping("dictionary")
public class DictionaryController {
    private final DictionaryService dictionaryService;
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    @GetMapping("products/search")
    public List<Product> searchSimilarProducts(
            @RequestParam(value = "species", required = false) String species,
            @RequestParam(value = "suspectedOriginCountry", required = false) String country
    ) {
        return dictionaryService.getProductsAndCountries(species, country);
    }

    @GetMapping("products")
    public ModelAndView index(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String code,
            Model model) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        authentication.getAuthorities().forEach(
                it-> System.out.println(it.getAuthority())
        );

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = dictionaryService.getFilteredProducts(description, code, pageable);

        int totalPages = productPage.getTotalPages();

        // Хлебные крошки
        List<Breadcrumb> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(new Breadcrumb("/dashboard", "Панель управления"));
        breadcrumbs.add(new Breadcrumb("/dictionary/products", "Объекты"));

        // Определяем начальную и конечную страницы для отображения
        int startPage = Math.max(1, page + 1 - 1);  // Начальная страница
        int endPage = Math.min(totalPages, startPage + 2);  // Конечная страница (максимум 3)

        List<Integer> pageNumbers = IntStream.rangeClosed(startPage, endPage)
                .boxed()
                .collect(Collectors.toList());

        // Передача данных в шаблон
        model.addAttribute("breadcrumbs", breadcrumbs);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", "");
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("previousPage", (page > 0) ? page - 1 : 0);
        model.addAttribute("nextPage", (page < totalPages - 1) ? page + 1 : totalPages - 1);
        model.addAttribute("previousDisabled", (page == 0));
        model.addAttribute("nextDisabled", (page == totalPages - 1));

        // Передача ограниченного списка страниц
        model.addAttribute("pageNumbers", pageNumbers);

        return new ModelAndView("dictionary/product/index");
    }


    @PostMapping("products")
    public ModelAndView addProduct(@ModelAttribute ProductRequest productRequest,
                                   @RequestParam("image") MultipartFile image) throws IOException {

        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setCode(productRequest.getCode());
        product.setDescription(productRequest.getDescription());
        product.setPreferredUnit(productRequest.getPreferredUnit());
        product.setAlternativeBlock(productRequest.getAlternativeBlock());
        product.setExplanation(productRequest.getExplanation());
        if (!image.isEmpty()) {
            product.setImagePath(saveImage(image)); // Сохраняем путь в базе данных
        }
        productRepository.save(product);
        return new ModelAndView("redirect:/dictionary/products");
    }

    private String saveImage(MultipartFile pdfFile) throws IOException {
        String fileName = UUID.randomUUID() + "_" + pdfFile.getOriginalFilename();
        Path filePath = Paths.get("src/main/resources/static/uploads/images", fileName);
        Files.createDirectories(filePath.getParent()); // создаем директории, если не существует
        Files.copy(pdfFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING); // сохраняем файл
        return "/uploads/images/" + fileName;
    }

    @GetMapping("products/form")
    public String form(Model model) {
        // Хлебные крошки
        List<Breadcrumb> breadcrumbs = new ArrayList<>();

        breadcrumbs.add(new Breadcrumb("/dashboard", "Панель управления"));
        breadcrumbs.add(new Breadcrumb("/dictionary/products", "Объекты"));
        breadcrumbs.add(new Breadcrumb("/dictionary/products/form", "Создать"));

        List<Product> productPage = dictionaryService.getProducts();

        model.addAttribute("breadcrumbs", breadcrumbs);
        model.addAttribute("products", productPage);
        model.addAttribute("currentPage", "");
        return "dictionary/product/create";
    }

    @GetMapping("products/{id}")
    public ModelAndView viewProduct(@PathVariable UUID id, Model model) {
        // Хлебные крошки
        List<Breadcrumb> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(new Breadcrumb("/dashboard", "Панель управления"));
        breadcrumbs.add(new Breadcrumb("/dictionary/products", "Объекты"));
        breadcrumbs.add(new Breadcrumb("/dictionary/products/" + id, "Детали"));

        model.addAttribute("breadcrumbs", breadcrumbs);
        model.addAttribute("currentPage", "Просмотр");

        return productService.findById(id)
                .map(product -> {
                    model.addAttribute("product", product);
                    return new ModelAndView("dictionary/product/view");
                })
                .orElseGet(() -> {
                    model.addAttribute("error", "Product not found");
                    return new ModelAndView("redirect:/products");
                });
    }

    @PutMapping("products/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable UUID id, @RequestBody Product productDetails) {
        Optional<Product> existingProduct = productService.findById(id);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setDescription(productDetails.getDescription());
            product.setCode(productDetails.getCode());
            product.setPreferredUnit(productDetails.getPreferredUnit());
            product.setAlternativeBlock(productDetails.getAlternativeBlock());
            product.setExplanation(productDetails.getExplanation());
            productService.save(product);
            return ResponseEntity.ok("Успешно обновлено!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }
}
