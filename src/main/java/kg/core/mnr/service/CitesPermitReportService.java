package kg.core.mnr.service;

import kg.core.mnr.models.entity.CitesPermit;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CitesPermitReportService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void generateCitesPermitReport(List<CitesPermit> citesPermits, OutputStream outputStream) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("CITES Permit Report");

        String[] headers = {
                "ID", "Issue Date", "Expiry Date", "Company Name", "Object", "Quantity", "Measure", "Importer Country",
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
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(permit.getId());
            row.createCell(1).setCellValue(permit.getIssueDate() != null ? permit.getIssueDate().format(DATE_FORMATTER) : "");
            row.createCell(2).setCellValue(permit.getExpiryDate() != null ? permit.getExpiryDate().format(DATE_FORMATTER) : "");
            row.createCell(3).setCellValue(permit.getCompanyName());
            row.createCell(4).setCellValue(permit.getObject());
            row.createCell(5).setCellValue(permit.getQuantity());
            row.createCell(6).setCellValue(permit.getMeasure());
            row.createCell(7).setCellValue(permit.getImporterCountry());
            row.createCell(8).setCellValue(permit.getExporterCountry());
            row.createCell(9).setCellValue(permit.getStatus() != null ? permit.getStatus().name() : "");
            row.createCell(10).setCellValue(permit.getPurpose());
            row.createCell(11).setCellValue(permit.getRemarks());
            row.createCell(12).setCellValue(permit.getProtectionMarkNumber());
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(outputStream);
        workbook.close();
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
