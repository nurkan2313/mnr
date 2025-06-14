package kg.sh.mnr.controller;

import kg.sh.mnr.entity.dict.Definition;
import kg.sh.mnr.model.Breadcrumb;
import kg.sh.mnr.model.enums.ObjectType;
import kg.sh.mnr.repository.CitesPermitRepository;
import kg.sh.mnr.service.DefinitionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/definitions")
public class DefinitionController {

    private final DefinitionService service;
    private final CitesPermitRepository citesPermitRepository;

    public DefinitionController(DefinitionService service, CitesPermitRepository citesPermitRepository) {
        this.service = service;
        this.citesPermitRepository = citesPermitRepository;
    }

    @GetMapping
    public String list(Model model) {
        List<Breadcrumb> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(new Breadcrumb("/dashboard", "дэшборд"));
        breadcrumbs.add(new Breadcrumb("/definitions", "определитиль СITES"));

        model.addAttribute("breadcrumbs", breadcrumbs);
        model.addAttribute("currentPage", "список");

        model.addAttribute("definitions", service.getAll());
        return "definitions/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        List<Breadcrumb> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(new Breadcrumb("/dashboard", "дэшборд"));
        breadcrumbs.add(new Breadcrumb("/definitions", "определитиль СITES"));

        model.addAttribute("breadcrumbs", breadcrumbs);
        model.addAttribute("currentPage", "форма создания");

        Definition definition = new Definition();
        definition.setId(UUID.randomUUID()); // или null, если Spring генерирует
        definition.setName(""); // важно!
        definition.setType("");
        definition.setLatinName("");
        definition.setAreal("");
        definition.setCitesApplication("");
        definition.setObject(null);
        definition.setImagePath("");
        definition.setExportCountry("");
        definition.setImportCountry("");
        definition.setTransport("");
        definition.setTrade("");
        definition.setCheckProcess("");

        model.addAttribute("pageName", "definitionForm");

        model.addAttribute("id", definition.getId());
        model.addAttribute("name", definition.getName());
        model.addAttribute("type", definition.getType());
        model.addAttribute("latinName", definition.getLatinName());
        model.addAttribute("areal", definition.getAreal());
        model.addAttribute("citesApplication", definition.getCitesApplication());
        model.addAttribute("object", definition.getObject());
        model.addAttribute("imagePath", definition.getImagePath());
        model.addAttribute("exportCountry", definition.getExportCountry());
        model.addAttribute("importCountry", definition.getImportCountry());
        model.addAttribute("transport", definition.getTransport());
        model.addAttribute("trade", definition.getTrade());
        model.addAttribute("checkProcess", definition.getCheckProcess());

        model.addAttribute("objectTypes", ObjectType.values());


        return "definitions/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Definition definition) {
        if (definition.getId() == null) {
            definition.setId(UUID.randomUUID());
        }
        service.save(definition);
        return "redirect:/definitions";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable UUID id, Model model) {
        List<Breadcrumb> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(new Breadcrumb("/dashboard", "дэшборд"));
        breadcrumbs.add(new Breadcrumb("/definitions", "определитиль СITES"));

        model.addAttribute("breadcrumbs", breadcrumbs);
        model.addAttribute("currentPage", "редактировать");

        Definition def = service.getById(id);
        model.addAttribute("id", def.getId());
        model.addAttribute("name", def.getName());
        model.addAttribute("type", def.getType());
        model.addAttribute("latinName", def.getLatinName());
        model.addAttribute("areal", def.getAreal());
        model.addAttribute("citesApplication", def.getCitesApplication());
        model.addAttribute("object", def.getObject() != null ? def.getObject().name() : null);
        model.addAttribute("imagePath", def.getImagePath());
        model.addAttribute("exportCountry", def.getExportCountry());
        model.addAttribute("importCountry", def.getImportCountry());
        model.addAttribute("transport", def.getTransport());
        model.addAttribute("trade", def.getTrade());
        model.addAttribute("checkProcess", def.getCheckProcess());

        // Добавляем список ObjectType и выделенный selected
        model.addAttribute("objectTypes", Arrays.stream(ObjectType.values())
                .map(val -> Map.of(
                        "value", val.name(),
                        "selected", val == def.getObject()
                ))
                .collect(Collectors.toList()));

        model.addAttribute("definition", service.getById(id));
        model.addAttribute("objectTypes", ObjectType.values());

        return "definitions/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable UUID id) {
        service.delete(id);
        return "redirect:/definitions";
    }

    @GetMapping("/search")
    @ResponseBody
    public List<Map<String, String>> searchObjects(@RequestParam("query") String query) {
        String q = query.toLowerCase();

        // 1. Получаем из основной справочной таблицы
        List<String> fromDict = service.getAll().stream()
                .map(Definition::getName)
                .filter(Objects::nonNull)
                .filter(name -> name.toLowerCase().contains(q))
                .toList();

        // 2. Получаем уникальные объекты из разрешений, если не нашли ничего или мало
        List<String> fromPermits = citesPermitRepository.findDistinctObjectContaining(q);

        // 3. Объединяем + убираем дубликаты
        return Stream.concat(fromDict.stream(), fromPermits.stream())
                .distinct()
                .limit(20)
                .map(name -> Map.of("id", name, "name", name))
                .toList();
    }

}
