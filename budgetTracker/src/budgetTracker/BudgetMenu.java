package budgetTracker;

import java.util.Formatter;
import java.util.Scanner;

public class BudgetMenu extends Menu {

	public void show(Household household, Budget budget, Menu mainMenu) {
		boolean selectionError;
		do {
			try {
				int selection = showOptions();
				selectionError = false;
				processSelection(household, budget, mainMenu, selection);
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

	public void processSelection(Household household, Budget budget, Menu mainMenu, int selection) {
		switch (selection) {
		case 1:
			budget.setUpBudget(household, budget);
			Formatter budgetTable = budget.displayBudget(household, budget);
			budgetTable.close();
			break;
		case 2:
			System.out.println("Displaying budget for " + budget.budgetMonthString(budget) + " " + budget.getBudgetYear() + ":");
			budget.displayBudget(household, budget);
			break;
		case 3:
			System.out.println("Editing your budget:");
			budget.editBudget(household);
			break;
		case 4:
			mainMenu.show(household, budget, mainMenu);
			break;
		}
		show(household, budget, mainMenu);
	}

}
