package budgetTracker;

import java.util.Scanner;

public class SubPurchaseMenu implements SubMenu {

	public void show(Household household, Budget budget, Menu purchaseMenu, Menu mainMenu) {
		System.out.println(
				"Choose an option:\na - Show All Purchases\nb - Show Purchases By Family Member\nc - Show Purchases By Date\nd - Back to Purchase Menu");
		Scanner in = new Scanner(System.in);
		String selection = in.next();
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
			purchaseMenu.show(household, budget, mainMenu);
		}
		show(household, budget, purchaseMenu, mainMenu);
	}
}
