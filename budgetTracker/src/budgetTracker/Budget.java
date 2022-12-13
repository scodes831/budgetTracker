package budgetTracker;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Budget {

	Map<String, ArrayList<Double>> budgetMap = new LinkedHashMap<String, ArrayList<Double>>();

	private Map<String, ArrayList<Double>> makeBudgetMap(Household household) {
		budgetMap.put("housing",
				new ArrayList<>(Arrays.asList(household.getHousingBudget(), household.getHousingSpend())));
		budgetMap.put("utilities",
				new ArrayList<>(Arrays.asList(household.getUtilitiesBudget(), household.getUtilitiesSpend())));
		budgetMap.put("health",
				new ArrayList<>(Arrays.asList(household.getHealthBudget(), household.getHealthSpend())));
		budgetMap.put("car", new ArrayList<>(Arrays.asList(household.getCarBudget(), household.getCarSpend())));
		budgetMap.put("groceries",
				new ArrayList<>(Arrays.asList(household.getGroceryBudget(), household.getGrocerySpend())));
		budgetMap.put("dining",
				new ArrayList<>(Arrays.asList(household.getDiningBudget(), household.getDiningSpend())));
		budgetMap.put("fun", new ArrayList<>(Arrays.asList(household.getFunBudget(), household.getFunSpend())));
		budgetMap.put("miscellaneous",
				new ArrayList<>(Arrays.asList(household.getMiscBudget(), household.getMiscSpend())));
		return budgetMap;
	}

	public void addPurchaseMenu(Household household) {
		Scanner in = new Scanner(System.in);
		System.out.println("When was the purchase made? Please enter the date in MM-DD-YYYY format.");
		String dateInput = in.next();
		String[] dateInputArr = new String[3];
		dateInputArr = dateInput.split("-");
		LocalDate datePurchased = LocalDate.of(Integer.parseInt(dateInputArr[2]),
				Month.of(Integer.parseInt(dateInputArr[0])), Integer.parseInt(dateInputArr[1]));
		System.out.println("purchase date was: " + datePurchased);
		System.out.println("Who made the purchase?");
		String purchasedBy = in.next();
		System.out.println("Enter purchase amount:");
		double amount = in.nextDouble();
		System.out.println(
				"What category was your purchase?\na - Housing\nb - Utilities\nc - Health\nd - Car\ne - Groceries\nf - Dining\ng - Fun\nh - Miscellaneous");
		String categorySel = in.next();
		switch (categorySel) {
		case "a":
			household.addPurchase("housing", amount, purchasedBy, datePurchased);
			System.out.println("Your purchase has been added: " + amount + " for housing.");
			break;
		case "b":
			household.addPurchase("utilities", amount, purchasedBy, datePurchased);
			break;
		case "c":
			household.addPurchase("health", amount, purchasedBy, datePurchased);
			break;
		case "d":
			household.addPurchase("car", amount, purchasedBy, datePurchased);
			break;
		case "e":
			household.addPurchase("grocery", amount, purchasedBy, datePurchased);
			break;
		case "f":
			household.addPurchase("dining", amount, purchasedBy, datePurchased);
			break;
		case "g":
			household.addPurchase("fun", amount, purchasedBy, datePurchased);
			break;
		case "h":
			household.addPurchase("miscellaneous", amount, purchasedBy, datePurchased);
			break;
		default:
			System.out.println("Please enter a valid option a-h");
		}
	}

	public static void setUpBudget(Household household) {
		Scanner in = new Scanner(System.in);

		System.out.println("Let's create a new budget!\nYour total household income is $"
				+ household.calculateHouseholdIncome(household));
		Budget budget = new Budget();
		System.out.println("Enter budget for housing: ");
		double housingBudget = in.nextDouble();
		household.setHousingBudget(housingBudget);

		System.out.println("Enter budget for utilities: ");
		double utilitiesBudget = in.nextDouble();
		household.setUtilitiesBudget(utilitiesBudget);

		System.out.println("Enter budget for health: ");
		double healthBudget = in.nextDouble();
		household.setHealthBudget(healthBudget);

		System.out.println("Enter budget for car: ");
		double carBudget = in.nextDouble();
		household.setCarBudget(carBudget);

		System.out.println("Enter budget for groceries: ");
		double groceryBudget = in.nextDouble();
		household.setGroceryBudget(groceryBudget);

		System.out.println("Enter budget for dining out: ");
		double diningBudget = in.nextDouble();
		household.setDiningBudget(diningBudget);

		System.out.println("Enter budget for fun: ");
		double funBudget = in.nextDouble();
		household.setFunBudget(funBudget);

		System.out.println("Enter budget for miscellaneous: ");
		double miscBudget = in.nextDouble();
		household.setMiscBudget(miscBudget);
	}

	public void editBudget(Household household, String category, double newAmount) {
		ArrayList<Double> list = budgetMap.get(category);
		switch (category) {
		case "housing":
			household.setHousingBudget(newAmount);
			list.set(0, household.getHousingBudget());
			System.out.println("The new budget for housing is $" + household.getHousingBudget());
			break;
		case "utilities":
			household.setUtilitiesBudget(newAmount);
			list.set(0, household.getUtilitiesBudget());
			break;
		case "health":
			household.setHealthBudget(newAmount);
			list.set(0, household.getHealthBudget());
			break;
		case "car":
			household.setCarBudget(newAmount);
			list.set(0, household.getCarBudget());
			break;
		case "grocery":
			household.setGroceryBudget(newAmount);
			list.set(0, household.getGroceryBudget());
			break;
		case "dining":
			household.setDiningBudget(newAmount);
			list.set(0, household.getDiningBudget());
			break;
		case "fun":
			household.setFunBudget(newAmount);
			list.set(0, household.getFunBudget());
			break;
		case "miscellaneous":
			household.setMiscBudget(newAmount);
			list.set(0, household.getMiscBudget());
			break;
		}
	}

	public Formatter displayBudget(Household household) {
		Formatter table = new Formatter();
		Map<String, ArrayList<Double>> budgetMap = makeBudgetMap(household);
		table.format("%15s %15s %15s\n", "Category", "Budget", "Remaining");
		budgetMap.forEach((k, v) -> {
			table.format("%15s %15s %15s\n", k, v.get(0), v.get(0) - v.get(1));
		});
		System.out.println("\nTotal amount budgeted: ");
		System.out.println("Total amount remaining: ");
		System.out.println(table);
		return table;
	}

	public void mainMenuOptions(Household household) {
		System.out.println("Choose an option: \n1 - Create a New Budget\n2 - Check Your Budget\n3 - Add a Purchase");
		Scanner in = new Scanner(System.in);
		int userSelection = in.nextInt();
		switch (userSelection) {
		case 1:
			setUpBudget(household);
			Formatter budgetTable = displayBudget(household);
			budgetTable.close();
			break;
		case 2:
			System.out.println("Let's check your current budget!");
			displayBudget(household);
			break;
		case 3:
			addPurchaseMenu(household);
		}
		subMenuOptions(household);
	}

	public void subMenuOptions(Household household) {
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
				editBudget(household, "housing", amountInput);
				break;
			case "b":
				editBudget(household, "utilities", amountInput);
				break;
			case "c":
				editBudget(household, "health", amountInput);
				break;
			case "d":
				editBudget(household, "car", amountInput);
				break;
			case "e":
				editBudget(household, "groceries", amountInput);
				break;
			case "f":
				editBudget(household, "dining", amountInput);
				break;
			case "g":
				editBudget(household, "fun", amountInput);
				break;
			case "h":
				editBudget(household, "miscellaneous", amountInput);
				break;
			}
			break;
		case 2:
			System.out.println("Let's check your current budget!");
			Formatter budgetTable = displayBudget(household);
			budgetTable.close();
			break;
		case 3:
			addPurchaseMenu(household);
		case 4:
			purchasesSubMenuOptions(household);
		}
		subMenuOptions(household);
	}

	public void showAllPurchases(Household household) {
		for (Purchase purchase : household.getPurchasesList()) {
			System.out.println("\n\n-----------------------------------");
			System.out.println("Displaying all purchases...\n");
			System.out.println(purchase.getPurchasedBy().toUpperCase() + " spent " + "$" + purchase.getAmount() + " on "
					+ purchase.getCategory());
			System.out.println("\n\n-----------------------------------\n\n");
		}
	}

	public void showPurchasesByFamilyMember(Household household) {
		System.out.println("Enter family member name to view purchases:");
		Scanner in = new Scanner(System.in);
		String nameInput = in.next();
		System.out.println("\n\n-----------------------------------");
		for (FamilyMember familyMember : household.getHouseholdMembers()) {
			if (nameInput.toLowerCase().equals(familyMember.getName().toLowerCase())) {
				System.out.println("Displaying all purchases for " + familyMember.getName() + "\n");
				for (Purchase purchase : familyMember.getMemberPurchases()) {
					System.out.println("Date: " + purchase.getDatePurchased() + " Category: " + purchase.getCategory()
							+ " Amount: $" + purchase.getAmount());
				}
			}
		}
		System.out.println("\n\n-----------------------------------\n\n");

	}

	public void showPurchasesByDate(ArrayList<Purchase> purchasesList) {
		System.out.println(
				"How do you want to view purchases?\na - Purchases Today\nb - Purchases This Week\nc - Purchases This Month");
		Scanner in = new Scanner(System.in);
		String selection = in.next();
		LocalDate todaysDate = LocalDate.now();
		switch (selection) {
		case "a":
			System.out.println("You selected a - purchases today");
			ArrayList<Purchase> purchasesToday = viewPurchasesToday(purchasesList, todaysDate);
			System.out.println("generated purchasesToday array list");
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

	private void displayPurchases(ArrayList<Purchase> purchasesList) {
		System.out.println("inside displayPurchases method.");
		for (Purchase purchase : purchasesList) {
			System.out.println("Date: " + purchase.getDatePurchased() + " Category: " + purchase.getCategory()
					+ " Amount: $" + purchase.getAmount() + " Purchased By: " + purchase.getPurchasedBy());
		}

	}

	public ArrayList<Purchase> viewPurchasesToday(ArrayList<Purchase> purchasesList, LocalDate todaysDate) {
		ArrayList<Purchase> purchasesToday = new ArrayList<Purchase>();
		for (Purchase purchase : purchasesList) {
			if (todaysDate.compareTo(purchase.getDatePurchased()) == 0) {
				purchasesToday.add(purchase);
			}
		}
		return purchasesToday;

	}

	public ArrayList<Purchase> viewPurchasesThisWeek(ArrayList<Purchase> purchasesList, LocalDate todaysDate) {
		ArrayList<Purchase> purchasesThisWeek = new ArrayList<Purchase>();
		LocalDate weekRange = todaysDate.minusDays(7);
		System.out.println("the range is :" + weekRange);
		for (Purchase purchase : purchasesList) {
			if (purchase.getDatePurchased().compareTo(weekRange) > -1
					&& purchase.getDatePurchased().compareTo(todaysDate) < 1) {
				purchasesThisWeek.add(purchase);
			}
		}
		return purchasesThisWeek;

	}

	public ArrayList<Purchase> viewPurchasesThisMonth(ArrayList<Purchase> purchasesList, LocalDate todaysDate) {
		ArrayList<Purchase> purchasesThisMonth = new ArrayList<Purchase>();
		LocalDate monthRange = todaysDate.minusMonths(1);
		System.out.println("the range is :" + monthRange);
		for (Purchase purchase : purchasesList) {
			if (purchase.getDatePurchased().compareTo(monthRange) > -1
					&& purchase.getDatePurchased().compareTo(todaysDate) < 1) {
				purchasesThisMonth.add(purchase);
			}
		}

		return purchasesThisMonth;

	}

	public void purchasesSubMenuOptions(Household household) {
		System.out.println(
				"Choose an option:\na - Show All Purchases\nb - Show Purchases By Family Member\nc - Show Purchases By Date");
		Scanner in = new Scanner(System.in);
		String selection = in.next();
		switch (selection) {
		case "a":
			showAllPurchases(household);
			break;
		case "b":
			showPurchasesByFamilyMember(household);
			break;
		case "c":
			showPurchasesByDate(household.getPurchasesList());
			break;
		}
	}

	public static void main(String[] args) {
		Household household = new Household();
		Budget budget = new Budget();
		household.addFamilyMembers();
		budget.mainMenuOptions(household);
	}
}
