package budgetTracker;

import java.sql.Connection;

public interface SubMenu {
	
	public abstract void show(Household household, Menu parentMenu, Menu mainMenu, Connection connection, UsersTable usersTable,
			BudgetActualTable budgetActualTable, PurchasesTable purchasesTable);
	public abstract String showOptions();
	public abstract void processSelection(Household household, Menu parentMenu, Menu mainMenu, String selection, Connection connection, UsersTable usersTable,
			BudgetActualTable budgetActualTable, PurchasesTable purchasesTable);

}
