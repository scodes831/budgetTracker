package budgetTracker;

import java.sql.Connection;

public class Main {

	public static void main(String[] args) {
		Connection connection = DatabaseManager.connectDatabase("budgettracker", null, null);
		UsersTable usersTable = new UsersTable();
		usersTable.createUsersTable(connection);
		BudgetActualTable budgetActualTable = new BudgetActualTable();
		budgetActualTable.createBudgetVsActualTable(connection);
		PurchasesTable purchasesTable = new PurchasesTable();
		purchasesTable.createPurchasesTable(connection);
		Menu.welcomeUser();
		Household household = new Household();
		MainMenu mainMenu = new MainMenu();
		mainMenu.show(household, mainMenu, connection, usersTable, budgetActualTable, purchasesTable);
	}
}
