package kg.sh.mnr.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import kg.sh.mnr.entity.CitesPermit;
import kg.sh.mnr.repository.CountryRepository;
import kg.sh.mnr.repository.em.CitesPermitRepositoryImpl;
import kg.sh.mnr.service.CitesPermitReportService;
import kg.sh.mnr.service.DictionaryService;
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
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String companyName) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = null;
        LocalDate end = null;

        // Parse dates if provided
        if (startDate != null && !startDate.isEmpty()) {
            start = LocalDate.parse(startDate, formatter);
        }
        if (endDate != null && !endDate.isEmpty()) {
            end = LocalDate.parse(endDate, formatter);
        }

        String[] monthNames = {
                "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
                "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
        };

        // Base SQL query
        StringBuilder sqlBuilder = new StringBuilder(
                "SELECT p.exporter_country AS country, p.object AS object, EXTRACT(MONTH FROM p.issue_date) AS month, SUM(CAST(p.quantity AS NUMERIC)) AS total_quantity " +
                        "FROM cites_permit p WHERE 1=1 "
        );

        // Add date range filters if dates are provided
        if (start != null) {
            sqlBuilder.append("AND (p.issue_date >= :startDate OR p.issue_date IS NULL) ");
        }
        if (end != null) {
            sqlBuilder.append("AND (p.issue_date <= :endDate OR p.issue_date IS NULL) ");
        }

        // Region filter
        if (region != null && !region.isEmpty()) {
            sqlBuilder.append(
                    "AND (p.importer_country IN (SELECT c.name FROM country c WHERE LOWER(c.region) = :region) " +
                            "OR p.exporter_country IN (SELECT c.name FROM country c WHERE LOWER(c.region) = :region)) "
            );
        }

        // Object filter
        if (object != null && !object.isEmpty()) {
            sqlBuilder.append("AND LOWER(p.object) LIKE :object ");
        }

        // Importer country filter
        if (importerCountry != null && !importerCountry.isEmpty()) {
            sqlBuilder.append("AND LOWER(p.importer_country) LIKE :importerCountry ");
        }

        // Exporter country filter
        if (exporterCountry != null && !exporterCountry.isEmpty()) {
            sqlBuilder.append("AND LOWER(p.exporter_country) LIKE :exporterCountry ");
        }

        // Exporter filter
        if (companyName != null && !companyName.isEmpty()) {
            sqlBuilder.append("AND LOWER(p.company_name) LIKE :companyName ");
        }


        // Add date range filters if dates are provided
        if (start != null) {
            sqlBuilder.append("AND (p.issue_date >= :startDate OR p.issue_date IS NULL) ");
        }
        if (end != null) {
            sqlBuilder.append("AND (p.issue_date <= :endDate OR p.issue_date IS NULL) ");
        }

        // Region filter
        if (region != null && !region.isEmpty()) {
            sqlBuilder.append(
                    "AND (p.importer_country IN (SELECT c.name FROM country c WHERE LOWER(c.region) = :region) " +
                            "OR p.exporter_country IN (SELECT c.name FROM country c WHERE LOWER(c.region) = :region)) "
            );
        }

        // Object filter
        if (object != null && !object.isEmpty()) {
            sqlBuilder.append("AND LOWER(p.object) LIKE :object ");
        }

        // Importer country filter
        if (importerCountry != null && !importerCountry.isEmpty()) {
            sqlBuilder.append("AND LOWER(p.importer_country) LIKE :importerCountry ");
        }

        // Exporter country filter
        if (exporterCountry != null && !exporterCountry.isEmpty()) {
            sqlBuilder.append("AND LOWER(p.exporter_country) LIKE :exporterCountry ");
        }

        // Exporter filter
        if (companyName != null && !companyName.isEmpty()) {
            sqlBuilder.append("AND LOWER(p.company_name) LIKE :companyName ");
        }

        // Grouping and sorting
        sqlBuilder.append("GROUP BY p.exporter_country, p.object, EXTRACT(MONTH FROM p.issue_date) ");
        sqlBuilder.append("ORDER BY p.exporter_country, p.object, EXTRACT(MONTH FROM p.issue_date)");

        Query query = entityManager.createNativeQuery(sqlBuilder.toString());

        // Set parameters for date filters
        if (start != null) {
            query.setParameter("startDate", java.sql.Date.valueOf(start));
        }
        if (end != null) {
            query.setParameter("endDate", java.sql.Date.valueOf(end));
        }

        // Set parameters for other filters
        if (region != null && !region.isEmpty()) {
            query.setParameter("region", region.toLowerCase());
        }
        if (object != null && !object.isEmpty()) {
            query.setParameter("object", "%" + object.toLowerCase() + "%");
        }
        if (importerCountry != null && !importerCountry.isEmpty()) {
            query.setParameter("importerCountry", "%" + importerCountry.toLowerCase() + "%");
        }
        if (exporterCountry != null && !exporterCountry.isEmpty()) {
            query.setParameter("exporterCountry", "%" + exporterCountry.toLowerCase() + "%");
        }
        if (companyName != null && !companyName.isEmpty()) {
            query.setParameter("companyName", "%" + companyName.toLowerCase() + "%");
        }

        // Execute query
        List<Object[]> results = query.getResultList();

        // Transform results for response
        List<Map<String, Object>> response = results.stream().map(row -> {
            Map<String, Object> map = new HashMap<>();
            map.put("country", row[0]); // Exporter country
            map.put("object", row[1]); // Exported object
            if (row[2] != null) {
                int monthIndex = ((Number) row[2]).intValue() - 1;
                map.put("month", monthNames[Math.max(0, Math.min(monthIndex, 11))]); // защита от выхода за пределы
            } else {
                map.put("month", "Без даты");
            }
            map.put("total_quantity", row[3]); // Total quantity
            return map;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

}