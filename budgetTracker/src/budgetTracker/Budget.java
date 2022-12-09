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
	
	Budget(Household household) {
		this.budgetMap = makeBudgetMap(household);
	}

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
		System.out.println("Add your purchase...\nEnter purchase amount:");
		double amount = in.nextDouble();
		System.out.println(
				"What category was your purchase?\na - Housing\nb - Utilities\nc - Health\nd - Car\ne - Groceries\nf - Dining\ng - Fun\nh - Miscellaneous");
		String categorySel = in.next();
		switch (categorySel) {
		case "a":
			household.addPurchase("housing", amount);
			System.out.println("Your purchase has been added: " + amount + " for housing.");
			break;
		case "b":
			household.addPurchase("utilities", amount);
			break;
		case "c":
			household.addPurchase("health", amount);
			break;
		case "d":
			household.addPurchase("car", amount);
			break;
		case "e":
			household.addPurchase("groceries", amount);
			break;
		case "f":
			household.addPurchase("dining", amount);
			break;
		case "g":
			household.addPurchase("fun", amount);
			break;
		case "h":
			household.addPurchase("miscellaneous", amount);
			break;
		default:
			System.out.println("Please enter a valid option a-h");
		}
	}

	public static void setUpBudget(Household household) {
		Scanner in = new Scanner(System.in);

		System.out.println("Let's create a new budget!");
		Budget budget = new Budget(household);
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
		switch (category) {
		case "housing":
			household.setHousingBudget(newAmount);
			break;
		case "utilities":
			household.setUtilitiesBudget(newAmount);
			break;
		case "health":
			household.setHealthBudget(newAmount);
			break;
		case "car":
			household.setCarBudget(newAmount);
			break;
		case "grocery":
			household.setGroceryBudget(newAmount);
			break;
		case "dining":
			household.setDiningBudget(newAmount);
			break;
		case "fun":
			household.setFunBudget(newAmount);
			break;
		case "miscellaneous":
			household.setMiscBudget(newAmount);
			break;
		}
	
	}

	public void displayBudget(Household household) {
		Formatter table = new Formatter();
		table.format("%15s %15s %15s\n", "Category", "Budget", "Remaining");
		budgetMap.forEach((k, v) -> {
			table.format("%15s %15s %15s\n", k, v.get(0), v.get(0) - v.get(1));
		});

		System.out.println(table);

	}

	public static void main(String[] args) {
		Household household = new Household();
		Budget budget = new Budget(household);
		budget.makeBudgetMap(household);
		household.addFamilyMembers();

		System.out.println("Choose an option: \n1 - Create a New Budget\n2 - Check Your Budget\n3 - Add a Purchase");
		Scanner in = new Scanner(System.in);
		int userSelection = in.nextInt();
		switch (userSelection) {
		case 1:
			setUpBudget(household);
			budget.displayBudget(household);
			break;
		case 2:
			System.out.println("Let's check your current budget!");
			budget.displayBudget(household);
			break;
		case 3:
			
		}
		// check budget
		// display all categories with budget amount and remaining amount
		// provide option to add a purchase
		// add a purchase
		// ask user for purchase date, category, and amount
		// ask user if they want to add another purchase
		// if yes, repeat
		// if no, display all categories with budget amount and remaining amount
	}
}
