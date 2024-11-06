package kg.core.mnr.service;

import kg.core.mnr.models.dto.enums.DocStatus;
import kg.core.mnr.models.entity.CitesPermit;
import kg.core.mnr.models.entity.dict.Country;
import kg.core.mnr.models.entity.dict.Product;
import kg.core.mnr.repository.CitesPermitRepository;
import kg.core.mnr.repository.CountryRepository;
import kg.core.mnr.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ExcelUploadService {

    private static final DateTimeFormatter DATE_FORMATTER_FULL = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private static final DateTimeFormatter DATE_FORMATTER_SHORT = DateTimeFormatter.ofPattern("dd.MM.yy");

    private final ProductRepository productRepository;
    private final CountryRepository countryRepository;

    public List<CitesPermit> parseExcelFile(MultipartFile file) throws IOException {
        List<CitesPermit> permits = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(file.getInputStream());

        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            Sheet sheet = workbook.getSheetAt(i);

            // Проходим по всем строкам каждого листа
            for (Row row : sheet) {
                // Пропускаем первые две строки
                if (row.getRowNum() < 3) continue;

                CitesPermit permit = new CitesPermit();

                permit.setId(parseIdCell(row.getCell(1)));
                permit.setIssueDate(parseDateCell(row.getCell(2)));
                permit.setCompanyName(parseStringCell(row.getCell(3)));
                permit.setObject(parseStringCell(row.getCell(4)));
                permit.setQuantity(parseStringCell(row.getCell(5)));

                // Используем метод checkAndCreateCountry для импорта и экспорта стран
                String importerCountryName = parseStringCell(row.getCell(6));
                String exporterCountryName = parseStringCell(row.getCell(7));

                UUID importerCountryId = checkAndCreateCountry(importerCountryName);
                UUID exporterCountryId = checkAndCreateCountry(exporterCountryName);

                permit.setImporterCountry(importerCountryName);
                permit.setExporterCountry(exporterCountryName);

                permit.setExportId(exporterCountryId);
                permit.setImportId(importerCountryId);

                permit.setPurpose(parseStringCell(row.getCell(8)));
                permit.setRemarks(parseStringCell(row.getCell(9)));
                permit.setProtectionMarkNumber(String.valueOf(parseNumericCell(row.getCell(10))));
                permit.setStatus(DocStatus.USED);

                // Проверка существования продукта и его создание при необходимости
                UUID id = checkAndCreateProduct(row.getCell(4));
                permit.setObjectId(id);
                permits.add(permit);
            }
        }

        workbook.close();
        return permits;
    }

    private String parseIdCell(Cell cell) {
        if (cell == null) return null;

        try {
            if (cell.getCellType() == CellType.STRING) {
                return cell.getStringCellValue();
            }
        } catch (Exception e) {
            System.err.println("Ошибка при парсинге ID: " + e.getMessage());
        }
        return null;
    }

    private LocalDateTime parseDateCell(Cell cell) {
        if (cell == null) return null;

        try {
            if (cell.getCellType() == CellType.NUMERIC) {
                return cell.getLocalDateTimeCellValue();
            } else if (cell.getCellType() == CellType.STRING) {
                String dateString = cell.getStringCellValue();
                try {
                    // Пытаемся сначала распарсить с полным форматом
                    return LocalDateTime.parse(dateString, DATE_FORMATTER_FULL);
                } catch (Exception e) {
                    // Если не получилось, пробуем с коротким форматом
                    LocalDate date = LocalDate.parse(dateString, DATE_FORMATTER_SHORT);
                    return date.atStartOfDay();
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка при парсинге даты: " + e.getMessage());
        }
        return null;
    }

    // Метод для обработки числовых ячеек
    private Integer parseNumericCell(Cell cell) {
        if (cell == null) return null;

        try {
            if (cell.getCellType() == CellType.NUMERIC) {
                return (int) cell.getNumericCellValue();
            } else if (cell.getCellType() == CellType.STRING) {
                return Integer.parseInt(cell.getStringCellValue());
            }
        } catch (Exception e) {
            System.err.println("Ошибка при парсинге числового значения: " + e.getMessage());
        }
        return null;
    }

    // Метод для обработки строковых ячеек
    private String parseStringCell(Cell cell) {
        if (cell == null) return null;

        try {
            if (cell.getCellType() == CellType.STRING) {
                return cell.getStringCellValue();
            } else if (cell.getCellType() == CellType.NUMERIC) {
                return String.valueOf(cell.getNumericCellValue());
            }
        } catch (Exception e) {
            System.err.println("Ошибка при парсинге строкового значения: " + e.getMessage());
        }
        return null;
    }

    private UUID checkAndCreateProduct(Cell cell) {
        if (cell == null) return null;

        String productCode = parseStringCell(cell);
        if (productCode == null) return null;

        // Проверка существования продукта по описанию
        Optional<Product> existingProduct = productRepository.findByDescription(productCode);

        if (existingProduct.isPresent()) {
            // Если продукт уже существует, возвращаем его ID
            return existingProduct.get().getId();
        } else {
            // Если продукта нет, создаем новый с новым UUID
            UUID id = UUID.randomUUID();
            Product newProduct = new Product();
            newProduct.setId(id);
            newProduct.setDescription(productCode); // Укажите значения для других полей при необходимости

            productRepository.save(newProduct); // Сохраняем новый продукт
            return id; // Возвращаем ID нового продукта
        }
    }

    private UUID checkAndCreateCountry(String countryName) {
        if (countryName == null || countryName.trim().isEmpty()) {
            return null;
        }

        // Проверка существования страны по названию
        Optional<Country> existingCountry = countryRepository.findByName(countryName);

        if (existingCountry.isPresent()) {
            // Если страна уже существует, возвращаем её ID
            return existingCountry.get().getId();
        } else {
            // Если страны нет, создаем новую с новым UUID
            UUID id = UUID.randomUUID();
            Country newCountry = new Country();
            newCountry.setId(id);
            newCountry.setName(countryName); // Установите другие свойства при необходимости

            countryRepository.save(newCountry); // Сохраняем новую страну
            return id; // Возвращаем ID новой страны
        }
    }
}
