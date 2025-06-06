package kg.core.mnr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kg.core.mnr.models.breadcrumbs.Breadcrumb;
import kg.core.mnr.models.dto.requests.ProductRequest;
import kg.core.mnr.models.entity.dict.Product;
import kg.core.mnr.models.entity.dict.UnitOfMeasurement;
import kg.core.mnr.repository.ProductRepository;
import kg.core.mnr.repository.UnitOfMeasurementRepository;
import kg.core.mnr.service.DictionaryService;
import kg.core.mnr.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@AllArgsConstructor
@Controller
@RequestMapping("dictionary")
public class DictionaryController {
    private final DictionaryService dictionaryService;
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final UnitOfMeasurementRepository unitOfMeasurementRepository;

    @GetMapping("/units/search")
    @ResponseBody
    public List<UnitOfMeasurement> searchUnits(@RequestParam("unit") String unit) {
        System.out.println(dictionaryService.getSimilarUnitOfMeasurements(unit));
        return dictionaryService.getSimilarUnitOfMeasurements(unit);
    }

    @PostMapping("/units/create")
    public ResponseEntity<UnitOfMeasurement> createUnit(@RequestBody Map<String, String> request) {
        String unitName = request.get("unit");

        // Проверяем, что единица измерения уникальна
        if (unitOfMeasurementRepository.findByUnit(unitName) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Конфликт, если уже существует
        }

        UnitOfMeasurement newUnit = new UnitOfMeasurement();
        newUnit.setUnit(unitName);
        unitOfMeasurementRepository.save(newUnit);

        return ResponseEntity.status(HttpStatus.CREATED).body(newUnit);
    }

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
        breadcrumbs.add(new Breadcrumb("/dashboard", "дешборд"));
        breadcrumbs.add(new Breadcrumb("/dictionary/products", "объекты"));

        // Определяем начальную и конечную страницы для отображения
        int startPage = Math.max(1, page + 1 - 1);  // Начальная страница
        int endPage = Math.min(totalPages, startPage + 10);  // Конечная страница (максимум 10)

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
        product.setDescription(productRequest.getDescription());
        product.setExplanation(productRequest.getExplanation());
        product.setLatinName(productRequest.getLatinName());
        if (!image.isEmpty()) {
            product.setImagePath(productService.saveImage(image)); // Сохраняем путь в базе данных
        }
        productRepository.save(product);
        return new ModelAndView("redirect:/dictionary/products");
    }


    @GetMapping("products/form")
    public String form(Model model) {
        // Хлебные крошки
        List<Breadcrumb> breadcrumbs = new ArrayList<>();

        breadcrumbs.add(new Breadcrumb("/dashboard", "дешборд"));
        breadcrumbs.add(new Breadcrumb("/dictionary/products", "объекты"));
        breadcrumbs.add(new Breadcrumb("/dictionary/products/form", "создать"));

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
        breadcrumbs.add(new Breadcrumb("/dashboard", "дешборд"));
        breadcrumbs.add(new Breadcrumb("/dictionary/products", "объекты"));
        breadcrumbs.add(new Breadcrumb("/dictionary/products/" + id, "детали"));

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

    @PutMapping(value = "products/{id}", consumes = "multipart/form-data")
    public ResponseEntity<String> updateProduct(
            @PathVariable UUID id,
            @RequestPart("product") String productJson,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            // Обработка данных продукта из JSON
            ObjectMapper objectMapper = new ObjectMapper();
            Product product = objectMapper.readValue(productJson, Product.class);

            // Если файл был отправлен, сохраните его
            if (file != null && !file.isEmpty()) {
                String imagePath = productService.saveImage(file);
                product.setImagePath(imagePath);
            }

            // Логика обновления продукта в БД
            productService.update(id, product);

            return ResponseEntity.ok("Продукт успешно обновлен");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка обработки данных");
        }
    }


}
