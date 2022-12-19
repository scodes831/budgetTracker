package budgetTracker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Budget {

	private int budgetMonth;
	private int budgetYear;
	
	private double totalBudgeted;
	private double totalSpent;
	
	private double housingBudget;
	private double housingSpend;
	private double utilitiesBudget;
	private double utilitiesSpend;
	private double healthBudget;
	private double healthSpend;
	private double carBudget;
	private double carSpend;
	private double groceryBudget;
	private double grocerySpend;
	private double diningBudget;
	private double diningSpend;
	private double funBudget;
	private double funSpend;
	private double miscBudget;
	private double miscSpend;
	
	Map<String, ArrayList<Double>> budgetMap = new LinkedHashMap<String, ArrayList<Double>>();
	
	Budget(int budgetMonth, int budgetYear) {
		this.budgetMonth = budgetMonth;
		this.budgetYear = budgetYear;
	}

	private Map<String, ArrayList<Double>> makeBudgetMap(Household household, Budget budget) {
		household.calculateCategorySpend(budget);
		budgetMap.put("housing",
				new ArrayList<>(Arrays.asList(getHousingBudget(), getHousingSpend())));
		budgetMap.put("utilities",
				new ArrayList<>(Arrays.asList(getUtilitiesBudget(), getUtilitiesSpend())));
		budgetMap.put("health",
				new ArrayList<>(Arrays.asList(getHealthBudget(), getHealthSpend())));
		budgetMap.put("car", new ArrayList<>(Arrays.asList(getCarBudget(), getCarSpend())));
		budgetMap.put("groceries",
				new ArrayList<>(Arrays.asList(getGroceryBudget(), getGrocerySpend())));
		budgetMap.put("dining",
				new ArrayList<>(Arrays.asList(getDiningBudget(), getDiningSpend())));
		budgetMap.put("fun", new ArrayList<>(Arrays.asList(getFunBudget(), getFunSpend())));
		budgetMap.put("miscellaneous",
				new ArrayList<>(Arrays.asList(getMiscBudget(), getMiscSpend())));
		return budgetMap;
	}
	
	public static Budget initializeBudget(Household household, int[] budgetName) {
		Budget budget = new Budget(budgetName[0], budgetName[1]);
		household.getBudgets().add(budget);
		return budget;
	}

	public void setUpBudget(Household household, Budget budget) {
		Scanner in = new Scanner(System.in);
		System.out.println("Let's set up your budget for " + budget.budgetMonthString(budget) + " " + budget.getBudgetYear() + "!\nYour total household income is $"
				+ household.calculateHouseholdIncome(household));
		setHousingBudget(PromptUserInput.promptUserHousingBudget(in));
		setUtilitiesBudget(PromptUserInput.promptUserUtilitiesBudget(in));
		setHealthBudget(PromptUserInput.promptUserHealthBudget(in));
		setCarBudget(PromptUserInput.promptUserCarBudget(in));
		setGroceryBudget(PromptUserInput.promptUserGroceryBudget(in));
		setDiningBudget(PromptUserInput.promptUserDiningBudget(in));
		setFunBudget(PromptUserInput.promptUserFunBudget(in));
		setMiscBudget(PromptUserInput.promptUserMiscBudget(in));
	}

	public void editBudget(Household household, Budget budget) {
		String category = PromptUserInput.promptUserCategoryInput(household);
		double newAmount = PromptUserInput.promptUserAmountInput(household);
		ArrayList<Double> list = budgetMap.get(category);
		switch (category) {
		case "housing":
			setHousingBudget(newAmount);
			list.set(0, getHousingBudget());
			System.out.println("The new budget for housing is $" + getHousingBudget());
			break;
		case "utilities":
			setUtilitiesBudget(newAmount);
			list.set(0, getUtilitiesBudget());
			break;
		case "health":
			setHealthBudget(newAmount);
			list.set(0, getHealthBudget());
			break;
		case "car":
			setCarBudget(newAmount);
			list.set(0, getCarBudget());
			break;
		case "grocery":
			setGroceryBudget(newAmount);
			list.set(0, getGroceryBudget());
			break;
		case "dining":
			setDiningBudget(newAmount);
			list.set(0, getDiningBudget());
			break;
		case "fun":
			setFunBudget(newAmount);
			list.set(0, getFunBudget());
			break;
		case "miscellaneous":
			setMiscBudget(newAmount);
			list.set(0, getMiscBudget());
			break;
		}
	}
	
	public Budget selectABudget(Household household) {
		int budgetCount = 1;
		for (Budget budget : household.getBudgets()) {
			System.out.println(budgetCount + ": " + budget.budgetMonthString(budget)+ budget.getBudgetYear());
			budgetCount++;
		}
		System.out.println("Enter the line number of the budget:");
		Scanner in = new Scanner(System.in);
		Budget selectedBudget = household.getBudgets().get(household.getBudgets().size()-1);
		return selectedBudget;
	}

	public Formatter displayBudget(Household household, Budget budget) {
		Formatter table = new Formatter();
		Map<String, ArrayList<Double>> budgetMap = makeBudgetMap(household, budget);
		table.format("%15s %15s %15s\n", "Category", "Budget", "Remaining");
		budgetMap.forEach((k, v) -> {
			table.format("%15s %15s %15s\n", k, v.get(0), v.get(0) - v.get(1));
		});
		double totalBudget = calculateTotalBudget(budgetMap);
		double totalSpend = calculateTotalSpend(household, budget);
		System.out.println("\nTotal amount budgeted: $" + totalBudget);
		System.out.println("Total amount spent: $" + totalSpend);
		System.out.println("Total amount remaining: $" + calculateTotalRemaining(totalBudget, totalSpend) + "\n");
		System.out.println(table);
		return table;
	}
	
	private double calculateTotalBudget(Map<String, ArrayList<Double>> budgetMap) {
		double totalBudget = 0.0;
		for (Map.Entry<String, ArrayList<Double>> entry : budgetMap.entrySet()) {
			totalBudget += entry.getValue().get(0);
		}
		return totalBudget;
	}
	
	private double calculateTotalSpend(Household household, Budget budget) {
		double totalSpend = 0.0;
		int budgetMonth = budget.getBudgetMonth();
		int budgetYear = budget.getBudgetYear();
		for (Purchase purchase : household.getPurchasesList()) {
			LocalDate datePurchased = purchase.getDatePurchased();
			int monthPurchased = datePurchased.getMonthValue();
			int yearPurchased = datePurchased.getYear();
			if (budgetMonth == monthPurchased && budgetYear == yearPurchased) {
				totalSpend += purchase.getAmount();
			}
		}
		return totalSpend;
		
	}
	
	private double calculateTotalRemaining(double totalBudget, double totalSpend) {
		return totalBudget - totalSpend;
	}
	
	public String budgetMonthString(Budget budget) {
		int monthInt = budget.getBudgetMonth();
		switch (monthInt) {
		case 1:
			return "January";
		case 2:
			return "February";
		case 3:
			return "March";
		case 4: 
			return "April";
		case 5:
			return "May";
		case 6:
			return "June";
		case 7:
			return "July";
		case 8:
			return "August";
		case 9: 
			return "September";
		case 10: 
			return "October";
		case 11:
			return "November";
		case 12:
			return "December";
		}
		return null;
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

	public double getTotalBudgeted() {
		return totalBudgeted;
	}

	public void setTotalBudgeted(double totalBudgeted) {
		this.totalBudgeted = totalBudgeted;
	}

	public double getTotalSpent() {
		return totalSpent;
	}

	public void setTotalSpent(double totalSpent) {
		this.totalSpent = totalSpent;
	}

	public double getHousingBudget() {
		return housingBudget;
	}

	public void setHousingBudget(double housingBudget) {
		this.housingBudget = housingBudget;
	}

	public double getHousingSpend() {
		return housingSpend;
	}

	public void setHousingSpend(double housingSpend) {
		this.housingSpend = housingSpend;
	}

	public double getUtilitiesBudget() {
		return utilitiesBudget;
	}

	public void setUtilitiesBudget(double utilitiesBudget) {
		this.utilitiesBudget = utilitiesBudget;
	}

	public double getUtilitiesSpend() {
		return utilitiesSpend;
	}

	public void setUtilitiesSpend(double utilitiesSpend) {
		this.utilitiesSpend = utilitiesSpend;
	}

	public double getHealthBudget() {
		return healthBudget;
	}

	public void setHealthBudget(double healthBudget) {
		this.healthBudget = healthBudget;
	}

	public double getHealthSpend() {
		return healthSpend;
	}

	public void setHealthSpend(double healthSpend) {
		this.healthSpend = healthSpend;
	}

	public double getCarBudget() {
		return carBudget;
	}

	public void setCarBudget(double carBudget) {
		this.carBudget = carBudget;
	}

	public double getCarSpend() {
		return carSpend;
	}

	public void setCarSpend(double carSpend) {
		this.carSpend = carSpend;
	}

	public double getGroceryBudget() {
		return groceryBudget;
	}

	public void setGroceryBudget(double groceryBudget) {
		this.groceryBudget = groceryBudget;
	}

	public double getGrocerySpend() {
		return grocerySpend;
	}

	public void setGrocerySpend(double grocerySpend) {
		this.grocerySpend = grocerySpend;
	}

	public double getDiningBudget() {
		return diningBudget;
	}

	public void setDiningBudget(double diningBudget) {
		this.diningBudget = diningBudget;
	}

	public double getDiningSpend() {
		return diningSpend;
	}

	public void setDiningSpend(double diningSpend) {
		this.diningSpend = diningSpend;
	}

	public double getFunBudget() {
		return funBudget;
	}

	public void setFunBudget(double funBudget) {
		this.funBudget = funBudget;
	}

	public double getFunSpend() {
		return funSpend;
	}

	public void setFunSpend(double funSpend) {
		this.funSpend = funSpend;
	}

	public double getMiscBudget() {
		return miscBudget;
	}

	public void setMiscBudget(double miscBudget) {
		this.miscBudget = miscBudget;
	}

	public double getMiscSpend() {
		return miscSpend;
	}

	public void setMiscSpend(double miscSpend) {
		this.miscSpend = miscSpend;
	}
	
}
