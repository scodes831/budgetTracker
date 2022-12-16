package budgetTracker;

import java.util.Formatter;
import java.util.Scanner;

public class BudgetMenu extends Menu {

	public void show(Household household, Budget budget, Menu mainMenu) {
		System.out.println("Budget Menu Options:\n1 - Add Budget\n2 - Display Budget\n3 - Edit Budget\n4 - Back to Main Menu");
		Scanner in = new Scanner(System.in);
		int selection = in.nextInt();
		switch (selection) {
		case 1:
			System.out.println("Setting up a new budget:");
			budget.setUpBudget(household);
			Formatter budgetTable = budget.displayBudget(household);
			budgetTable.close();
			break;
		case 2:
			System.out.println("Displaying current budget:");
			budget.displayBudget(household);
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
