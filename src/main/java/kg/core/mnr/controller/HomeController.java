package kg.core.mnr.controller;

import jakarta.servlet.http.HttpServletRequest;
import kg.core.mnr.models.dto.PageItem;
import kg.core.mnr.models.dto.enums.DocStatus;
import kg.core.mnr.models.entity.CitesPermit;
import kg.core.mnr.repository.ProductRepository;
import kg.core.mnr.service.DashboardService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@AllArgsConstructor
@Controller
public class HomeController {

    private final DashboardService dashboardService;

//    @GetMapping("/")
//    public String homePage(HttpServletRequest request, Model model) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String && authentication.getPrincipal().equals("anonymousUser"))) {
//            User user = (User) authentication.getPrincipal();
//            model.addAttribute("username", user.getUsername());
//        }
//        model.addAttribute("title", "Система Регулирования Разрешений");
//        return "home";
//    }

    @GetMapping("/")
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

        return "home";
    }

    @GetMapping("/dashboard")
    public String dashboardPage(
            @RequestParam(required = false, name = "start") String startDate,
            @RequestParam(required = false, name = "end") String endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);

        // Получение данных по диапазону дат
        Page<CitesPermit> permitsByDateRange = dashboardService.getPermitsByDateRange(pageable);

        extracted(permitsByDateRange);

        // Определение пагинации
        int totalPages = permitsByDateRange.getTotalPages();
        List<Integer> pageNumbers = generatePageNumbers(page, totalPages);

        // Получение данных по топовым экспортируемым видам
        Page<Map<String, Object>> topExportedSpecies = dashboardService.getTopExportedSpecies(pageable);
        int totalExportedSpeciesPages = topExportedSpecies.getTotalPages();
        List<Integer> pageNumbersEx = generatePageNumbers(page, totalExportedSpeciesPages);

        List<PageItem> pageNumberList = pageNumbers.stream()
                .map(PageItem::new)
                .collect(Collectors.toList());

        model.addAttribute("pageNumberList", pageNumberList);

        // Добавляем данные в модель
        addPaginationAttributes(model, page, totalPages, totalExportedSpeciesPages, pageNumbers, pageNumbersEx);

        // Добавляем данные в модель
        model.addAttribute("firstPage", 0);
        model.addAttribute("lastPage", totalPages - 1);
        model.addAttribute("nextStepPage", Math.min(page + 10, totalPages - 1));
        model.addAttribute("previousStepPage", Math.max(page - 10, 0));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("previousPage", (page > 0) ? page - 1 : 0);
        model.addAttribute("nextPage", (page < totalPages - 1) ? page + 1 : totalPages - 1);
        model.addAttribute("previousDisabled", (page == 0));
        model.addAttribute("nextDisabled", (page == totalPages - 1));
        model.addAttribute("permits", permitsByDateRange);
        model.addAttribute("totalIncidents", dashboardService.getTotalIncidents());
        model.addAttribute("totalPermits", dashboardService.getImportReportByCountry());
        model.addAttribute("importReportByCountry", dashboardService.getImportReportByCountry());

        return "index";
    }

    private List<Integer> generatePageNumbers(int currentPage, int totalPages) {
        if (totalPages == 0) return Collections.emptyList();

        int startPage = Math.max(0, currentPage - 1);
        int endPage = Math.min(totalPages - 1, startPage + 2);

        // 💡 Если startPage > endPage — вернём пусто
        if (startPage > endPage) return Collections.emptyList();

        return IntStream.rangeClosed(startPage, endPage)
                .boxed()
                .collect(Collectors.toList());
    }


    private void addPaginationAttributes( Model model, int currentPage, int totalPages, int totalExportedSpeciesPages,
                                         List<Integer> pageNumbers, List<Integer> pageNumbersEx) {
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("pageNumbersEx", pageNumbersEx);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalExportedSpeciesPage", totalExportedSpeciesPages);
        model.addAttribute("previousPage", (currentPage > 0) ? currentPage - 1 : 0);
        model.addAttribute("nextPage", (currentPage < totalPages - 1) ? currentPage + 1 : totalPages - 1);
        model.addAttribute("nextPageEx", (currentPage < totalExportedSpeciesPages - 1) ? currentPage + 1 : totalExportedSpeciesPages - 1);
        model.addAttribute("previousDisabled", (currentPage == 0));
        model.addAttribute("nextDisabled", (currentPage == totalPages - 1));
        model.addAttribute("nextDisabledEx", (currentPage == totalExportedSpeciesPages - 1));

    }

    private static void extracted(Page<CitesPermit> permitsByDateRange) {
        for (CitesPermit permit : permitsByDateRange) {
            if (permit.getStatus() == DocStatus.USED) {
                permit.setUsed(true);
                permit.setUnused(false);
            } else if (permit.getStatus() == DocStatus.UNUSED) {
                permit.setUsed(false);
                permit.setUnused(true);
            } else {
                permit.setUsed(false);
                permit.setUnused(false); // для других статусов
            }
        }
    }
}