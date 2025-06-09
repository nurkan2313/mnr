package kg.sh.mnr.controller;

import kg.sh.mnr.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final DashboardService dashboardService;

    @GetMapping("/")
    public String index(@RequestParam(required = false, defaultValue = "month") String period,
                        Model model) {
        LocalDate now = LocalDate.now();
        LocalDate startDate;

        switch (period) {
            case "year" -> startDate = now.withDayOfYear(1);
            case "quarter" -> startDate = now.minusMonths(3);
            default -> startDate = now.withDayOfMonth(1); // month
        }

        long totalPermits = dashboardService.countPermitsFrom(startDate);
        long totalIncidents = dashboardService.countIncidentsFrom(startDate);
        double importVolume = dashboardService.countImportVolumeFrom(startDate);

        model.addAttribute("totalPermits", totalPermits);
        model.addAttribute("totalIncidents", totalIncidents);
        model.addAttribute("importVolume", importVolume);

        model.addAttribute("isMonth", "month".equals(period));
        model.addAttribute("isQuarter", "quarter".equals(period));
        model.addAttribute("isYear", "year".equals(period));

        return "main";
    }

    @GetMapping("/dashboard")
    public String homePage(@RequestParam(required = false, defaultValue = "month") String period,
                           Model model) {

        LocalDate now = LocalDate.now();
        LocalDate startDate;

        switch (period) {
            case "year" -> startDate = now.withDayOfYear(1);
            case "quarter" -> startDate = now.minusMonths(3);
            default -> startDate = now.withDayOfMonth(1); // month
        }

        long totalPermits = dashboardService.countPermitsFrom(startDate);
        long totalIncidents = dashboardService.countIncidentsFrom(startDate);
        double importVolume = dashboardService.countImportVolumeFrom(startDate);

        model.addAttribute("totalPermits", totalPermits);
        model.addAttribute("totalIncidents", totalIncidents);
        model.addAttribute("importVolume", importVolume);

        model.addAttribute("isMonth", "month".equals(period));
        model.addAttribute("isQuarter", "quarter".equals(period));
        model.addAttribute("isYear", "year".equals(period));

        return "index";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "about";
    }

    @GetMapping("/cites-template")
    public String getCitesTemplate() {
        return "cites-template";
    }
}