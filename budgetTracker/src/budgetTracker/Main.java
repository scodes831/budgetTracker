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
		
		Menu.welcomeUser();
		Household household = new Household();
		Budget budget = Budget.initializeBudget(household, Household.generateBudgetName());
		budget.setUpBudget(household, budget, connection, budgetActualTable);
		household.addFamilyMembers(connection, usersTable);
		MainMenu mainMenu = new MainMenu();
		mainMenu.show(household, budget, mainMenu);
	}
}
