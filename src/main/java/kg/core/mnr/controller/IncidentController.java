package kg.core.mnr.controller;

import kg.core.mnr.models.breadcrumbs.Breadcrumb;
import kg.core.mnr.models.dto.IncidentViewDto;
import kg.core.mnr.models.dto.requests.IncidentFormRequest;
import kg.core.mnr.models.entity.Incident;
import kg.core.mnr.models.entity.dict.*;
import kg.core.mnr.models.mapper.IncidentMapper;
import kg.core.mnr.service.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor
@RequestMapping("/incidents")
public class IncidentController {

    private final IncidentMapper incidentMapper;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private final IncidentService incidentService;
    private final UnitOfMeasurementService unitOfMeasurementService;
    private final DictionaryService dictionaryService;
    private final ProductService productService;
    private final AuthorityService authorityService;

    @GetMapping("/search-products")
    @ResponseBody
    public List<Product> searchSimilarProducts(
            @RequestParam(value = "species", required = false) String species,
            @RequestParam(value = "suspectedOriginCountry", required = false) String country
    ) {
        return dictionaryService.getSimilarProducts(species);
    }

    @GetMapping("/search-countries")
    @ResponseBody
    public List<Country> searchCountries(@RequestParam("country") String country) {
        return dictionaryService.getSimilarCountries(country);
    }

    @GetMapping("/search-units")
    @ResponseBody
    public List<UnitOfMeasurement> searchUnits(@RequestParam("unit") String unit) {
        return dictionaryService.getSimilarUnitOfMeasurements(unit);
    }

    @PostMapping("/create-unit")
    public ResponseEntity<UnitOfMeasurement> createUnit(@RequestBody Map<String, String> request) {
        String unitName = request.get("unit");

        // Проверяем наличие единицы измерения
        UnitOfMeasurement existingUnit = unitOfMeasurementService.findUnitByName(unitName);
        if (existingUnit != null) {
            return ResponseEntity.ok(existingUnit); // Возвращаем уже существующую
        }

        // Создаем новую единицу
        UnitOfMeasurement newUnit = new UnitOfMeasurement();
        newUnit.setUnit(unitName);
        unitOfMeasurementService.addUnit(newUnit);

        return ResponseEntity.ok(newUnit);
    }

    @GetMapping("/search-species")
    @ResponseBody
    public List<Product> searchSpecies(@RequestParam("species") String species) {
        return dictionaryService.getSimilarProducts(species);
    }

    @GetMapping("/searchCountries")
    @ResponseBody
    public List<Country> searchCountriesByName(@RequestParam("query") String query) {
        return dictionaryService.searchCountriesByName(query);
    }

    // Отображение всех инцидентов
    @GetMapping
    public String getAllIncidents(@RequestParam(required = false) String species,
                                  @RequestParam(required = false) String authority,
                                  @RequestParam(required = false) String transportMethod,
                                  @RequestParam(required = false) String suspectedOriginCountry,
                                  @RequestParam(required = false) String finalDestination,
                                  @RequestParam(required = false) String registeredAt,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  Model model) {

        // Преобразование строки в LocalDateTime
        LocalDate registeredAtDate = parseDate(registeredAt, model);

        Pageable pageable = PageRequest.of(page, size);

        if (model.containsAttribute("error")) {
            model.addAttribute("incidents", incidentService.getAllIncidents(pageable));
            return "lists";
        }

        // Получение списков из сервисов
        List<Authority> allAuthorities = authorityService.getAllAuthorities();
        List<TransportMethod> transportMethods = dictionaryService.getTransportMethod();

        Page<Incident> incidentsRaw;
        int totalPages;
        // Определяем начальную и конечную страницы для отображения

        // Проверка: если все параметры пустые, возвращаем все инциденты
        if (isNullOrEmpty(species, authority, transportMethod, suspectedOriginCountry, finalDestination, registeredAt)) {
            incidentsRaw = incidentService.getAllIncidents(pageable);
            totalPages = incidentsRaw.getTotalPages();
        } else {
            incidentsRaw = incidentService.filterIncidents(
                    emptyToNull(species),
                    emptyToNull(authority),
                    emptyToNull(transportMethod),
                    emptyToNull(suspectedOriginCountry),
                    emptyToNull(finalDestination),
                    registeredAtDate,
                    pageable
            );
            totalPages = incidentsRaw.getTotalPages();
        }

        Page<IncidentViewDto> incidents = incidentsRaw.map(incident -> {
            String latinName = Optional.ofNullable(dictionaryService.findProductByDescription(incident.getSpecies()))
                    .map(Product::getLatinName)
                    .orElse("");
            return new IncidentViewDto(incident, latinName);
        });

        int startPage = Math.max(1, page + 1 - 1);  // Начальная страница
        int endPage = Math.min(totalPages, startPage + 2);  // Конечная страница (максимум 3)
        List<Integer> pageNumbers = IntStream.rangeClosed(startPage, endPage)
                .boxed()
                .collect(Collectors.toList());

        // Хлебные крошки
        List<Breadcrumb> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(new Breadcrumb("/dashboard", "Панель управления"));
        breadcrumbs.add(new Breadcrumb("/incidents", "инциденты"));

        // Добавление данных в модель
        model.addAttribute("breadcrumbs", breadcrumbs);
        model.addAttribute("currentPage", "список");
        model.addAttribute("allAuthorities", allAuthorities);
        model.addAttribute("transportMethods", transportMethods);
        model.addAttribute("incidents", incidents);
        model.addAttribute("currentPage", "");
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("previousPage", (page > 0) ? page - 1 : 0);
        model.addAttribute("nextPage", (page < totalPages - 1) ? page + 1 : totalPages - 1);
        model.addAttribute("previousDisabled", (page == 0));
        model.addAttribute("nextDisabled", (page == totalPages - 1));
        model.addAttribute("pageNumbers", pageNumbers);

        return "incidents/lists";
    }

    private LocalDate parseDate(String registeredAt, Model model) {
        if (registeredAt != null && !registeredAt.isEmpty()) {
            try {
                // Используем форматер для LocalDate
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return LocalDate.parse(registeredAt, formatter);
            } catch (DateTimeParseException e) {
                model.addAttribute("error", "Invalid date format for registeredAt");
            }
        }
        return null;
    }


    private boolean isNullOrEmpty(String... values) {
        for (String value : values) {
            if (value != null && !value.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private String emptyToNull(String value) {
        return (value == null || value.isEmpty()) ? null : value;
    }

    // Форма для создания нового инцидента
    @GetMapping("/create")
    public String showCreateIncidentForm(Model model) {
        List<UnitOfMeasurement> units = unitOfMeasurementService.getAllUnits();
        List<Country> countries = dictionaryService.getAllCountries();
        List<Authority> authorities = dictionaryService.getAuthorities();
        List<DiscoveryMethod> discoveryMethods = dictionaryService.getDiscoveryMethods();
        List<ReasonForSeizure> reasonForSeizures = dictionaryService.getReasonForSeizures();
        List<TradeDirection> tradeDirections = dictionaryService.getTradeDirections();
        List<TransportMethod> transportMethods = dictionaryService.getTransportMethod();

        // Хлебные крошки
        List<Breadcrumb> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(new Breadcrumb("/dashboard", "Панель управления"));
        breadcrumbs.add(new Breadcrumb("/incidents", "инциденты"));
        breadcrumbs.add(new Breadcrumb("/incidents/create", "создать"));

        // Добавление данных в модель
        model.addAttribute("breadcrumbs", breadcrumbs);
        model.addAttribute("currentPage", "создать инцидент");

        model.addAttribute("incident", new Incident());
        model.addAttribute("units", units);
        model.addAttribute("countries", countries);
        model.addAttribute("authorities", authorities);
        model.addAttribute("discoveryMethods", discoveryMethods);
        model.addAttribute("tradeDirections", tradeDirections);
        model.addAttribute("transportMethods", transportMethods);
        model.addAttribute("discoveryMethods", discoveryMethods);
        model.addAttribute("reasonForSeizures", reasonForSeizures);
        return "incidents/create";
    }

    // Обработка формы создания инцидента
    @PostMapping
    public ModelAndView createIncident(@Valid @ModelAttribute IncidentFormRequest form,
                                       BindingResult bindingResult,
                                       @RequestParam(value = "photo", required = false) MultipartFile photo,
                                       Model model) {
        if (photo != null && !photo.isEmpty()) {
            try {
                String filename = UUID.randomUUID() + "_" + photo.getOriginalFilename();
                Path uploadPath = Paths.get("uploads/files");

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Files.copy(photo.getInputStream(), uploadPath.resolve(filename), StandardCopyOption.REPLACE_EXISTING);

                form.setPhotoPath("/uploads/files/" + filename);
            } catch (IOException e) {
                bindingResult.reject("photo", "Ошибка загрузки файла: " + e.getMessage());
            }
        }

        try {
            form.setRegisteredAt(LocalDateTime.parse(form.getRegisteredAtString(), formatter));
        } catch (DateTimeParseException e) {
            bindingResult.rejectValue("registeredAtString", "error.registeredAtString", "Неверный формат даты");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return new ModelAndView("incidents/create");  // Вернуть форму для исправления ошибок
        }

        form.setTransitCountries(String.join(",", form.getTransitCountries()));
        form.setSpecies(productService.findById(UUID.fromString(form.getSpecies())).orElseThrow().getDescription());
        incidentService.createIncident(incidentMapper.toIncident(form));

        return new ModelAndView("redirect:/incidents");
    }

    // Просмотр конкретного инцидента
    @GetMapping("/{id}")
    public String getIncidentById(@PathVariable String id, Model model) {
        Incident incident = incidentService.getIncidentById(id);
        if (incident == null) {
            return "error/404";
        }
        List<Breadcrumb> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(new Breadcrumb("/dashboard", "Панель управления"));
        breadcrumbs.add(new Breadcrumb("/incidents", "инциденты"));

        // Добавление данных в модель
        model.addAttribute("breadcrumbs", breadcrumbs);
        model.addAttribute("currentPage", "просмотр");

        model.addAttribute("incident", incident);
        return "incidents/view";
    }
}
