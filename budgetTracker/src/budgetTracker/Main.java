package budgetTracker;

public class Main {
	
	public static void main(String[] args) {
		Menu.welcomeUser();
		Household household = new Household();
		int[] budgetName = generateBudgetName();
		Budget budget = new Budget(budgetName[0], budgetName[1]);
		household.addFamilyMembers();
		MainMenu mainMenu = new MainMenu();
		mainMenu.show(household, budget, mainMenu);
	}

}
