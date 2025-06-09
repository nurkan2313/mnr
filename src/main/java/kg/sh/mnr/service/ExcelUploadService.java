package kg.sh.mnr.service;

import kg.sh.mnr.entity.CitesPermit;
import kg.sh.mnr.entity.dict.Country;
import kg.sh.mnr.entity.dict.Product;
import kg.sh.mnr.model.enums.DocStatus;
import kg.sh.mnr.repository.CountryRepository;
import kg.sh.mnr.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
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

            for (Row row : sheet) {
                if (row.getRowNum() < 3) continue;

                // Проверка: если строка полностью пустая — пропускаем
                if (isRowEmpty(row)) continue;

                CitesPermit permit = new CitesPermit();

                permit.setId(parseIdCell(row.getCell(1)));
                permit.setIssueDate(parseDateCell(row.getCell(2)));
                permit.setCompanyName(parseStringCell(row.getCell(3)));
                permit.setObject(parseStringCell(row.getCell(4)));
                permit.setQuantity(parseStringCell(row.getCell(5)));

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
                permit.setProtectionMarkNumber(parseStringCell(row.getCell(10)));
                permit.setStatus(DocStatus.USED);

                permits.add(permit);
            }
        }

        workbook.close();
        return permits;
    }

    private boolean isRowEmpty(Row row) {
        if (row == null) return true;
        for (int c = 0; c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                String val = parseStringCell(cell);
                if (val != null && !val.trim().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
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

    private LocalDate parseDateCell(Cell cell) {
        if (cell == null) return null;

        try {
            if (cell.getCellType() == CellType.NUMERIC) {
                // Если ячейка содержит числовое значение, извлекаем дату
                return cell.getLocalDateTimeCellValue().toLocalDate();
            } else if (cell.getCellType() == CellType.STRING) {
                String dateString = cell.getStringCellValue().trim();
                try {
                    // Пытаемся сначала распарсить с полным форматом
                    return LocalDate.parse(dateString, DATE_FORMATTER_FULL);
                } catch (Exception e) {
                    // Если не получилось, пробуем с коротким форматом
                    return LocalDate.parse(dateString, DATE_FORMATTER_SHORT);
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
                double numericValue = cell.getNumericCellValue();
                // Если значение целое, преобразуем его без дробной части
                if (numericValue == Math.floor(numericValue)) {
                    return String.valueOf((int) numericValue);
                } else {
                    return String.valueOf(numericValue);
                }
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
