package kg.sh.mnr.service;

import com.github.pjfanning.xlsx.StreamingReader;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExcelUploadService {

    private static final DateTimeFormatter DATE_FORMATTER_FULL = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private static final DateTimeFormatter DATE_FORMATTER_SHORT = DateTimeFormatter.ofPattern("dd.MM.yy");

    private final ProductRepository productRepository;
    private final CountryRepository countryRepository;

    private Map<String, UUID> countryCache = new HashMap<>();

    public ExcelUploadService(ProductRepository productRepository, CountryRepository countryRepository) {
        this.productRepository = productRepository;
        this.countryRepository = countryRepository;
    }

    public List<CitesPermit> parseExcelFile(MultipartFile file) throws IOException {
        List<CitesPermit> permits = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(file.getInputStream());

        // 1. Собираем все уникальные страны из Excel
        Set<String> countryNames = new HashSet<>();

        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            Sheet sheet = workbook.getSheetAt(i);
            for (Row row : sheet) {
                if (row.getRowNum() < 3 || isRowEmpty(row)) continue;
                String importer = parseStringCell(row.getCell(6));
                String exporter = parseStringCell(row.getCell(7));
                if (importer != null) countryNames.add(importer.trim());
                if (exporter != null) countryNames.add(exporter.trim());
            }
        }

        // 2. Загружаем или создаём страны (кэш)
        Map<String, UUID> countryMap = preloadOrInsertCountries(countryNames);

        // 3. Снова проходимся по строкам — уже с кэшем
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            Sheet sheet = workbook.getSheetAt(i);
            for (Row row : sheet) {
                if (row.getRowNum() < 3 || isRowEmpty(row)) continue;

                CitesPermit permit = new CitesPermit();

                permit.setId(parseIdCell(row.getCell(1)));
                permit.setIssueDate(parseDateCell(row.getCell(2)));
                permit.setCompanyName(parseStringCell(row.getCell(3)));
                permit.setObject(parseStringCell(row.getCell(4)));
                permit.setQuantity(parseStringCell(row.getCell(5)));

                String importerCountryName = parseStringCell(row.getCell(6));
                String exporterCountryName = parseStringCell(row.getCell(7));

                permit.setImporterCountry(importerCountryName);
                permit.setExporterCountry(exporterCountryName);

                permit.setImportId(getCountryIdSafe(importerCountryName, countryMap));
                permit.setExportId(getCountryIdSafe(exporterCountryName, countryMap));

                permit.setPurpose(parseStringCell(row.getCell(8)));
                permit.setSource(parseStringCell(row.getCell(9)));
                permit.setRemarks(parseStringCell(row.getCell(10)));
                permit.setProtectionMarkNumber(parseStringCell(row.getCell(11)));
                permit.setStatus(DocStatus.USED);

                permits.add(permit);
            }
        }

        workbook.close();
        return permits;
    }

    private UUID getCountryIdSafe(String name, Map<String, UUID> map) {
        if (name == null) return null;
        String key = name.trim().toLowerCase();
        return map.getOrDefault(key, null);
    }

    private Map<String, UUID> preloadOrInsertCountries(Set<String> countryNames) {
        Map<String, UUID> result = new HashMap<>();

        // Приводим имена к нижнему регистру для поиска
        Set<String> normalized = countryNames.stream()
                .filter(Objects::nonNull)
                .map(s -> s.trim().toLowerCase())
                .collect(Collectors.toSet());

        // Загружаем уже существующие
        List<Country> existing = countryRepository.findByNameInIgnoreCase(normalized);
        for (Country c : existing) {
            result.put(c.getName().trim().toLowerCase(), c.getId());
        }

        // Определяем, какие нужно создать
        List<Country> toInsert = new ArrayList<>();
        for (String original : countryNames) {
            if (original == null) continue;
            String key = original.trim().toLowerCase();
            if (!result.containsKey(key)) {
                UUID id = UUID.randomUUID();
                Country newCountry = new Country();
                newCountry.setId(id);
                newCountry.setName(original.trim());
                newCountry.setRegion(null);      // можно задать значение по умолчанию
                newCountry.setShortName(null);   // или оставить пустым
                newCountry.setCode(null);

                toInsert.add(newCountry);
                result.put(key, id);
            }
        }

        if (!toInsert.isEmpty()) {
            countryRepository.saveAll(toInsert);
        }

        return result;
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

    private void preloadCountries() {
        countryCache = new HashMap<>();
        for (Country c : countryRepository.findAll()) {
            countryCache.put(c.getName().toLowerCase(), c.getId());
        }
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

        String key = countryName.trim().toLowerCase();

        // Сначала ищем в кэше
        if (countryCache.containsKey(key)) {
            return countryCache.get(key);
        }

        // Если нет — создаём и добавляем в кэш
        UUID id = UUID.randomUUID();
        Country newCountry = new Country();
        newCountry.setId(id);
        newCountry.setName(countryName);
        countryRepository.save(newCountry);

        countryCache.put(key, id); // добавить в кэш
        return id;
    }

}
