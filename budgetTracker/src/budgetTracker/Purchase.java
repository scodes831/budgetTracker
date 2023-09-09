package budgetTracker;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class Purchase {
	
	int purchaseId;
	String category;
	BigDecimal amount;
	String purchasedBy;
	LocalDate datePurchased;

	Purchase(String category, BigDecimal amount, String purchasedBy, LocalDate datePurchased) {
		this.category = category;
		this.amount = amount;
		this.purchasedBy = purchasedBy;
		this.datePurchased = datePurchased;
	}

	public static void showAllPurchases(Household household) {
		Purchase.displayPurchases(household.getPurchasesList(), household);
	}

	public static void showPurchasesByFamilyMember(Household household) {
		FamilyMember familyMember = PromptUserInput.promptUserSelectionInput(household);
		ArrayList<Purchase> newList = new ArrayList<Purchase>();
		System.out.println("Displaying all purchases for " + household.capitalizeName(familyMember.getName()) + "\n");
		for (Purchase purchase : household.getPurchasesList()) {
			if (purchase.getPurchasedBy().toLowerCase().equals(familyMember.getName().toLowerCase())) {
				newList.add(purchase);
			}
		}
		Purchase.displayPurchases(newList, household);
		System.out.println("\n\n-----------------------------------\n\n");
	}

	public static void showPurchasesByDate(ArrayList<Purchase> purchasesList, Household household) {
		String selection = PromptUserInput.promptUserDateSelection();
		LocalDate todaysDate = LocalDate.now();
		switch (selection) {
		case "a":
			ArrayList<Purchase> purchasesToday = viewPurchasesToday(purchasesList, todaysDate);
			displayPurchases(purchasesToday, household);
			break;
		case "b":
			ArrayList<Purchase> purchasesThisWeek = viewPurchasesThisWeek(purchasesList, todaysDate);
			displayPurchases(purchasesThisWeek, household);
			break;
		case "c":
			ArrayList<Purchase> purchasesThisMonth = viewPurchasesThisMonth(purchasesList, todaysDate);
			displayPurchases(purchasesThisMonth, household);
			break;
		}
	}

	public static void displayPurchases(ArrayList<Purchase> purchasesList, Household household) {
		Formatter table = new Formatter();
		table.format("%15s %15s %15s %15s\n", "Purchased By", "Amount", "Category", "Date");
		for (Purchase purchase : purchasesList) {
			table.format("%15s %15s %15s %15s\n", household.capitalizeName(purchase.getPurchasedBy()),
					purchase.getAmount(), purchase.getCategory(), purchase.getDatePurchased());
		}
		System.out.println(table);
	}

	public static void editPurchases(Household household, Connection connection, UsersTable usersTable,
			PurchasesTable purchasesTable, BudgetActualTable budgetTable) {
		displayPurchases(household.getPurchasesList(), household);
		System.out.println("\nEnter the line number of the purchase you want to edit: ");
		Scanner in = new Scanner(System.in);
		int purchaseIndex = in.nextInt() - 1;
		String newCategory = PromptUserInput.promptUserCategoryInput(household);
		BigDecimal newAmount = PromptUserInput.promptUserAmountInput(household);
		String newPurchasedBy = PromptUserInput.promptUserNameInput(household, connection, usersTable, purchasesTable);
		LocalDate newDatePurchased = PromptUserInput.promptUserDateInput(household, connection, usersTable,
				purchasesTable, budgetTable);
		Purchase oldPurchase = household.getPurchasesList().get(purchaseIndex);
		Purchase newPurchase = new Purchase(newCategory, newAmount, newPurchasedBy, newDatePurchased);
		household.getPurchasesList().set(purchaseIndex, newPurchase);
		int purchaseId = DatabaseManager.getPurchaseIdByPurchase(connection, oldPurchase.getDatePurchased(),
				oldPurchase.getCategory(), oldPurchase.getPurchasedBy(), oldPurchase.getAmount());
		purchasesTable.updatePurchase(connection, purchaseId, newDatePurchased, newCategory, household.capitalizeName(newPurchasedBy), newAmount);
	}

	public static void updatePurchasedBy(Household household, String oldName, String newName) {
		for (Purchase purchase : household.getPurchasesList()) {
			if (purchase.getPurchasedBy().toLowerCase().equals(oldName.toLowerCase())) {
				purchase.setPurchasedBy(newName);
			}
		}
	}
	
	public Budget matchPurchaseToBudget(Household household) {
		Budget budget = null;
		
		int purchaseYr = this.getDatePurchased().getYear();
		int purchaseMonth = this.getDatePurchased().getMonthValue();
		System.out.println("from the purchase: " + purchaseYr + " " + purchaseMonth);
		
		for (Budget b : household.getBudgets()) {
			System.out.println("from the budget: " + b.getBudgetMonth() + " " + b.getBudgetYear());
			if (b.getBudgetMonth() == purchaseMonth && b.getBudgetYear() == purchaseYr) {
				return b;
			}
		}
		
		return budget;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
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

	public int getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(int purchaseId) {
		this.purchaseId = purchaseId;
	}
}
