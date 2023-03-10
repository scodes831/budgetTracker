package budgetTracker;

import java.sql.Connection;
import java.util.Scanner;

public class HouseholdMenu extends Menu {

	public void show(Household household, Menu mainMenu, Connection connection, UsersTable usersTable,
			BudgetActualTable budgetActualTable, PurchasesTable purchasesTable) {
		boolean selectionError;
		do {
			try {
				int selection = showOptions();
				selectionError = false;
				processSelection(household, mainMenu, selection, connection, usersTable, budgetActualTable, purchasesTable);
			} catch (Exception e) {
				selectionError = true;
				System.out.println("Please enter a valid selection.");
			}
		} while (selectionError);

	}

	public int showOptions() {
		System.out.println(
				"Household Menu Options:\n1 - Add a Family Member\n2 - Display Family Members\n3 - Edit Family Members\n4 - Back to Main Menu");
		Scanner in = new Scanner(System.in);
		int selection = in.nextInt();
		return selection;
	}

	public void processSelection(Household household, Menu mainMenu, int selection, Connection connection,
			UsersTable usersTable, BudgetActualTable budgetActualTable, PurchasesTable purchasesTable) {
		switch (selection) {
		case 1:
			household.addFamilyMembers(connection, usersTable);
			household.calculateHouseholdIncome(household);
			break;
		case 2:
			usersTable.readAllUsers(connection, household);
			System.out.println("Displaying family members: \n");
			household.displayFamilyMembers();
			break;
		case 3:
			System.out.println("Editing family members: \n");
			household.editFamilyMembers(household, connection, usersTable);
			household.calculateHouseholdIncome(household);
			break;
		case 4:
			mainMenu.show(household, mainMenu, connection, usersTable, budgetActualTable, purchasesTable);
			break;
		}
		show(household, mainMenu, connection, usersTable, budgetActualTable, purchasesTable);
	}
}
