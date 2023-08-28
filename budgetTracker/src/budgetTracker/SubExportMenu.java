package budgetTracker;

import java.sql.Connection;
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
			System.out.println(query);
			DatabaseManager.exportData(connection, query, "all purchases", household);
			break;
		case "b":
			FamilyMember input = PromptUserInput.promptUserSelectionInput(household);
			System.out.println("selected user's name is " + input.getName());
			int userId = DatabaseManager.getUserIdByUsername(connection, input.getName());
			System.out.println("selected user userId is " + userId);
			String userPurchasesQuery = String.format("select * from purchases where purchasedby = '%s'", userId);
			DatabaseManager.exportData(connection, userPurchasesQuery, "purchases by user", household);
			break;
		case "c":
			break;
		case "d":
			parentMenu.show(household, mainMenu, connection, usersTable, budgetActualTable, purchasesTable);
		}
		show(household, parentMenu, mainMenu, connection, usersTable, budgetActualTable, purchasesTable);
	}

}
