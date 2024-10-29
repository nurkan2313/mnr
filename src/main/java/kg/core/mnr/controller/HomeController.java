package kg.core.mnr.controller;

import jakarta.servlet.http.HttpServletRequest;
import kg.core.mnr.models.dto.enums.DocStatus;
import kg.core.mnr.models.entity.CitesPermit;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@AllArgsConstructor
@Controller
public class HomeController {

    private final DashboardService dashboardService;

    @GetMapping("/")
    public String homePage(HttpServletRequest request, Model model) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String && authentication.getPrincipal().equals("anonymousUser"))) {
//            User user = (User) authentication.getPrincipal();
//            model.addAttribute("username", user.getUsername());
//        }
//        model.addAttribute("title", "Система Регулирования Разрешений");
        return "home";
    }


    @GetMapping("/dashboard")
    public String dashboardPage(
            @RequestParam(required = false, name = "start") String startDate,
            @RequestParam(required = false, name = "end") String endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        // Формат для dd/MM/yyyy
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Если даты не заданы, устанавливаем текущую дату и дату неделю назад
        if (startDate == null || endDate == null) {
            LocalDate now = LocalDate.now();  // Текущая дата
            LocalDate weekAgo = now.minusWeeks(1);  // Дата неделю назад
            startDate = weekAgo.toString();  // Преобразуем в строку в формате YYYY-MM-DD
            endDate = now.toString();
        }

        Result result = getResult(startDate, endDate, customFormatter);

        Pageable pageable = PageRequest.of(page, size);
        // Фильтруем данные по диапазону дат
        Page<CitesPermit> permitsByDateRange = dashboardService.getPermitsByDateRange(result.startDateTime(), result.endDateTime(),pageable);
        // Передача статусов как флаги в шаблон
        extracted(permitsByDateRange);

        // Определение номеров предыдущей и следующей страниц
        int totalPages = permitsByDateRange.getTotalPages();

        Page<Map<String, Object>> topExportedSpecies = dashboardService.getTopExportedSpecies(result.startDateTime(), result.endDateTime(), pageable);
        int totalExportedSpecies = topExportedSpecies.getTotalPages();

        int startPageExp = Math.max(1, page + 1 - 1);  // Начальная страница
        int endPageExp = Math.min(totalExportedSpecies, startPageExp + 2);  // Конечная страница (максимум 3)

        // Определяем начальную и конечную страницы для отображения
        int startPage = Math.max(1, page + 1 - 1);  // Начальная страница
        int endPage = Math.min(totalPages, startPage + 2);  // Конечная страница (максимум 3)

        List<Integer> pageNumbers = IntStream.rangeClosed(startPage, endPage)
                .boxed()
                .collect(Collectors.toList());

        List<Integer> pageNumbersEx = IntStream.rangeClosed(startPageExp, endPageExp)
                .boxed()
                .collect(Collectors.toList());

        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("pageNumbersEx", pageNumbersEx);
        model.addAttribute("permits", permitsByDateRange);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalExportedSpecies", totalExportedSpecies);
        model.addAttribute("previousPage", (page > 0) ? page - 1 : 0);
        model.addAttribute("nextPage", (page < totalPages - 1) ? page + 1 : totalPages - 1);
        model.addAttribute("nextPageEx", (page < totalExportedSpecies - 1) ? page + 1 : totalExportedSpecies - 1);
        model.addAttribute("previousDisabled", (page == 0));
        model.addAttribute("nextDisabled", (page == totalPages - 1));
        model.addAttribute("nextDisabledEx", (page == totalExportedSpecies - 1));
        model.addAttribute("totalExportedSpecies", totalExportedSpecies);
        model.addAttribute("totalExportedSpeciesPage", totalExportedSpecies);
        // Добавляем отформатированные даты в модель
        model.addAttribute("start", startDate);  // Форматируем дату начала
        model.addAttribute("end", endDate);      // Форматируем дату окончания
        // Остальные данные для дашборда
        model.addAttribute("totalIncidents", dashboardService.getTotalIncidents(result.startDateTime(), result.endDateTime()));
        model.addAttribute("totalPermits", dashboardService.getImportReportByCountry(result.startDateTime(), result.endDateTime()));
        model.addAttribute("importReportByCountry", dashboardService.getImportReportByCountry(result.startDateTime(), result.endDateTime()));
        model.addAttribute("topExportedSpecies", dashboardService.getTopExportedSpecies(result.startDateTime(), result.endDateTime(), pageable));

        return "index";
    }

    private static Result getResult(String startDate, String endDate, DateTimeFormatter customFormatter) {
        LocalDateTime startDateTime;
        LocalDateTime endDateTime;
        try {
            // Парсим даты в формате yyyy-MM-dd (стандартный формат)
            startDateTime = LocalDate.parse(startDate).atStartOfDay();
            endDateTime = LocalDate.parse(endDate).atTime(23, 59, 59);
        } catch (DateTimeParseException e) {
            // Если ошибка, пробуем парсить в формате dd/MM/yyyy
            startDateTime = LocalDate.parse(startDate, customFormatter).atStartOfDay();
            endDateTime = LocalDate.parse(endDate, customFormatter).atTime(23, 59, 59);
        }

        return new Result(startDateTime, endDateTime);
    }

    private record Result(LocalDateTime startDateTime, LocalDateTime endDateTime) {}

    private static void extracted(Page<CitesPermit> permitsByDateRange) {
        for (CitesPermit permit : permitsByDateRange) {
            if (permit.getStatus() == DocStatus.USED) {
                permit.setUnused(false);
            } else {
                permit.setUsed(true);
            }
        }
    }
}