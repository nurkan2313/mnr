package kg.core.mnr.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import kg.core.mnr.models.entity.CitesPermit;
import kg.core.mnr.models.entity.dict.Country;
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
import java.util.*;
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
            @RequestParam(required = false) String companyName) {

        // Форматирование дат
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        String[] monthNames = {
                "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
                "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
        };

        // Базовый SQL-запрос
        StringBuilder sqlBuilder = new StringBuilder(
                "SELECT EXTRACT(MONTH FROM p.issue_date) AS month, COUNT(p.id) " +
                        "FROM cites_permit p " +
                        "WHERE p.issue_date BETWEEN :startDate AND :endDate "
        );

        // Фильтрация по региону (через таблицу Country)
        if (region != null && !region.isEmpty()) {
            sqlBuilder.append(
                    "AND (p.importer_country IN (SELECT c.name FROM country c WHERE LOWER(c.region) = :region) " +
                            "OR p.exporter_country IN (SELECT c.name FROM country c WHERE LOWER(c.region) = :region)) "
            );
        }

        // Фильтрация по объекту
        if (object != null && !object.isEmpty()) {
            sqlBuilder.append("AND LOWER(p.object) LIKE :object ");
        }

        // Фильтрация по стране-импортёру
        if (importerCountry != null && !importerCountry.isEmpty()) {
            sqlBuilder.append("AND LOWER(p.importer_country) LIKE :importerCountry ");
        }

        // Фильтрация по стране-экспортёру
        if (exporterCountry != null && !exporterCountry.isEmpty()) {
            sqlBuilder.append("AND LOWER(p.exporter_country) LIKE :exporterCountry ");
        }

        // Фильтрация по экспортеру
        if (companyName != null && !companyName.isEmpty()) {
            sqlBuilder.append("AND LOWER(p.company_name) LIKE :companyName ");
        }

        // Группировка и сортировка
        sqlBuilder.append("GROUP BY EXTRACT(MONTH FROM p.issue_date) ");
        sqlBuilder.append("ORDER BY EXTRACT(MONTH FROM p.issue_date)");

        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        query.setParameter("startDate", java.sql.Date.valueOf(start));
        query.setParameter("endDate", java.sql.Date.valueOf(end));

        // Установка параметров для фильтров
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

        // Выполнение запроса
        List<Object[]> results = query.getResultList();

        // Преобразование данных для ответа
        List<Map<String, Object>> response = results.stream().map(row -> {
            Map<String, Object> map = new HashMap<>();
            int monthIndex = ((Number) row[0]).intValue() - 1; // Индексация месяцев с 0
            map.put("month", monthNames[monthIndex]); // Название месяца
            map.put("count", row[1]); // Количество
            return map;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }





}