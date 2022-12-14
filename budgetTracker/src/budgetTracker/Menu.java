package budgetTracker;

import java.util.Formatter;
import java.util.Scanner;

public abstract class Menu {
	private static boolean isAddPurchase = false;
	private static boolean isDisplayPurchase = false;
	private static boolean isEditBudget = false;
	private static boolean isAddBudget = false;
	private static boolean isDisplayBudget = false;
	private static boolean isAddFamilyMember = false;
	
	public static void viewMainMenu(Household household, Budget budget) {
		System.out.println("Choose an option: \n1 - Create a New Budget\n2 - Check Your Budget\n3 - Add a Purchase");
		Scanner in = new Scanner(System.in);
		int userSelection = in.nextInt();
		switch (userSelection) {
		case 1:
			Budget.setUpBudget(household);
			Formatter budgetTable = budget.displayBudget(household);
			budgetTable.close();
			break;
		case 2:
			System.out.println("Let's check your current budget!");
			budget.displayBudget(household);
			break;
		case 3:
			budget.addPurchaseMenu(household);
		}
		viewSubMenu(household, budget);
	}
	
	public static void viewSubMenu(Household household, Budget budget) {
		System.out.println(
				"Choose an option: \n1 - Edit Budget\n2 - Display Budget\n3 - Add a Purchase\n4 - View Household Purchases");
		Scanner in = new Scanner(System.in);
		int userSelection = in.nextInt();
		switch (userSelection) {
		case 1:
			System.out.println("Let's edit your budget!");
			System.out.println(
					"Select a category to edit:\na - Housing\nb - Utilities\nc - Health\nd - Car\ne - Groceries\nf - Dining\ng - Fun\nh - Miscellaneous");
			String categorySel = in.next();
			System.out.println("Enter a new amount:");
			double amountInput = in.nextDouble();
			switch (categorySel) {
			case "a":
				System.out.println("Editing your housing budget with new amount: " + amountInput);
				budget.editBudget(household, "housing", amountInput);
				break;
			case "b":
				budget.editBudget(household, "utilities", amountInput);
				break;
			case "c":
				budget.editBudget(household, "health", amountInput);
				break;
			case "d":
				budget.editBudget(household, "car", amountInput);
				break;
			case "e":
				budget.editBudget(household, "groceries", amountInput);
				break;
			case "f":
				budget.editBudget(household, "dining", amountInput);
				break;
			case "g":
				budget.editBudget(household, "fun", amountInput);
				break;
			case "h":
				budget.editBudget(household, "miscellaneous", amountInput);
				break;
			}
			break;
		case 2:
			System.out.println("Let's check your current budget!");
			Formatter budgetTable = budget.displayBudget(household);
			budgetTable.close();
			break;
		case 3:
			budget.addPurchaseMenu(household);
		case 4:
			budget.purchasesSubMenuOptions(household);
		}
		viewSubMenu(household, budget);
	}

}
