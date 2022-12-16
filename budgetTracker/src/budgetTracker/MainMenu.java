package budgetTracker;

import java.util.Scanner;

public class MainMenu extends Menu {

	public void show(Household household, Budget budget, Menu mainMenu) {
		Menu.getActiveMenuList().add("main");
		System.out.println("Select a menu to view options:\n1 - Budget Menu\n2 - HouseholdMenu\n3 - Purchase Menu\n4 - Go Back to Previous Menu");
		Scanner in = new Scanner(System.in);
		int selection = in.nextInt();
		switch (selection) {
		case 1:
			BudgetMenu budgetMenu = new BudgetMenu();
			budgetMenu.show(household, budget, mainMenu);
		case 2:
			HouseholdMenu householdMenu = new HouseholdMenu();
			householdMenu.show(household, budget, mainMenu);
		case 3:
			PurchaseMenu purchaseMenu = new PurchaseMenu();
			purchaseMenu.show(household, budget, mainMenu);
		}
		show(household, budget, mainMenu);
	}
}
