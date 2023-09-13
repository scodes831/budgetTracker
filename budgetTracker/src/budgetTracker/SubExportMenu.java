package budgetTracker;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Scanner;

public class SubExportMenu implements SubMenu {

	public void show(Household household, Menu parentMenu, Menu mainMenu, Connection connection, UsersTable usersTable,
			BudgetActualTable budgetActualTable, PurchasesTable purchasesTable) {
		boolean selectionError;
		do {
			try {
				String selection = showOptions();
				selectionError = false;
				processSelection(household, parentMenu, mainMenu, selection, connection, usersTable, budgetActualTable, purchasesTable);
			} catch (Exception e) {
				selectionError = true;
				System.out.println("Please enter a valid selection.");
			}
		} while (selectionError);
	}

	public String showOptions() {
		System.out.println(
				"Choose an option:\na - Export All Purchases\nb - Export Purchases By Family Member\nc - Export Purchases By Date\nd - Back to Purchase Menu");
		Scanner in = new Scanner(System.in);
		String selection = in.next();
		return selection;
	}

	public void processSelection(Household household, Menu parentMenu, Menu mainMenu, String selection,
			Connection connection, UsersTable usersTable, BudgetActualTable budgetActualTable,
			PurchasesTable purchasesTable) {
		switch (selection) {
		case "a":
			String query = String.format("select * from purchases order by purchasedate");
			DatabaseManager.exportData(connection, query, "all purchases", household);
			break;
		case "b":
			FamilyMember input = PromptUserInput.promptUserSelectionInput(household);
			int userId = DatabaseManager.getUserIdByUsername(connection, input.getName());
			String userPurchasesQuery = String.format("select * from purchases where purchasedby = '%s'", userId);
			DatabaseManager.exportData(connection, userPurchasesQuery, "purchases by user", household);
			break;
		case "c":
			String dateRange = PromptUserInput.promptUserDateSelection();
			LocalDate today = LocalDate.now();
			exportPurchasesByDateRange(connection, household, dateRange, today);
			break;
		case "d":
			parentMenu.show(household, mainMenu, connection, usersTable, budgetActualTable, purchasesTable);
		}
		show(household, parentMenu, mainMenu, connection, usersTable, budgetActualTable, purchasesTable);
	}
	
	private void exportPurchasesByDateRange(Connection connection, Household household, String dateRange, LocalDate today) {
		String query = null;
		Date dateToday = java.sql.Date.valueOf(today);
		switch (dateRange) {
		case "a":
			query = String.format("select * from purchases where purchasedate = '%s'", dateToday);
			break;
		case "b":
			query = String.format("select * from purchases where purchasedate >= '%s' and purchasedate <= '%s'", java.sql.Date.valueOf(today.minusDays(7)), dateToday);
			break;
		case "c":
			query = "select * from purchases where purchasedate between date_trunc('month', current_date) and (date_trunc('month', current_date) + interval '1 month - 1 second');";
			break;
		}
		DatabaseManager.exportData(connection, query, "purchases by date", household);
		
	}

}
