package budgetTracker;

import java.time.LocalDate;
import java.util.Scanner;

public class PurchaseMenu extends Menu {

	public void show(Household household, Budget budget, Menu mainMenu) {
		System.out.println("Purchase Menu Options:\n1 - Add a Purchase\n2 - Display Purchases\n3 - Edit a Purchase\n4 - Back to Main Menu");
		Scanner in = new Scanner(System.in);
		int selection = in.nextInt();
		switch (selection) {
		case 1:
			addPurchaseMenu(household, budget);
			break;
		case 2:
			SubPurchaseMenu subPurchaseMenu = new SubPurchaseMenu();
			subPurchaseMenu.show(household, budget, new PurchaseMenu(), mainMenu);
			break;
		case 3:
			System.out.println("Edit a purchase"); //edit a purchase method needed
			break;
		case 4: 
			mainMenu.show(household, budget, mainMenu);
			break;
		}
		show(household, budget, mainMenu);
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

//	public static void displayPurchasesMenu(Household household) {
//		System.out.println(
//				"Choose an option:\na - Show All Purchases\nb - Show Purchases By Family Member\nc - Show Purchases By Date");
//		Scanner in = new Scanner(System.in);
//		String selection = in.next();
//		switch (selection) {
//		case "a":
//			Purchase.showAllPurchases(household);
//			break;
//		case "b":
//			Purchase.showPurchasesByFamilyMember(household);
//			break;
//		case "c":
//			Purchase.showPurchasesByDate(household.getPurchasesList());
//			break;
//		}
//	}

}
