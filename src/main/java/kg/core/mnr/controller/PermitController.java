package kg.core.mnr.controller;

import kg.core.mnr.models.breadcrumbs.Breadcrumb;
import kg.core.mnr.models.dto.CitesPermitUpdateDTO;
import kg.core.mnr.models.dto.enums.DocStatus;
import kg.core.mnr.models.dto.requests.CitesPermitFormRequest;
import kg.core.mnr.models.entity.CitesPermit;
import kg.core.mnr.models.mapper.CitesPermitMapper;
import kg.core.mnr.repository.CountryRepository;
import kg.core.mnr.repository.ProductRepository;
import kg.core.mnr.service.CitesPermitService;
import kg.core.mnr.service.DictionaryService;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@AllArgsConstructor
@Controller
public class PermitController {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private final CitesPermitService citesPermitService;
    private final CitesPermitMapper citesPermitMapper;
    private final ProductRepository productRepository;
    private final DictionaryService dictionaryService;
    private final CountryRepository countryRepository;

    @GetMapping("permission")
    public ModelAndView permissionAndFilter(@RequestParam(required = false) String permitNumber,
                                            @RequestParam(required = false) String companyName,
                                            @RequestParam(required = false) String protectionNumber,
                                            @RequestParam(required = false) String object,
                                            @RequestParam(required = false) Double quantity,
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
        if (permitNumber != null || companyName != null || protectionNumber!= null || object != null || quantity != null) {
            List<CitesPermit> filteredPermits = citesPermitService.filterPermits(
                    permitNumber,
                    companyName,
                    protectionNumber,
                    object,
                    quantity,
                    startDate,
                    endDate);

            model.addAttribute("permits", filteredPermits);
            return new ModelAndView("permission/lists"); // Возвращаем список отфильтрованных данных
        }

        return new ModelAndView("permission/permit-form"); // Возвращаем форму для создания разрешения
    }

    @GetMapping("permission/{id}")
    public String permission(@PathVariable UUID id, Model model) {
        CitesPermit permitById = citesPermitService.getPermitById(id);
        UUID objectId = UUID.fromString(permitById.getObject());
        if (permitById == null) {
            return "error/404";
        }

        permitById.setObject(productRepository
                .findById(UUID.fromString(permitById.getObject())).get()
                .getDescription());

        List<Breadcrumb> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(new Breadcrumb("/dashboard", "Главная"));
        breadcrumbs.add(new Breadcrumb("/permission/list", "разрешения"));

        if(permitById.getStatus().equals(DocStatus.USED)) {
            model.addAttribute("stat", false);
        } else {
            model.addAttribute("stat", true);
        }

        permitById.setImporterCountry(countryRepository.findById(UUID.fromString(permitById.getImporterCountry())).get().getName());
        permitById.setExporterCountry(countryRepository.findById(UUID.fromString(permitById.getExporterCountry())).get().getName());

        model.addAttribute("status", permitById.getStatus().name());
        model.addAttribute("breadcrumbs", breadcrumbs);
        model.addAttribute("currentPage", "просмотр разрешения");
        model.addAttribute("permit", permitById);
        model.addAttribute("imagePath", productRepository.findById(objectId).get().getImagePath());
        return "permission/detail";
    }

    @GetMapping("/permission/list")
    public String index(@RequestParam(required = false) String permitNumber,
                              @RequestParam(required = false) String companyName,
                              @RequestParam(required = false) String protectionNumber,
                              @RequestParam(required = false) String object,
                              @RequestParam(required = false) Double quantity,
                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              Model model) {

        // Добавление хлебных крошек
        List<Breadcrumb> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(new Breadcrumb("/dashboard", "Главная"));
        breadcrumbs.add(new Breadcrumb("/permission/list", "разрешения"));

        model.addAttribute("breadcrumbs", breadcrumbs);
        model.addAttribute("currentPage", "форма для создания разрешения");

        // Если присутствуют параметры фильтрации, выполняем фильтрацию
        if (permitNumber != null || companyName != null || protectionNumber != null || object != null || quantity != null || startDate != null || endDate != null) {
            List<CitesPermit> filteredPermits = citesPermitService.filterPermits(
                    permitNumber,
                    companyName,
                    protectionNumber,
                    object,
                    quantity,
                    startDate,
                    endDate);

            filteredPermits.forEach(permit -> permit.setStatusDescription(permit.getStatus().getDescription()));
            filteredPermits.forEach(permit -> permit.setObject(
                            productRepository
                                    .findById(UUID.fromString(permit.getObject())).get().getDescription()
                    ));


            model.addAttribute("permits", filteredPermits);
            return "permission/lists"; // Возвращаем список отфильтрованных данных
        } else {
            // Если параметры фильтрации отсутствуют, выполняем пагинацию
            Pageable pageable = PageRequest.of(page, size);
            Page<CitesPermit> allPermits = citesPermitService.getAllPermits(pageable);

            allPermits.forEach(permit -> permit.setStatusDescription(permit.getStatus().getDescription()));
            allPermits.forEach(permit -> permit.setObject(
                    productRepository
                            .findById(UUID.fromString(permit.getObject())).get().getDescription()
            ));

            int totalPages = allPermits.getTotalPages();
            int startPage = Math.max(1, page + 1 - 1); // Начальная страница
            int endPage = Math.min(totalPages, startPage + 2); // Конечная страница (максимум 3)
            List<Integer> pageNumbers = IntStream.rangeClosed(startPage, endPage)
                    .boxed()
                    .collect(Collectors.toList());

            model.addAttribute("permits", allPermits);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("previousPage", (page > 0) ? page - 1 : 0);
            model.addAttribute("nextPage", (page < totalPages - 1) ? page + 1 : totalPages - 1);
            model.addAttribute("previousDisabled", (page == 0));
            model.addAttribute("nextDisabled", (page == totalPages - 1));
            model.addAttribute("pageNumbers", pageNumbers);

            return "permission/lists"; // Возвращаем список с пагинацией
        }
    }

    // Обновление разрешения
    @PostMapping("permission/update/{id}")
    public String updatePermit(@PathVariable UUID id, @ModelAttribute CitesPermitUpdateDTO updateDTO) {
        updateDTO.setId(id);
        citesPermitService.updateCitesPermit(updateDTO);
        return "redirect:/permission/lists";  // Перенаправление на список разрешений
    }

    @PostMapping("permission")
    public String post(@Valid @ModelAttribute CitesPermitFormRequest form,
                             BindingResult bindingResult,
                             @RequestParam("pdfFile") MultipartFile pdfFile) {
        try {
            form.setIssueDate(LocalDateTime.parse(form.getIssueDateString(), formatter));
            form.setExpiryDate(LocalDateTime.parse(form.getExpiryDateString(), formatter));
        } catch (DateTimeException e) {
            bindingResult.rejectValue("issueDate", "error.issueDate", "Invalid date format for issue date");
            bindingResult.rejectValue("expiryDate", "error.expiryDate", "Invalid date format for expiry date");
        }
        CitesPermit permit = citesPermitMapper.toEntity(form);
        // Сохраняем файл
        if (!pdfFile.isEmpty()) {
            try {
                String fileName = savePdfFile(pdfFile); // Функция сохранения файла
                permit.setPdfFileName(fileName); // Устанавливаем имя файла в модель
            } catch (IOException e) {
                bindingResult.rejectValue("pdfFile", "error.pdfFile", "Error saving file");
                return "/permission/permit-form";
            }
        }

        citesPermitService.createPermit(permit);
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
        String fileName = UUID.randomUUID() + "_" + pdfFile.getOriginalFilename();
        Path filePath = Paths.get("uploads", fileName);
        Files.createDirectories(filePath.getParent()); // создаем директории, если не существует
        Files.copy(pdfFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING); // сохраняем файл
        return fileName;
    }

}
