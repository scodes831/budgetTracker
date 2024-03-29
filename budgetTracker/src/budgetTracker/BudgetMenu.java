package budgetTracker;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Formatter;
import java.util.Scanner;

public class BudgetMenu extends Menu {

	public void show(Household household, Menu mainMenu, Connection connection, UsersTable usersTable,
			BudgetActualTable budgetActualTable, PurchasesTable purchasesTable) {
		boolean selectionError;
		do {
			try {
				int selection = showOptions();
				selectionError = false;
				retrieveFromDatabase(household, mainMenu, usersTable, connection, budgetActualTable);
				processSelection(household, mainMenu, selection, connection, usersTable, budgetActualTable,
						purchasesTable);
			} catch (Exception e) {
				System.out.println(e);
				selectionError = true;
				System.out.println("Please enter a valid selection.");
			}
		} while (selectionError);
	}

	public int showOptions() {
		System.out.println(
				"Budget Menu Options:\n1 - Add Budget\n2 - Display Budget\n3 - Edit Budget\n4 - Export Budget\n5 - Back to Main Menu");
		Scanner in = new Scanner(System.in);
		int selection = in.nextInt();
		return selection;
	}

	public void processSelection(Household household, Menu mainMenu, int selection, Connection connection,
			UsersTable usersTable, BudgetActualTable budgetActualTable, PurchasesTable purchasesTable) {
		switch (selection) {
		case 1:
			if ((!household.hasZeroFamilyMembers(household)) || household.getIncome() != 0) {
				Budget newBudget = Budget.initializeBudget(household, Household.generateBudgetName());
				newBudget.setUpBudget(household, newBudget, connection, budgetActualTable);
				System.out.println("Displaying budget for " + Budget.budgetMonthString(newBudget) + " "
						+ newBudget.getBudgetYear());
				budgetActualTable.readMonthlyBudget(connection, household, newBudget);
				Formatter budgetTable = newBudget.displayBudget(household, newBudget);
				budgetTable.close();
			} else {
				System.out.println("Household income is $0. Add a household member or update income to continue.");
			}
			break;
		case 2:
			Budget selectedBudgetDisplay = Budget.selectABudget(household, "display");
			budgetActualTable.readMonthlyBudget(connection, household, selectedBudgetDisplay);
			purchasesTable.readAllPurchases(connection, household);
			household.updateDisplayBudgetActualRemainingValues(budgetActualTable, selectedBudgetDisplay, connection);
			System.out.println("Displaying budget for " + Budget.budgetMonthString(selectedBudgetDisplay) + " "
					+ selectedBudgetDisplay.getBudgetYear() + ":");
			selectedBudgetDisplay.displayBudget(household, selectedBudgetDisplay);
			break;
		case 3:
			Budget selectedBudgetEdit = Budget.selectABudget(household, "display");
			System.out.println("Editing your " + Budget.budgetMonthString(selectedBudgetEdit) + " "
					+ selectedBudgetEdit.getBudgetYear() + " budget:");
			selectedBudgetEdit.editBudget(household, selectedBudgetEdit, connection, budgetActualTable);
			break;
		case 4:
			Budget selectedBudgetExport = Budget.selectABudget(household, "export");
			budgetActualTable.readMonthlyBudget(connection, household, selectedBudgetExport);
			purchasesTable.readAllPurchases(connection, household);
			household.updateDisplayBudgetActualRemainingValues(budgetActualTable, selectedBudgetExport, connection);
			selectedBudgetExport.displayBudget(household, selectedBudgetExport);
			LocalDate budgetDate = LocalDate.of(selectedBudgetExport.getBudgetYear(),
					selectedBudgetExport.getBudgetMonth(), 1);
			String query = String.format("select * from budgetvsactual where budgetname = '%s' order by rowid",
					Date.valueOf(budgetDate));
			DatabaseManager.exportData(connection, query, "monthly budget", selectedBudgetExport);
			System.out.println("Your budget for " + selectedBudgetExport.getBudgetMonth() + " "
					+ selectedBudgetExport.getBudgetYear() + " has been exported successfully.");
		case 5:
			mainMenu.show(household, mainMenu, connection, usersTable, budgetActualTable, purchasesTable);
			break;
		}
		show(household, mainMenu, connection, usersTable, budgetActualTable, purchasesTable);
	}

	public void retrieveFromDatabase(Household household, Menu mainMenu, UsersTable usersTable, Connection connection,
			BudgetActualTable budgetActualTable) {
		usersTable.readAllUsers(connection, household);
		household.calculateHouseholdIncome(household);
		budgetActualTable.readAllBudgetNames(connection, household);
	}

}
