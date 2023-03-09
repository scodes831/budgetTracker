package budgetTracker;

import java.sql.Connection;
import java.util.ArrayList;

public abstract class Menu {

	private static ArrayList<String> activeMenuList = new ArrayList<String>();

	public abstract void show(Household household, Menu mainMenu, Connection connection, UsersTable usersTable,
			BudgetActualTable budgetActualTable, PurchasesTable purchasesTable);

	public abstract int showOptions();

	public abstract void processSelection(Household household, Menu mainMenu, int selection, Connection connection,
			UsersTable usersTable, BudgetActualTable budgetActualTable, PurchasesTable purchasesTable);

	public static void welcomeUser() {
		System.out.println("Welcome to Budget Tracker! Follow the prompts to get started.\n");
	}

	public static ArrayList<String> getActiveMenuList() {
		return activeMenuList;
	}

	public static void setActiveMenuList(ArrayList<String> activeMenuList) {
		Menu.activeMenuList = activeMenuList;
	}
}
