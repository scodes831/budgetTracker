package budgetTracker;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Budget {

	private int budgetMonth;
	private int budgetYear;
	
	Map<String, ArrayList<Double>> budgetMap = new LinkedHashMap<String, ArrayList<Double>>();
	
	Budget(int budgetMonth, int budgetYear) {
		this.budgetMonth = budgetMonth;
		this.budgetYear = budgetYear;
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
	
	public static int[] generateBudgetName() {
		Scanner in = new Scanner(System.in);
		int[] budgetName = new int[2];
		budgetName[0] = PromptUserInput.promptUserBudgetMonth(in);
		budgetName[1] = PromptUserInput.promptUserBudgetYear(in);
		return budgetName;
	}

	public void setUpBudget(Household household) {
		Scanner in = new Scanner(System.in);
		System.out.println("Let's create a new budget!\nYour total household income is $"
				+ household.calculateHouseholdIncome(household));
		household.setHousingBudget(PromptUserInput.promptUserHousingBudget(in));
		household.setUtilitiesBudget(PromptUserInput.promptUserUtilitiesBudget(in));
		household.setHealthBudget(PromptUserInput.promptUserHealthBudget(in));
		household.setCarBudget(PromptUserInput.promptUserCarBudget(in));
		household.setGroceryBudget(PromptUserInput.promptUserGroceryBudget(in));
		household.setDiningBudget(PromptUserInput.promptUserDiningBudget(in));
		household.setFunBudget(PromptUserInput.promptUserFunBudget(in));
		household.setMiscBudget(PromptUserInput.promptUserMiscBudget(in));
	}

	public void editBudget(Household household) {
		String category = PromptUserInput.promptUserCategoryInput(household);
		double newAmount = PromptUserInput.promptUserAmountInput(household);
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
		System.out.println("Total amount remaining: \n");
		System.out.println(table);
		return table;
	}
	
	public int getBudgetMonth() {
		return budgetMonth;
	}

	public void setBudgetMonth(int budgetMonth) {
		this.budgetMonth = budgetMonth;
	}

	public int getBudgetYear() {
		return budgetYear;
	}

	public void setBudgetYear(int budgetYear) {
		this.budgetYear = budgetYear;
	}

	public static void main(String[] args) {
		Menu.welcomeUser();
		Household household = new Household();
		int[] budgetName = generateBudgetName();
		Budget budget = new Budget(budgetName[0], budgetName[1]);
		household.addFamilyMembers();
		MainMenu mainMenu = new MainMenu();
		mainMenu.show(household, budget, mainMenu);
	}

	
}
