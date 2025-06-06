package kg.core.mnr.controller;

import com.samskivert.mustache.Mustache;
import kg.core.mnr.models.breadcrumbs.Breadcrumb;
import kg.core.mnr.models.dto.enums.ObjectType;
import kg.core.mnr.models.entity.dict.Definition;
import kg.core.mnr.service.DefinitionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/definitions")
public class DefinitionController {

    private final DefinitionService service;

    public DefinitionController(DefinitionService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        List<Breadcrumb> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(new Breadcrumb("/dashboard", "дешборд"));
        breadcrumbs.add(new Breadcrumb("/definitions", "определитиль СITES"));

        model.addAttribute("breadcrumbs", breadcrumbs);
        model.addAttribute("currentPage", "список");

        model.addAttribute("definitions", service.getAll());
        return "definitions/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        List<Breadcrumb> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(new Breadcrumb("/dashboard", "дешборд"));
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

        model.addAttribute("isSelectedObject", (Mustache.Lambda) (frag, out) -> {
            String current = frag.execute().trim();
            if (current.equals(String.valueOf(definition.getObject()))) {
                out.write("selected");
            }
        });

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
        model.addAttribute("definition", service.getById(id));
        model.addAttribute("objectTypes", ObjectType.values());
        return "definitions/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable UUID id) {
        service.delete(id);
        return "redirect:/definitions";
    }
}
