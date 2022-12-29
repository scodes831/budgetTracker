package budgetTracker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Purchase {

	String category;
	double amount;
	String purchasedBy;
	LocalDate datePurchased;

	Purchase(String category, double amount, String purchasedBy, LocalDate datePurchased) {
		this.category = category;
		this.amount = amount;
		this.purchasedBy = purchasedBy;
		this.datePurchased = datePurchased;
	}

	public static void showAllPurchases(Household household) {
		System.out.println("\n\n-----------------------------------");
		System.out.println("Displaying all purchases...\n");
		for (Purchase purchase : household.getPurchasesList()) {
			System.out.println(purchase.getPurchasedBy().toUpperCase() + " spent " + "$" + purchase.getAmount() + " on "
					+ purchase.getCategory() + " on " + purchase.getDatePurchased());
		}
		System.out.println("\n\n-----------------------------------\n\n");
	}

	public static void showPurchasesByFamilyMember(Household household) {
		System.out.println("Enter family member name to view purchases:");
		Scanner in = new Scanner(System.in);
		String nameInput = in.next();
		System.out.println("\n\n-----------------------------------");
		System.out.println("Displaying all purchases for " + nameInput + "\n");
		for (Purchase purchase : household.getPurchasesList()) {
			if (purchase.getPurchasedBy().toLowerCase().equals(nameInput.toLowerCase())) {
				System.out.println("Date: " + purchase.getDatePurchased() + " Category: " + purchase.getCategory()
						+ " Amount: $" + purchase.getAmount());
			}
		}
		System.out.println("\n\n-----------------------------------\n\n");
	}

	public static void showPurchasesByDate(ArrayList<Purchase> purchasesList) {
		System.out.println(
				"How do you want to view purchases?\na - Purchases Today\nb - Purchases This Week\nc - Purchases This Month");
		Scanner in = new Scanner(System.in);
		String selection = in.next();
		LocalDate todaysDate = LocalDate.now();
		switch (selection) {
		case "a":
			ArrayList<Purchase> purchasesToday = viewPurchasesToday(purchasesList, todaysDate);
			displayPurchases(purchasesToday);
			break;
		case "b":
			ArrayList<Purchase> purchasesThisWeek = viewPurchasesThisWeek(purchasesList, todaysDate);
			displayPurchases(purchasesThisWeek);
			break;
		case "c":
			ArrayList<Purchase> purchasesThisMonth = viewPurchasesThisMonth(purchasesList, todaysDate);
			displayPurchases(purchasesThisMonth);
			break;
		}
	}

	public static void displayPurchases(ArrayList<Purchase> purchasesList) {
		int purchasesCount = 1;
		for (Purchase purchase : purchasesList) {
			System.out.println(
					purchasesCount + " Date: " + purchase.getDatePurchased() + " Category: " + purchase.getCategory()
							+ " Amount: $" + purchase.getAmount() + " Purchased By: " + purchase.getPurchasedBy());
			purchasesCount++;
		}
	}

	public static void editPurchases(Household household, Budget budget) {
		displayPurchases(household.getPurchasesList());
		System.out.println("\nEnter the line number of the purchase you want to edit: ");
		Scanner in = new Scanner(System.in);
		int purchaseIndex = in.nextInt() - 1;
		String newCategory = PromptUserInput.promptUserCategoryInput(household);
		double newAmount = PromptUserInput.promptUserAmountInput(household);
		String newPurchasedBy = PromptUserInput.promptUserNameInput(household, budget);
		LocalDate newDatePurchased = PromptUserInput.promptUserDateInput(household, budget);
		Purchase oldPurchase = household.getPurchasesList().get(purchaseIndex);
		Purchase newPurchase = new Purchase(newCategory, newAmount, newPurchasedBy, newDatePurchased);
		household.getPurchasesList().set(purchaseIndex, newPurchase);
	}

	public static void updatePurchasedBy(Household household, String oldName, String newName) {
		for (Purchase purchase : household.getPurchasesList()) {
			if (purchase.getPurchasedBy().toLowerCase().equals(oldName.toLowerCase())) {
				purchase.setPurchasedBy(newName);
			}
		}
	}

	public static ArrayList<Purchase> viewPurchasesToday(ArrayList<Purchase> purchasesList, LocalDate todaysDate) {
		ArrayList<Purchase> purchasesToday = new ArrayList<Purchase>();
		for (Purchase purchase : purchasesList) {
			if (todaysDate.compareTo(purchase.getDatePurchased()) == 0) {
				purchasesToday.add(purchase);
			}
		}
		return purchasesToday;

	}

	public static ArrayList<Purchase> viewPurchasesThisWeek(ArrayList<Purchase> purchasesList, LocalDate todaysDate) {
		ArrayList<Purchase> purchasesThisWeek = new ArrayList<Purchase>();
		LocalDate weekRange = todaysDate.minusDays(7);
		for (Purchase purchase : purchasesList) {
			if (purchase.getDatePurchased().compareTo(weekRange) > -1
					&& purchase.getDatePurchased().compareTo(todaysDate) < 1) {
				purchasesThisWeek.add(purchase);
			}
		}
		return purchasesThisWeek;

	}

	public static ArrayList<Purchase> viewPurchasesThisMonth(ArrayList<Purchase> purchasesList, LocalDate todaysDate) {
		ArrayList<Purchase> purchasesThisMonth = new ArrayList<Purchase>();
		for (Purchase purchase : purchasesList) {
			if (todaysDate.getMonthValue() == purchase.getDatePurchased().getMonthValue()
					&& todaysDate.getYear() == purchase.getDatePurchased().getYear()) {
				purchasesThisMonth.add(purchase);
			}
		}
		return purchasesThisMonth;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getPurchasedBy() {
		return purchasedBy;
	}

	public void setPurchasedBy(String purchasedBy) {
		this.purchasedBy = purchasedBy;
	}

	public LocalDate getDatePurchased() {
		return datePurchased;
	}

	public void setDatePurchased(LocalDate datePurchased) {
		this.datePurchased = datePurchased;
	}
}
