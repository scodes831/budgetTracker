package budgetTracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.LinkedHashMap;
import java.util.List;
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
		System.out.println("Who made the purchase?");
		String purchasedBy = in.next();
		System.out.println("Enter purchase amount:");
		double amount = in.nextDouble();
		System.out.println(
				"What category was your purchase?\na - Housing\nb - Utilities\nc - Health\nd - Car\ne - Groceries\nf - Dining\ng - Fun\nh - Miscellaneous");
		String categorySel = in.next();
		switch (categorySel) {
		case "a":
			household.addPurchase("housing", amount, purchasedBy);
			System.out.println("Your purchase has been added: " + amount + " for housing.");
			break;
		case "b":
			household.addPurchase("utilities", amount, purchasedBy);
			break;
		case "c":
			household.addPurchase("health", amount, purchasedBy);
			break;
		case "d":
			household.addPurchase("car", amount, purchasedBy);
			break;
		case "e":
			household.addPurchase("grocery", amount, purchasedBy);
			break;
		case "f":
			household.addPurchase("dining", amount, purchasedBy);
			break;
		case "g":
			household.addPurchase("fun", amount, purchasedBy);
			break;
		case "h":
			household.addPurchase("miscellaneous", amount, purchasedBy);
			break;
		default:
			System.out.println("Please enter a valid option a-h");
		}
	}

	public static void setUpBudget(Household household) {
		Scanner in = new Scanner(System.in);

		System.out.println("Let's create a new budget!");
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
		System.out.println("Choose an option: \n1 - Edit Budget\n2 - Display Budget\n3 - Add a Purchase\n4 - Show Purchases By Family Member");
		Scanner in = new Scanner(System.in);
		int userSelection = in.nextInt();
		switch (userSelection) {
		case 1:
			System.out.println("Let's edit your budget!");
			System.out.println("Select a category to edit:\na - Housing\nb - Utilities\nc - Health\nd - Car\ne - Groceries\nf - Dining\ng - Fun\nh - Miscellaneous");
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
			showPurchasesByMember();
		}
		subMenuOptions(household);
	}
	
	public void showPurchasesByMember() {
			for (Purchase purchase : Household.getPurchasesList()) {
				System.out.println(purchase.getPurchasedBy().toUpperCase() + " spent " + "$" + purchase.getAmount() + " on " + purchase.getCategory());
			}
	}

	public static void main(String[] args) {
		Household household = new Household();
		Budget budget = new Budget();
		household.addFamilyMembers();
		budget.mainMenuOptions(household);
	}
}
