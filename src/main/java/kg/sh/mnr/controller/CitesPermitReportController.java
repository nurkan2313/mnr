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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.thymeleaf.util.StringUtils.capitalizeWords;

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
        LocalDate start = (startDate != null && !startDate.isEmpty()) ? LocalDate.parse(startDate, formatter) : null;
        LocalDate end = (endDate != null && !endDate.isEmpty()) ? LocalDate.parse(endDate, formatter) : null;

        StringBuilder sqlBuilder = new StringBuilder(
                "SELECT p.exporter_country AS country, p.object AS object, EXTRACT(MONTH FROM p.issue_date) AS month, p.quantity " +
                        "FROM cites_permit p WHERE 1=1 "
        );

        if (start != null) {
            sqlBuilder.append("AND (p.issue_date >= :startDate OR p.issue_date IS NULL) ");
        }
        if (end != null) {
            sqlBuilder.append("AND (p.issue_date <= :endDate OR p.issue_date IS NULL) ");
        }

        if (region != null && !region.isEmpty()) {
            sqlBuilder.append(
                    "AND (p.importer_country IN (SELECT c.name FROM country c WHERE LOWER(c.region) = :region) " +
                            "OR p.exporter_country IN (SELECT c.name FROM country c WHERE LOWER(c.region) = :region)) "
            );
        }

        if (object != null && !object.isEmpty()) {
            sqlBuilder.append("AND to_tsvector('russian', p.object) @@ to_tsquery('russian', :objectQuery) ");
        }

        if (importerCountry != null && !importerCountry.isEmpty()) {
            sqlBuilder.append("AND LOWER(p.importer_country) LIKE :importerCountry ");
        }

        if (exporterCountry != null && !exporterCountry.isEmpty()) {
            sqlBuilder.append("AND LOWER(p.exporter_country) LIKE :exporterCountry ");
        }

        if (companyName != null && !companyName.isEmpty()) {
            sqlBuilder.append("AND LOWER(p.company_name) LIKE :companyName ");
        }

        sqlBuilder.append("ORDER BY p.exporter_country, p.object, EXTRACT(MONTH FROM p.issue_date) ");

        Query query = entityManager.createNativeQuery(sqlBuilder.toString());

        if (start != null) {
            query.setParameter("startDate", java.sql.Date.valueOf(start));
        }
        if (end != null) {
            query.setParameter("endDate", java.sql.Date.valueOf(end));
        }
        if (region != null && !region.isEmpty()) {
            query.setParameter("region", region.toLowerCase());
        }
        if (object != null && !object.isEmpty()) {
            String tsQuery = Arrays.stream(object.toLowerCase().split("\\s+"))
                    .filter(s -> !s.isBlank())
                    .map(word -> word + ":*")
                    .collect(Collectors.joining(" & "));
            query.setParameter("objectQuery", tsQuery);
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

        List<Object[]> results = query.getResultList();
        Map<String, Map<String, BigDecimal>> grouped = new HashMap<>();
        Map<String, String> displayNames = new HashMap<>();

        for (Object[] row : results) {
            String rawCountry = Objects.toString(row[0], "");
            String rawObject = Objects.toString(row[1], "");
            String quantityRaw = Objects.toString(row[3], "");

            String country = clusterCountryName(rawCountry);

            String objectKey = normalizeObjectSmart(rawObject);
            displayNames.putIfAbsent(objectKey, capitalizeWords(rawObject));

            BigDecimal quantityValue = extractNumericValue(quantityRaw);
            if (quantityValue == null) continue;

            grouped
                    .computeIfAbsent(country, k -> new HashMap<>())
                    .merge(objectKey, quantityValue, BigDecimal::add);
        }

        List<Map<String, Object>> response = new ArrayList<>();
        for (var countryEntry : grouped.entrySet()) {
            String country = countryEntry.getKey();
            for (var objectEntry : countryEntry.getValue().entrySet()) {
                String objectKey = objectEntry.getKey();
                BigDecimal total = objectEntry.getValue();

                Map<String, Object> row = new HashMap<>();
                row.put("country", country);
                row.put("object", displayNames.get(objectKey));
                row.put("total_quantity", total);
                response.add(row);
            }
        }

        return ResponseEntity.ok(response);
    }

    private BigDecimal extractNumericValue(String raw) {
        if (raw == null || raw.isBlank()) return null;

        // Извлекаем только первое число (целое или с точкой/запятой)
        Matcher matcher = Pattern.compile("(\\d+[.,]?\\d*)").matcher(raw);
        if (matcher.find()) {
            String numberStr = matcher.group(1).replace(",", ".");
            try {
                return new BigDecimal(numberStr);
            } catch (NumberFormatException e) {
                return null;
            }
        }

        return null;
    }

    private String normalizeCountryName(Object raw) {
        if (raw == null) return "неизвестно";
        return raw.toString().trim().replaceAll("\\s+", " ").toLowerCase();
    }

    private String clusterCountryName(String raw) {
        if (raw == null || raw.isBlank()) return "Неизвестно";

        // Приводим к нижнему регистру, удаляем лишнее
        String cleaned = raw
                .toLowerCase()
                .replaceAll("[^а-яa-zё\\s\\-]", "") // оставляем буквы, пробелы, дефисы
                .replaceAll("\\s+", " ") // нормализуем пробелы
                .trim();

        // Разбиваем строку по дефису или пробелам
        String[] parts = cleaned.split("[- ]");

        // Пытаемся взять последнее слово (вероятнее — страна)
        String lastWord = parts.length > 0 ? parts[parts.length - 1] : cleaned;

        // Кластеры
        if (lastWord.contains("кыргыз") || lastWord.contains("kirgiz") || lastWord.contains("kyrgyz")) {
            return "Кыргызская Республика";
        }
        if (lastWord.contains("казахстан")) {
            return "Республика Казахстан";
        }
        if (lastWord.contains("узбекистан")) {
            return "Республика Узбекистан";
        }
        if (lastWord.contains("таджикистан")) {
            return "Республика Таджикистан";
        }
        if (lastWord.contains("туркменистан")) {
            return "Республика Туркменистан";
        }
        if (lastWord.contains("россия") || lastWord.contains("рф")) {
            return "Российская Федерация";
        }
        if (lastWord.contains("сауд") && lastWord.contains("аравия")) {
            return "Саудовская Аравия";
        }
        if (lastWord.contains("оаэ")) {
            return "Объединенные Арабские Эмираты";
        }
        if (lastWord.contains("сша")) {
            return "Соединенные Штаты Америки";
        }
        if (lastWord.contains("румын")) return "Румыния";
        if (lastWord.contains("польш")) return "Польша";
        if (lastWord.contains("герман") || lastWord.contains("german")) return "Германия";
        if (lastWord.contains("france") || lastWord.contains("франц")) return "Франция";
        if (lastWord.contains("china") || lastWord.contains("китай")) return "Китайская Народная Республика";

        // fallback
        return capitalizeWords(cleaned);
    }


    private String normalizeObjectSmart(String raw) {
        if (raw == null || raw.isBlank()) return "неизвестно";

        // Удаляем запятые, точки, тире, косые черты, дублирующие пробелы
        String cleaned = raw
                .toLowerCase()
                .replaceAll("[,./\\-]", " ")     // заменяем на пробел
                .replaceAll("[^а-яa-zё\\s]", "") // удаляем всё, кроме букв
                .replaceAll("\\s+", " ")         // нормализуем пробелы
                .trim();

        // Разбиваем, удаляем дубликаты, сортируем
        Set<String> uniqueWords = new TreeSet<>(Arrays.asList(cleaned.split(" ")));

        return String.join(" ", uniqueWords);
    }

}