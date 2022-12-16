package budgetTracker;

import java.time.LocalDate;
import java.util.Scanner;

public class PurchaseMenu extends Menu {

	public void show(Household household, Budget budget, Menu mainMenu) {
		boolean selectionError;
		do {
			try {
				int selection = showOptions();
				selectionError = false;
				processSelection(household, budget, mainMenu, selection);
			} catch (Exception e) {
				selectionError = true;
				System.out.println("Please enter a valid selection.");
			}
		} while (selectionError);
	}

	public static void addPurchaseMenu(Household household, Budget budget) {
		boolean purchaseAdded = false;
		do {
			LocalDate datePurchased = PromptUserInput.promptUserDateInput(household, budget);
			String purchasedBy = PromptUserInput.promptUserNameInput(household, budget);
			double amount = PromptUserInput.promptUserAmountInput(household);
			String category = PromptUserInput.promptUserCategoryInput(household);
			household.addPurchase(category, amount, purchasedBy, datePurchased);
			System.out.println(
					"this is the purchase:\n" + datePurchased + " " + purchasedBy + " " + +amount + " " + category);
			purchaseAdded = true;
		} while (!purchaseAdded);

	}

	public int showOptions() {
		System.out.println(
				"Purchase Menu Options:\n1 - Add a Purchase\n2 - Display Purchases\n3 - Edit a Purchase\n4 - Back to Main Menu");
		Scanner in = new Scanner(System.in);
		int selection = in.nextInt();
		return selection;
	}

	public void processSelection(Household household, Budget budget, Menu mainMenu, int selection) {
		switch (selection) {
		case 1:
			addPurchaseMenu(household, budget);
			break;
		case 2:
			SubPurchaseMenu subPurchaseMenu = new SubPurchaseMenu();
			subPurchaseMenu.show(household, budget, new PurchaseMenu(), mainMenu);
			break;
		case 3:
			System.out.println("Edit a purchase");
			Purchase.editPurchases(household, budget);
			break;
		case 4:
			mainMenu.show(household, budget, mainMenu);
			break;
		}
		show(household, budget, mainMenu);
	}

}
