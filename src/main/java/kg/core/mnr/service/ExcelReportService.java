package kg.core.mnr.service;

import kg.core.mnr.models.entity.CitesPermit;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelReportService {

    public void generateCitesPermitReport(List<CitesPermit> citesPermits) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("CITES Permit Report");

        // Заголовки колонок
        String[] headers = {"ID", "Company Name", "Object", "Quantity", "Measure", "Issue Date", "Expiry Date", "Importer Country", "Exporter Country", "Status"};
        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(getHeaderCellStyle(workbook));
        }

        // Заполнение данных
        int rowNum = 1;
        for (CitesPermit permit : citesPermits) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(permit.getId());
            row.createCell(1).setCellValue(permit.getCompanyName());
            row.createCell(2).setCellValue(permit.getObject());
            row.createCell(3).setCellValue(permit.getQuantity());
            row.createCell(4).setCellValue(permit.getMeasure());
            row.createCell(5).setCellValue(permit.getIssueDate().toString());
            row.createCell(6).setCellValue(permit.getExpiryDate().toString());
            row.createCell(7).setCellValue(permit.getImporterCountry());
            row.createCell(8).setCellValue(permit.getExporterCountry());
            row.createCell(9).setCellValue(permit.getStatus().toString());
        }

        // Автоматическое расширение колонок
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Сохранение в файл
        try (FileOutputStream fileOut = new FileOutputStream("CITES_Permit_Report.xlsx")) {
            workbook.write(fileOut);
        }

        workbook.close();
    }

    private CellStyle getHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }
}
