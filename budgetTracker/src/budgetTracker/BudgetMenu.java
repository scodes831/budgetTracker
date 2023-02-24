package budgetTracker;

import java.sql.Connection;
import java.util.Formatter;
import java.util.Scanner;

public class BudgetMenu extends Menu {

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
				"Budget Menu Options:\n1 - Add Budget\n2 - Display Budget\n3 - Edit Budget\n4 - Back to Main Menu");
		Scanner in = new Scanner(System.in);
		int selection = in.nextInt();
		return selection;
	}

	public void processSelection(Household household, Budget budget, Menu mainMenu, int selection, Connection connection, UsersTable usersTable,
			BudgetActualTable budgetActualTable, PurchasesTable purchasesTable) {
		switch (selection) {
		case 1:
			Budget newBudget = Budget.initializeBudget(household, Household.generateBudgetName());
			newBudget.setUpBudget(household, newBudget, connection, budgetActualTable);
			System.out.println("displaying budget for " + newBudget.budgetMonthString(newBudget) + " " + newBudget.getBudgetYear());
			Formatter budgetTable = newBudget.displayBudget(household, newBudget);
			budgetTable.close();
			break;
		case 2:
			budgetActualTable.readAllBudgetNames(connection, household);
			Budget selectedBudgetDisplay = budget.selectABudget(household);
			budgetActualTable.readMonthlyBudget(connection, household, selectedBudgetDisplay);
			System.out.println("Displaying budget for " + selectedBudgetDisplay.budgetMonthString(selectedBudgetDisplay)
					+ " " + selectedBudgetDisplay.getBudgetYear() + ":");
			selectedBudgetDisplay.displayBudget(household, selectedBudgetDisplay);
			break;
		case 3:
			Budget selectedBudgetEdit = budget.selectABudget(household);
			System.out.println("Editing your " + selectedBudgetEdit.budgetMonthString(selectedBudgetEdit) + " "
					+ selectedBudgetEdit.getBudgetYear() + " budget:");
			budget.editBudget(household, selectedBudgetEdit, connection, budgetActualTable);
			break;
		case 4:
			mainMenu.show(household, budget, mainMenu, connection, usersTable, budgetActualTable, purchasesTable);
			break;
		}
		show(household, budget, mainMenu, connection, usersTable, budgetActualTable, purchasesTable);
	}

}
