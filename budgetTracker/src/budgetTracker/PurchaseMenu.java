package budgetTracker;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.Scanner;

public class PurchaseMenu extends Menu {

	public void show(Household household, Menu mainMenu, Connection connection, UsersTable usersTable,
			BudgetActualTable budgetActualTable, PurchasesTable purchasesTable) {
		boolean selectionError;
		do {
			try {
				int selection = showOptions();
				selectionError = false;
				processSelection(household, mainMenu, selection, connection, usersTable, budgetActualTable,
						purchasesTable);
			} catch (Exception e) {
				selectionError = true;
				System.out.println("Please enter a valid selection.");
			}
		} while (selectionError);
	}

	public static void addPurchaseMenu(Household household, Connection connection, UsersTable usersTable,
			PurchasesTable purchasesTable, BudgetActualTable budgetTable) {
		boolean purchaseAdded = false;
		do {
			LocalDate datePurchased = PromptUserInput.promptUserDateInput(household, connection, usersTable,
					purchasesTable, budgetTable);
			String purchasedBy = PromptUserInput.promptUserNameInput(household, connection, usersTable, purchasesTable);
			BigDecimal amount = PromptUserInput.promptUserAmountInput(household);
			String category = PromptUserInput.promptUserCategoryInput(household);
			household.addPurchase(category, amount, purchasedBy, datePurchased, connection, purchasesTable, budgetTable);
			purchaseAdded = true;
		} while (!purchaseAdded);

	}

	public int showOptions() {
		System.out.println(
				"Purchase Menu Options:\n1 - Add a Purchase\n2 - Display Purchases\n3 - Edit a Purchase\n4 - Export Purchases\n5 - Back to Main Menu");
		Scanner in = new Scanner(System.in);
		int selection = in.nextInt();
		return selection;
	}

	public void processSelection(Household household, Menu mainMenu, int selection, Connection connection,
			UsersTable usersTable, BudgetActualTable budgetActualTable, PurchasesTable purchasesTable) {
		switch (selection) {
		case 1:
			usersTable.readAllUsers(connection, household);
			budgetActualTable.readAllBudgetNames(connection, household);
			purchasesTable.readAllPurchases(connection, household);
			addPurchaseMenu(household, connection, usersTable, purchasesTable, budgetActualTable);
			break;
		case 2:
			purchasesTable.readAllPurchases(connection, household);
			SubPurchaseMenu subPurchaseMenu = new SubPurchaseMenu();
			subPurchaseMenu.show(household, new PurchaseMenu(), mainMenu, connection, usersTable, budgetActualTable,
					purchasesTable);
			break;
		case 3:
			purchasesTable.readAllPurchases(connection, household);
			usersTable.readAllUsers(connection, household);
			System.out.println("Edit a purchase");
			Purchase.editPurchases(household, connection, usersTable, purchasesTable, budgetActualTable);
			break;
		case 4:
			usersTable.readAllUsers(connection, household);
			purchasesTable.readAllPurchases(connection, household);
			SubExportMenu subExportMenu = new SubExportMenu();
			subExportMenu.show(household, this, mainMenu, connection, usersTable, budgetActualTable, purchasesTable);
			break;
		case 5:
			mainMenu.show(household, mainMenu, connection, usersTable, budgetActualTable, purchasesTable);
			break;
		}
		show(household, mainMenu, connection, usersTable, budgetActualTable, purchasesTable);
	}

}
