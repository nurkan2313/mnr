package kg.core.mnr.controller;

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
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/reports/cites-permit")
public class CitesPermitReportController {

    private final CitesPermitReportService reportService;
    private final CitesPermitRepositoryImpl citesPermitRepository;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DictionaryService dictionaryService;
    private final CountryRepository countryRepository;

    @GetMapping
    public ResponseEntity<byte[]> generateCitesPermitReport(
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
                importerCountry, exporterCountry, object, exporter, startDateTime, endDeateTime);

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

}
