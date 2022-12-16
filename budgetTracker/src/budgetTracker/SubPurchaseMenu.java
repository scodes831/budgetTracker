package budgetTracker;

import java.util.Scanner;

public class SubPurchaseMenu implements SubMenu {

	public void show(Household household, Budget budget, Menu purchaseMenu, Menu mainMenu) {
		boolean selectionError;
		do {
			try {
				String selection = showOptions();
				selectionError = false;
				processSelection(household, budget, purchaseMenu, mainMenu, selection);
			} catch (Exception e) {
				selectionError = true;
				System.out.println("Please enter a valid selection.");
			}
		} while (selectionError);
		
	}

	public String showOptions() {
		System.out.println(
				"Choose an option:\na - Show All Purchases\nb - Show Purchases By Family Member\nc - Show Purchases By Date\nd - Back to Purchase Menu");
		Scanner in = new Scanner(System.in);
		String selection = in.next();
		return selection;
	}

	public void processSelection(Household household, Budget budget, Menu parentMenu, Menu mainMenu, String selection) {
		switch (selection) {
		case "a":
			Purchase.showAllPurchases(household);
			break;
		case "b":
			Purchase.showPurchasesByFamilyMember(household);
			break;
		case "c":
			Purchase.showPurchasesByDate(household.getPurchasesList());
			break;
		case "d":
			parentMenu.show(household, budget, mainMenu);
		}
		show(household, budget, parentMenu, mainMenu);
	}
}
