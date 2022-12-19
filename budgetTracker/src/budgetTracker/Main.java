package budgetTracker;

public class Main {
	
	public static void main(String[] args) {
		Menu.welcomeUser();
		Household household = new Household();
		Budget budget = Budget.initializeBudget(household, Household.generateBudgetName());
		budget.setUpBudget(household, budget);
		household.addFamilyMembers();
		MainMenu mainMenu = new MainMenu();
		mainMenu.show(household, budget, mainMenu);
	}
}
