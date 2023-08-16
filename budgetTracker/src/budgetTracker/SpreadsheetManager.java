package budgetTracker;

import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SpreadsheetManager {

	public void createSpreadsheet(ResultSet data, String reportType) throws Exception {

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet();
		LinkedHashMap<String, String> columnHeaders = new LinkedHashMap<String, String>();

		switch (reportType) {

		case "monthly budget":
			System.out.println("Exporting monthly budget");
			columnHeaders.put("Category", "String");
			columnHeaders.put("Budget Amount", "Big Decimal");
			columnHeaders.put("Spend Amount", "Big Decimal");
			columnHeaders.put("Remaining", "Big Decimal");
			break;
		}

		readData(data, workbook, sheet, columnHeaders);
		String filePath = ".//exports/" + generateFileName() + ".xlsx";
		FileOutputStream out = new FileOutputStream(filePath);
		workbook.write(out);
		out.close();
		columnHeaders.clear();
	}

	private void readData(ResultSet data, XSSFWorkbook workbook, XSSFSheet sheet,
			LinkedHashMap<String, String> columnHeaders) throws Exception {
		XSSFRow headerRow = sheet.createRow(0);
		Set<String> keys = columnHeaders.keySet();
		int columns = 0;
		for (String key : keys) {
			headerRow.createCell(columns++).setCellValue(key);
		}
	}

	private String generateFileName() {
		return String.valueOf(LocalDateTime.now()).replaceAll("-", "_");
	}

}
