package kg.sh.mnr.service;

import kg.sh.mnr.entity.CitesPermit;
import kg.sh.mnr.repository.CountryRepository;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@AllArgsConstructor
@Service
public class CitesPermitReportService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final CountryRepository countryRepository;

    public void generateCitesPermitReport(List<CitesPermit> citesPermits, OutputStream outputStream) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("CITES Permit Report by Region");

        String[] headers = {
                "Region", "Country", "ID", "Issue Date", "Expiry Date", "Company Name", "Object", "Quantity", "Measure", "Importer Country",
                "Exporter Country", "Status", "Purpose", "Remarks", "Protection Mark Number"
        };

        Row headerRow = sheet.createRow(0);
        CellStyle headerCellStyle = getHeaderCellStyle(workbook);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerCellStyle);
        }

        int rowNum = 1;
        for (CitesPermit permit : citesPermits) {
            String importerRegion = getRegionByCountry(permit.getImporterCountry());
            String exporterRegion = getRegionByCountry(permit.getExporterCountry());

            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(importerRegion != null ? importerRegion : "Unknown");
            row.createCell(1).setCellValue(permit.getImporterCountry());
            row.createCell(2).setCellValue(permit.getId());
            row.createCell(3).setCellValue(permit.getIssueDate() != null ? permit.getIssueDate().format(DATE_FORMATTER) : "");
            row.createCell(4).setCellValue(permit.getExpiryDate() != null ? permit.getExpiryDate().format(DATE_FORMATTER) : "");
            row.createCell(5).setCellValue(permit.getCompanyName());
            row.createCell(6).setCellValue(permit.getObject());
            row.createCell(7).setCellValue(permit.getQuantity());
            row.createCell(8).setCellValue(permit.getMeasure());
            row.createCell(9).setCellValue(permit.getImporterCountry());
            row.createCell(10).setCellValue(permit.getExporterCountry());
            row.createCell(11).setCellValue(permit.getStatus() != null ? permit.getStatus().name() : "");
            row.createCell(12).setCellValue(permit.getPurpose());
            row.createCell(13).setCellValue(permit.getRemarks());
            row.createCell(14).setCellValue(permit.getProtectionMarkNumber());
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(outputStream);
        workbook.close();
    }

    private String getRegionByCountry(String countryName) {
        // Реализация поиска региона по названию страны
        // Например, с использованием репозитория
        return countryRepository.findRegionByCountryName(countryName);
    }


    private CellStyle getHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }
}
