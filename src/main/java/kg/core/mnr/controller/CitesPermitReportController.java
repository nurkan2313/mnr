package kg.core.mnr.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import kg.core.mnr.models.entity.CitesPermit;
import kg.core.mnr.repository.CountryRepository;
import kg.core.mnr.repository.em.CitesPermitRepositoryImpl;
import kg.core.mnr.service.CitesPermitReportService;
import kg.core.mnr.service.DictionaryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/reports/cites-permit")
public class CitesPermitReportController {
    @PersistenceContext
    private EntityManager entityManager;

    private final CitesPermitReportService reportService;
    private final CitesPermitRepositoryImpl citesPermitRepository;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DictionaryService dictionaryService;
    private final CountryRepository countryRepository;

    @GetMapping
    public ResponseEntity<byte[]> generateCitesPermitReport(
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String importerCountry,
            @RequestParam(required = false) String exporterCountry,
            @RequestParam(required = false) String object,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String exporter,
            @RequestParam(required = false) String endDate) {

        // Парсинг строковых дат в LocalDateTime
        LocalDate startDateTime = parseDate(startDate);
        LocalDate endDeateTime = parseDate(endDate);

        if(importerCountry != null && !importerCountry.isEmpty()) {
            importerCountry =  countryRepository.findById(UUID.fromString(importerCountry)).get().getName();
        }

        if(exporterCountry != null && !exporterCountry.isEmpty()) {
            exporterCountry = countryRepository.findById(UUID.fromString(exporterCountry)).get().getName();
        }

        // Получение данных с использованием фильтрации
        List<CitesPermit> filteredData = citesPermitRepository.findByCriteria(
                region, importerCountry, exporterCountry, object, exporter, startDateTime, endDeateTime);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // Генерация отчета в формате Excel и запись в поток
            reportService.generateCitesPermitReport(filteredData, outputStream);

            // Получение байтового массива из потока
            byte[] reportContent = outputStream.toByteArray();

            // Настройка заголовков для загрузки файла
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "CITES_Permit_Report.xlsx");

            return new ResponseEntity<>(reportContent, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error generating report: " + e.getMessage()).getBytes());
        }
    }

    public LocalDate parseDate(String date) {
        if (date == null || date.isEmpty()) {
            System.out.println("Дата пуста или равна null.");
            return null;
        }

        try {
            // Парсим строку в LocalDate с использованием заданного формата
            LocalDate parsedDate = LocalDate.parse(date, formatter);
            System.out.println("Успешный разбор даты: " + parsedDate);
            return parsedDate;
        } catch (DateTimeParseException e) {
            System.err.println("Ошибка разбора даты: " + e.getMessage());
        }

        return null;
    }

    @GetMapping("/export-data")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> getExportData(
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String importerCountry,
            @RequestParam(required = false) String exporterCountry,
            @RequestParam(required = false) String object,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(required = false) String exporter) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        String[] monthNames = {
                "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
                "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
        };

        // Базовый запрос
        StringBuilder queryBuilder = new StringBuilder(
                "SELECT MONTH(p.issueDate), COUNT(p.id) " +
                        "FROM CitesPermit p " +
                        "WHERE p.issueDate BETWEEN :startDate AND :endDate ");

        if (object != null && !object.isEmpty()) {
            queryBuilder.append("AND LOWER(p.object) LIKE LOWER(:object) ");
        }
        if (region != null && !region.isEmpty()) {
            queryBuilder.append("AND LOWER(p.region) LIKE LOWER(:region) ");
        }
        if (importerCountry != null && !importerCountry.isEmpty()) {
            queryBuilder.append("AND LOWER(p.importerCountry) LIKE LOWER(:importerCountry) ");
        }
        if (exporterCountry != null && !exporterCountry.isEmpty()) {
            queryBuilder.append("AND LOWER(p.exporterCountry) LIKE LOWER(:exporterCountry) ");
        }
        if (exporter != null && !exporter.isEmpty()) {
            queryBuilder.append("AND LOWER(p.exporter) LIKE LOWER(:exporter) ");
        }

        queryBuilder.append("GROUP BY MONTH(p.issueDate) ORDER BY MONTH(p.issueDate)");

        Query query = entityManager.createQuery(queryBuilder.toString(), Object[].class);
        query.setParameter("startDate", start);
        query.setParameter("endDate", end);

        if (object != null && !object.isEmpty()) {
            query.setParameter("object", "%" + object + "%");
        }
        if (region != null && !region.isEmpty()) {
            query.setParameter("region", "%" + region + "%");
        }
        if (importerCountry != null && !importerCountry.isEmpty()) {
            query.setParameter("importerCountry", "%" + importerCountry + "%");
        }
        if (exporterCountry != null && !exporterCountry.isEmpty()) {
            query.setParameter("exporterCountry", "%" + exporterCountry + "%");
        }
        if (exporter != null && !exporter.isEmpty()) {
            query.setParameter("exporter", "%" + exporter + "%");
        }

        List<Object[]> results = query.getResultList();

        // Преобразование данных
        List<Map<String, Object>> response = results.stream().map(row -> {
            Map<String, Object> map = new HashMap<>();
            int monthIndex = (int) row[0] - 1; // Индексация месяцев с 0
            map.put("month", monthNames[monthIndex]); // Название месяца
            map.put("count", row[1]); // Количество
            return map;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

}