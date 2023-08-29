package budgetTracker;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SpreadsheetManager {

	public void createSpreadsheet(ResultSet data, String reportType, Budget budget) throws Exception {

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet();
		LinkedHashMap<String, String> columnHeaders = new LinkedHashMap<String, String>();

		switch (reportType) {
		case "monthly budget":
			System.out.println("Exporting monthly budget");
			columnHeaders.put("category", "String");
			columnHeaders.put("budgetamount", "Big Decimal");
			columnHeaders.put("spendamount", "Big Decimal");
			columnHeaders.put("remainingamount", "Big Decimal");
			break;
		}

		readData(data, workbook, sheet, columnHeaders, budget);
		String filePath = ".//exports/" + generateFileName() + ".xlsx";
		FileOutputStream out = new FileOutputStream(filePath);
		workbook.write(out);
		out.close();
		columnHeaders.clear();
	}

	public void createSpreadsheet(ResultSet data, Connection connection, String reportType, Household household)
			throws Exception {

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet();
		LinkedHashMap<String, String> columnHeaders = new LinkedHashMap<String, String>();

		switch (reportType) {
		case "all purchases":
			System.out.println("Exporting all purchases");
			break;
		case "purchases by user":
			System.out.println("Exporting the user's purchases");
		case "purchases by date":
			System.out.println("Exporting purchases by date range");
		}
		
		columnHeaders.put("purchasedate", "Date");
		columnHeaders.put("category", "String");
		columnHeaders.put("purchasedby", "String");
		columnHeaders.put("purchaseamount", "Big Decimal");

		readData(data, connection, workbook, sheet, columnHeaders, household);
		String filePath = ".//exports/" + generateFileName() + "_all_purchases.xlsx";
		FileOutputStream out = new FileOutputStream(filePath);
		workbook.write(out);
		out.close();
		columnHeaders.clear();
	}

	private void readData(ResultSet data, XSSFWorkbook workbook, XSSFSheet sheet,
			LinkedHashMap<String, String> columnHeaders, Budget budget) throws Exception {
		XSSFRow headerRow = sheet.createRow(0);
		Set<String> keys = columnHeaders.keySet();
		int columns = 0;
		for (String key : keys) {
			headerRow.createCell(columns++).setCellValue(key.toUpperCase());
		}

		int rowNum = 1;
		while (data.next()) {
			XSSFRow row = sheet.createRow(rowNum++);
			columns = 0;
			for (Map.Entry<String, String> entry : columnHeaders.entrySet()) {
				switch (entry.getValue()) {
				case "String":
					String str = data.getString(entry.getKey());
					row.createCell(columns++).setCellValue(str);
					break;
				case "Big Decimal":
					double bd = data.getDouble(entry.getKey());
					row.createCell(columns++).setCellValue(bd);
					break;
				}
			}
		}
		XSSFRow totals = sheet.createRow(rowNum);
		totals.createCell(0).setCellValue("Total:");
		totals.createCell(1).setCellValue(budget.getTotalBudgeted().doubleValue());
		totals.createCell(2).setCellValue(budget.getTotalSpent().doubleValue());
		totals.createCell(3).setCellValue(budget.getTotalRemaining().doubleValue());

	}

	private void readData(ResultSet data, Connection connection, XSSFWorkbook workbook, XSSFSheet sheet,
			LinkedHashMap<String, String> columnHeaders, Household household) throws Exception {
		XSSFRow headerRow = sheet.createRow(0);
		Set<String> keys = columnHeaders.keySet();
		int columns = 0;
		for (String key : keys) {
			headerRow.createCell(columns++).setCellValue(key.toUpperCase());
		}

		int rowNum = 1;
		while (data.next()) {
			XSSFRow row = sheet.createRow(rowNum++);
			columns = 0;
			for (Map.Entry<String, String> entry : columnHeaders.entrySet()) {
				if (entry.getKey().equals("purchasedby")) {
					String purchasedByName = DatabaseManager.getUsernameByUserId(connection, Integer.valueOf(data.getString(entry.getKey())));
					row.createCell(columns++).setCellValue(purchasedByName);
				} else {
					switch (entry.getValue()) {
					case "String":
						String str = data.getString(entry.getKey());
						row.createCell(columns++).setCellValue(str);
						break;
					case "Big Decimal":
						double bd = data.getDouble(entry.getKey());
						row.createCell(columns++).setCellValue(bd);
						break;
					case "Date":
						String[] strDate = data.getString(entry.getKey()).split("-");
						Calendar date = new GregorianCalendar();
						date.set(Integer.valueOf(strDate[0]), Integer.valueOf(strDate[1])-1, Integer.valueOf(strDate[2])); 
						XSSFCellStyle dateStyle = workbook.createCellStyle();
						dateStyle.setDataFormat((short) 14);
						XSSFCell cell = row.createCell(columns++);
						cell.setCellValue(date);
						cell.setCellStyle(dateStyle);
						break;
					}
				}
			}
		}
	}

	private String generateFileName() {
		return String.valueOf(LocalDateTime.now()).replaceAll("-", "_");
	}

}
