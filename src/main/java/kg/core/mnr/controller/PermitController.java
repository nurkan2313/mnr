package kg.core.mnr.controller;

import kg.core.mnr.models.breadcrumbs.Breadcrumb;
import kg.core.mnr.models.dto.CitesPermitUpdateDTO;
import kg.core.mnr.models.dto.enums.DocStatus;
import kg.core.mnr.models.dto.requests.CitesPermitFormRequest;
import kg.core.mnr.models.entity.CitesPermit;
import kg.core.mnr.models.entity.dict.BorderCheckpoint;
import kg.core.mnr.models.entity.dict.Product;
import kg.core.mnr.repository.BorderCheckpointRepository;
import kg.core.mnr.repository.CountryRepository;
import kg.core.mnr.repository.ProductRepository;
import kg.core.mnr.service.CitesPermitService;
import kg.core.mnr.service.ExcelUploadService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@AllArgsConstructor
@Controller
public class PermitController {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    private final CitesPermitService citesPermitService;
    private final ExcelUploadService excelUploadService;

    private final ProductRepository productRepository;
    private final CountryRepository countryRepository;
    private final BorderCheckpointRepository borderCheckpointRepository;

    @GetMapping("permission")
    public ModelAndView permissionAndFilter(@RequestParam(required = false) String permitNumber,
                                            @RequestParam(required = false) String protectionNumber,
                                            @RequestParam(required = false) String companyName,
                                            @RequestParam(required = false) String object,
                                            @RequestParam(required = false) String quantity,
                                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                            Model model) {

        // Добавление хлебных крошек
        List<Breadcrumb> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(new Breadcrumb("/dashboard", "Главная"));
        breadcrumbs.add(new Breadcrumb("/permission/list", "разрешения"));

        model.addAttribute("breadcrumbs", breadcrumbs);
        model.addAttribute("currentPage", "форма для создания разрешения");

        // Проверка наличия параметров фильтрации
        if (permitNumber != null || protectionNumber!= null ||  companyName != null  || object != null || quantity != null) {
            List<CitesPermit> filteredPermits = citesPermitService.filterPermits(
                    permitNumber,
                    protectionNumber,
                    companyName,
                    object,
                    quantity,
                    startDate,
                    endDate);

            model.addAttribute("permits", filteredPermits);
            return new ModelAndView("permission/lists"); // Возвращаем список отфильтрованных данных
        }

        return new ModelAndView("permission/permit-form"); // Возвращаем форму для создания разрешения
    }

    @GetMapping("/permission/report")
    public String report() {
        return "otchet/reports";
    }

    @GetMapping("/permission/report/export-data")
    public String exportВata() {
        return "otchet/export-data";
    }

    @GetMapping("/permission/list")
    public String index(@RequestParam(required = false) String permitNumber,
                              @RequestParam(required = false) String protectionNumber,
                              @RequestParam(required = false) String companyName,
                              @RequestParam(required = false) String object,
                              @RequestParam(required = false) String quantity,
                              @RequestParam(required = false) String type,
                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                              @RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "15") int size,
                              Model model) {

        int zeroBasedPage = (page > 0) ? page - 1 : 0;

        // Добавление хлебных крошек
        List<Breadcrumb> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(new Breadcrumb("/dashboard", "Главная"));
        breadcrumbs.add(new Breadcrumb("/permission/list", "разрешения"));

        model.addAttribute("breadcrumbs", breadcrumbs);
        model.addAttribute("currentPage", "форма для создания разрешения");
        // Если присутствуют параметры фильтрации, выполняем фильтрацию
        if (permitNumber != null || protectionNumber != null || companyName != null || object != null
                || quantity != null || startDate != null || endDate != null || type != null) {
            List<CitesPermit> filteredPermits = citesPermitService.filterPermits(
                    permitNumber,
                    protectionNumber,
                    companyName,
                    object,
                    quantity,
                    startDate,
                    endDate,
                    type);

            filteredPermits.forEach(permit -> permit.setStatusDescription(permit.getStatus().getDescription()));

            model.addAttribute("permits", filteredPermits);
            return "permission/lists"; // Возвращаем список отфильтрованных данных
        } else {
            // Если параметры фильтрации отсутствуют, выполняем пагинацию
            Pageable pageable = PageRequest.of(zeroBasedPage, size);

            Page<CitesPermit> allPermits = citesPermitService.getAllPermits(pageable);

            allPermits.forEach(permit -> permit.setStatusDescription(permit.getStatus().getDescription()));

            int totalPages = allPermits.getTotalPages();
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());  // Страницы 1-базированные

            int lastPage = totalPages - 1;
            int startPage = Math.max(1, page + 1 - 1); // Начальная страница
            int firstPage = 1;
            int stepBack10 = Math.max(0, page - 10); // Шаг назад на 10 страниц
            int stepForward10 = Math.min(lastPage, page + 10); // Шаг вперед на 10 страниц
            int endPage = Math.min(totalPages, startPage + 9); // Конечная страница (максимум 3)
//            List<Integer> pageNumbers = IntStream.rangeClosed(startPage, endPage)
//                    .boxed()
//                    .collect(Collectors.toList());
            List<BorderCheckpoint> checkpoints = borderCheckpointRepository.findAll();

            model.addAttribute("checkpoints", checkpoints);
            model.addAttribute("permits", allPermits);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("lastPage", lastPage); // Передача индекса последней страницы
            model.addAttribute("startPage", startPage); // Передача индекса последней страницы
            model.addAttribute("firstPage", firstPage);
            model.addAttribute("stepBack10", stepBack10); // Шаг назад на 10 страниц
            model.addAttribute("stepForward10", stepForward10);
            model.addAttribute("previousPage", (page > 0) ? page - 1 : 0);
            model.addAttribute("nextPage", (page < totalPages - 1) ? page + 1 : totalPages - 1);
            model.addAttribute("previousDisabled", (page == 0));
            model.addAttribute("nextDisabled", (page == totalPages - 1));
            model.addAttribute("pageNumbers", pageNumbers);

            return "permission/lists"; // Возвращаем список с пагинацией
        }
    }

    @GetMapping("permission/{id}")
    public String permission(@PathVariable String id, Model model) {
        CitesPermit permitById = citesPermitService.getPermitById(id);

        Optional<Product> productOptional = productRepository.findByDescription(permitById.getObject());
        String measure = productOptional.map(Product::getPreferredUnit).orElse(null);

        String imagePath = productRepository.findByDescription(permitById.getObject())
                .map(Product::getImagePath)
                .orElse(null);

        List<Breadcrumb> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(new Breadcrumb("/dashboard", "Главная"));
        breadcrumbs.add(new Breadcrumb("/permission/list", "разрешения"));

        if(permitById.getStatus().equals(DocStatus.USED)) {
            model.addAttribute("stat", false);
        } else {
            model.addAttribute("stat", true);
        }

        model.addAttribute("status", permitById.getStatus().name());
        model.addAttribute("breadcrumbs", breadcrumbs);
        model.addAttribute("currentPage", "просмотр разрешения");
        model.addAttribute("permit", permitById);
        model.addAttribute("measure", measure);
        model.addAttribute("imagePath", imagePath);
        return "permission/detail";
    }

    // Обновление разрешения
    @PostMapping("permission/update/{id}")
    public String updatePermit(@PathVariable String id, @ModelAttribute CitesPermitUpdateDTO updateDTO) {
        updateDTO.setId(id);
        citesPermitService.updateCitesPermit(updateDTO);
        return "redirect:/permission/lists";  // Перенаправление на список разрешений
    }

    @PostMapping("permission")
    public String post(@Valid @ModelAttribute CitesPermitFormRequest dto,
                             BindingResult bindingResult,
                             @RequestParam("pdfFile") MultipartFile pdfFile) {
        try {
            dto.setIssueDate(LocalDate.parse(dto.getIssueDateString(), formatter));
            dto.setExpiryDate(LocalDate.parse(dto.getExpiryDateString(), formatter));
        } catch (DateTimeException e) {
            bindingResult.rejectValue("issueDate", "error.issueDate", "Invalid date format for issue date");
            bindingResult.rejectValue("expiryDate", "error.expiryDate", "Invalid date format for expiry date");
        }

        CitesPermit citesPermit = new CitesPermit();

        citesPermit.setIssueDate(dto.getIssueDate());
        citesPermit.setExpiryDate(dto.getExpiryDate());
        processObject(citesPermit, dto);

        citesPermit.setImportId(UUID.fromString(dto.getImporterCountry()));
        citesPermit.setImporterCountry(String.valueOf(countryRepository.findById(
                UUID.fromString(dto.getImporterCountry())).get().getName()
        ));
        citesPermit.setExportId(UUID.fromString(dto.getExporterCountry()));
        citesPermit.setExporterCountry(
                String.valueOf(countryRepository.findById(
                        UUID.fromString(dto.getExporterCountry())).get().getName()
                )
        );

        citesPermit.setCompanyName(dto.getCompanyName());

        if ( dto.getQuantity() != null ) {
            citesPermit.setQuantity( String.valueOf( dto.getQuantity() ) );
        }

        citesPermit.setType(dto.getType());
        citesPermit.setLimiter(dto.getLimiter());
        citesPermit.setPurpose(dto.getPurpose());
        citesPermit.setRemarks(dto.getRemarks());
        citesPermit.setProtectionMarkNumber(dto.getProtectionMarkNumber() );
        citesPermit.setStatus(dto.getStatus());

        // Сохраняем файл
        if (!pdfFile.isEmpty()) {
            try {
                String fileName = savePdfFile(pdfFile); // Функция сохранения файла
                citesPermit.setPdfFileName(fileName); // Устанавливаем имя файла в модель
            } catch (IOException e) {
                bindingResult.rejectValue("pdfFile", "error.pdfFile", "Error saving file");
                return "/permission/permit-form";
            }
        }

        citesPermitService.createPermit(citesPermit);
        return "redirect:/permission";
    }

    public void processObject(CitesPermit citesPermit, CitesPermitFormRequest dto) {
        Object object = dto.getObject();

        // Проверяем, является ли объект строкой и пытаемся преобразовать в UUID
        if (object instanceof String) {
            try {
                UUID objectId = UUID.fromString((String) object);

                // Если UUID успешно извлечен, ищем описание
                Optional<Product> productOptional = productRepository.findById(objectId);
                if (productOptional.isPresent()) {
                    citesPermit.setObject(productOptional.get().getDescription());
                } else {
                    // Если продукт не найден, сохраняем исходное значение
                    citesPermit.setObject(object.toString());
                }
            } catch (IllegalArgumentException e) {
                // Если строка не является валидным UUID, сохраняем исходное значение
                citesPermit.setObject(object.toString());
            }
        } else {
            // Если объект не строка, сохраняем его строковое представление
            citesPermit.setObject(object != null ? object.toString() : null);
        }
    }

    @PostMapping("permission/upload-excel")
    public String uploadExcelFile(@RequestParam("excelFile") MultipartFile file,
                                  RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Выберите файл для загрузки");
            return "redirect:/permission";
        }

        try {
            List<CitesPermit> permits = excelUploadService.parseExcelFile(file);
            citesPermitService.saveAll(permits); // Сохранение всех разрешений
            redirectAttributes.addFlashAttribute("message", "Файл успешно загружен и данные сохранены!");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("message", "Ошибка при обработке файла: " + e.getMessage());
        }

        return "redirect:/permission";
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get("uploads", fileName);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new FileNotFoundException("File not found");
            }
        } catch (FileNotFoundException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }


    private String savePdfFile(MultipartFile pdfFile) throws IOException {
        // Проверяем, что файл не пуст
        if (pdfFile == null || pdfFile.isEmpty()) {
            throw new IllegalArgumentException("Файл не может быть пустым");
        }

        // Извлекаем оригинальное имя файла
        String originalFileName = pdfFile.getOriginalFilename();
        if (originalFileName == null || !originalFileName.toLowerCase().endsWith(".pdf")) {
            throw new IllegalArgumentException("Файл должен иметь расширение .pdf");
        }

        // Генерируем уникальное имя для сохранения файла
        String fileName = UUID.randomUUID().toString() + ".pdf";

        // Указываем путь для сохранения
        Path filePath = Paths.get("uploads", fileName);
        Files.createDirectories(filePath.getParent()); // Создаем директории, если их нет

        // Сохраняем файл
        Files.copy(pdfFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }

}
