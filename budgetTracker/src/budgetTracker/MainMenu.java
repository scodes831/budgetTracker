package budgetTracker;

import java.sql.Connection;
import java.util.Scanner;

public class MainMenu extends Menu {

	public void show(Household household, Budget budget, Menu mainMenu, Connection connection, UsersTable usersTable,
			BudgetActualTable budgetActualTable, PurchasesTable purchasesTable) {
		boolean selectionError;
		do {
			try {
				int selection = showOptions();
				selectionError = false;
				processSelection(household, budget, mainMenu, selection, connection, usersTable, budgetActualTable, purchasesTable);
			} catch (Exception e) {
				selectionError = true;
				System.out.println("Please enter a valid selection.");
			}
		} while (selectionError);
	}

	public int showOptions() {
		System.out.println(
				"Select a menu to view options:\n1 - Budget Menu\n2 - Household Menu\n3 - Purchase Menu\n4 - Go Back to Previous Menu");
		Scanner in = new Scanner(System.in);
		int selection = in.nextInt();
		return selection;
	}

	public void processSelection(Household household, Budget budget, Menu mainMenu, int selection,
			Connection connection, UsersTable usersTable, BudgetActualTable budgetActualTable,
			PurchasesTable purchasesTable) {
		switch (selection) {
		case 1:
			BudgetMenu budgetMenu = new BudgetMenu();
			budgetMenu.show(household, budget, mainMenu, connection, usersTable, budgetActualTable, purchasesTable);
		case 2:
			HouseholdMenu householdMenu = new HouseholdMenu();
			householdMenu.show(household, budget, mainMenu, connection, usersTable, budgetActualTable, purchasesTable);
		case 3:
			PurchaseMenu purchaseMenu = new PurchaseMenu();
			purchaseMenu.show(household, budget, mainMenu, connection, usersTable, budgetActualTable, purchasesTable);
		}
		show(household, budget, mainMenu, connection, usersTable, budgetActualTable, purchasesTable);
	}
}
