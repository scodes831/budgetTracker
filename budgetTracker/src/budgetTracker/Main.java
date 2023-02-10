package budgetTracker;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDate;

public class Main {
	
	public static void main(String[] args) {
		DatabaseManager dbManager = new DatabaseManager();
		Connection connection = dbManager.connectDatabase("budgettracker", null, null);
		UsersTable usersTable = new UsersTable();
		usersTable.createUsersTable(connection);
		BudgetActualTable budgetActualTable = new BudgetActualTable();
		budgetActualTable.createBudgetVsActualTable(connection);
		PurchasesTable purchasesTable = new PurchasesTable();
		purchasesTable.createPurchasesTable(connection);
		
		Menu.welcomeUser();
		Household household = new Household();
		Budget budget = Budget.initializeBudget(household, Household.generateBudgetName());
		budget.setUpBudget(household, budget, connection, budgetActualTable);
		household.addFamilyMembers(connection, usersTable);
		MainMenu mainMenu = new MainMenu();
		mainMenu.show(household, budget, mainMenu, connection, usersTable, budgetActualTable, purchasesTable);
	}
}

//need to fix:
//1. budgetname and category cannot be unique in budget table but can only have one combo of same budgetname and category
//2.
