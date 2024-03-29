package budgetTracker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

public class Budget {

	private int budgetMonth;
	private int budgetYear;

	private BigDecimal totalBudgeted;
	private BigDecimal totalSpent;
	private BigDecimal totalRemaining;

	private BigDecimal housingBudget;
	private BigDecimal housingSpend;
	private BigDecimal housingRemaining;
	private BigDecimal utilitiesBudget;
	private BigDecimal utilitiesSpend;
	private BigDecimal utilitiesRemaining;
	private BigDecimal healthBudget;
	private BigDecimal healthSpend;
	private BigDecimal healthRemaining;
	private BigDecimal carBudget;
	private BigDecimal carSpend;
	private BigDecimal carRemaining;
	private BigDecimal groceryBudget;
	private BigDecimal grocerySpend;
	private BigDecimal groceryRemaining;
	private BigDecimal diningBudget;
	private BigDecimal diningSpend;
	private BigDecimal diningRemaining;
	private BigDecimal funBudget;
	private BigDecimal funSpend;
	private BigDecimal funRemaining;
	private BigDecimal miscBudget;
	private BigDecimal miscSpend;
	private BigDecimal miscRemaining;
	
	final String[] budgetCategories = new String[] {"housing", "utilities", "health", "car", "grocery", "dining", "fun", "miscellaneous"};

	Map<String, ArrayList<BigDecimal>> budgetMap = new LinkedHashMap<String, ArrayList<BigDecimal>>();

	Budget(int budgetMonth, int budgetYear) {
		this.budgetMonth = budgetMonth;
		this.budgetYear = budgetYear;
	}

	private Map<String, ArrayList<BigDecimal>> makeBudgetMap(Household household, Budget budget) {
		household.calculateCategorySpend(budget);
		budgetMap.put("housing",
				new ArrayList<>(Arrays.asList(getHousingBudget(), getHousingSpend(), getHousingRemaining())));
		budgetMap.put("utilities",
				new ArrayList<>(Arrays.asList(getUtilitiesBudget(), getUtilitiesSpend(), getUtilitiesRemaining())));
		budgetMap.put("health",
				new ArrayList<>(Arrays.asList(getHealthBudget(), getHealthSpend(), getHealthRemaining())));
		budgetMap.put("car", new ArrayList<>(Arrays.asList(getCarBudget(), getCarSpend(), getCarRemaining())));
		budgetMap.put("grocery",
				new ArrayList<>(Arrays.asList(getGroceryBudget(), getGrocerySpend(), getGroceryRemaining())));
		budgetMap.put("dining",
				new ArrayList<>(Arrays.asList(getDiningBudget(), getDiningSpend(), getDiningRemaining())));
		budgetMap.put("fun", new ArrayList<>(Arrays.asList(getFunBudget(), getFunSpend(), getFunRemaining())));
		budgetMap.put("miscellaneous",
				new ArrayList<>(Arrays.asList(getMiscBudget(), getMiscSpend(), getMiscRemaining())));
		calculateTotalBudgeted(budgetMap);
		calculateTotalSpent(household, budget);
		calculateTotalRemaining(getTotalBudgeted(), getTotalSpent());
		return budgetMap;
	}

	public static Budget initializeBudget(Household household, int[] budgetName) {
		int isDuplicate = isBudgetADuplicate(household, budgetName);
		if (isDuplicate == -1) {
			Budget budget = new Budget(budgetName[0], budgetName[1]);
			household.getBudgets().add(budget);
			return budget;
		} else {
			System.out.println("A budget already exists for "
					+ budgetMonthString(household.getBudgets().get(isDuplicate)) + " " + budgetName[1] + ".");
		}
		return null;
	}

	public void setUpBudget(Household household, Budget budget, Connection connection,
			BudgetActualTable budgetActualTable) {
		BigDecimal runningTotal = new BigDecimal(0);
		String[] categories = { "Housing", "Utilities", "Health", "Car", "Grocery", "Dining", "Fun", "Miscellaneous" };
		System.out.println("Let's set up your budget for " + budgetMonthString(budget) + " " + budget.getBudgetYear()
				+ "!\nYour total household income is $" + household.getIncome());
		Scanner in = new Scanner(System.in);
		for (int i = 0; i < categories.length; i++) {
			boolean hasError;
			do {
				BigDecimal currValue = new BigDecimal(PromptUserInput.promptUserBudgetAmount(in, categories[i]));
				runningTotal = runningTotal.add(currValue);
				if (runningTotal.compareTo(new BigDecimal(household.getIncome())) < 0) {
					hasError = false;
					setBudgetAmount(categories[i], currValue);
					budgetActualTable.insertBudgetRow(connection, LocalDate.of(budgetYear, budgetMonth, 1),
							categories[i], currValue, new BigDecimal(0), currValue);
					System.out.println("Income remaining: $"
							+ new BigDecimal(household.getIncome()).subtract(runningTotal) + "\n");
				} else {
					hasError = true;
					runningTotal = runningTotal.subtract(currValue);
					System.out.println("ERROR - Budgeted amount exceeds household income. Enter a valid amount:\n");
				}
			} while (hasError);
		}
	}

	private void setBudgetAmount(String category, BigDecimal amount) {
		switch (category.toLowerCase()) {
		case "housing":
			setHousingBudget(amount);
			break;
		case "utilities":
			setUtilitiesBudget(amount);
			break;
		case "health":
			setHealthBudget(amount);
			break;
		case "car":
			setCarBudget(amount);
			break;
		case "grocery":
			setGroceryBudget(amount);
			break;
		case "dining":
			setDiningBudget(amount);
			break;
		case "fun":
			setFunBudget(amount);
			break;
		case "miscellanous":
			setMiscBudget(amount);
			break;
		}
	}

	public boolean isAllIncomeBudgeted(Household household, Budget budget) {
		return false;
	}

	public void editBudget(Household household, Budget budget, Connection connection,
			BudgetActualTable budgetActualTable) {
		String category = PromptUserInput.promptUserCategoryInput(household);
		BigDecimal newAmount = PromptUserInput.promptUserAmountInput(household);
		ArrayList<BigDecimal> list = budgetMap.get(category);
		switch (category) {
		case "housing":
			setHousingBudget(newAmount);
			list.set(0, getHousingBudget());
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
		int rowId = DatabaseManager.getBudgetRowIdByBudget(connection, LocalDate.of(budgetYear, budgetMonth, 1),
				StringUtils.capitalize(category));
		budgetActualTable.updateBudget(connection, rowId, LocalDate.of(budgetYear, budgetMonth, 1),
				StringUtils.capitalize(category), newAmount.setScale(2, RoundingMode.HALF_UP), new BigDecimal(0),
				new BigDecimal(0));

	}

	public static int isBudgetADuplicate(Household household, int[] budgetName) {
		for (int i = 0; i < household.getBudgets().size(); i++) {
			if (household.getBudgets().get(i).getBudgetMonth() == budgetName[0]
					&& household.getBudgets().get(i).getBudgetYear() == budgetName[1]) {
				return i;
			}
		}
		return -1;
	}

	public static Budget selectABudget(Household household, String message) {
		int budgetIndex = PromptUserInput.promptUserBudgetSelection(household, message);
		Budget selectedBudget = household.getBudgets().get(budgetIndex);
		return selectedBudget;
	}

	public Formatter displayBudget(Household household, Budget selectedBudget) {
		Formatter table = new Formatter();
		Map<String, ArrayList<BigDecimal>> budgetMap = makeBudgetMap(household, selectedBudget);
		table.format("%15s %15s %15s %15s\n", "Category", "Budget", "Actual", "Remaining");
		budgetMap.forEach((k, v) -> {
			table.format("%15s %15s %15s %15s\n", k, v.get(0), v.get(1), v.get(0).subtract(v.get(1)));
		});

		System.out.println("\nTotal amount budgeted: $" + getTotalBudgeted());
		System.out.println("Total amount spent: $" + getTotalSpent());
		System.out.println("Total amount remaining: $" + getTotalRemaining() + "\n");
		System.out.println(table);
		return table;
	}

	private void calculateTotalBudgeted(Map<String, ArrayList<BigDecimal>> budgetMap) {
		BigDecimal totalBudget = new BigDecimal(0);
		for (Map.Entry<String, ArrayList<BigDecimal>> entry : budgetMap.entrySet()) {
			totalBudget = totalBudget.add(entry.getValue().get(0));
		}
		setTotalBudgeted(totalBudget);
	}

	private void calculateTotalSpent(Household household, Budget budget) {
		BigDecimal totalSpend = new BigDecimal(0);
		int budgetMonth = budget.getBudgetMonth();
		int budgetYear = budget.getBudgetYear();
		for (Purchase purchase : household.getPurchasesList()) {
			LocalDate datePurchased = purchase.getDatePurchased();
			int monthPurchased = datePurchased.getMonthValue();
			int yearPurchased = datePurchased.getYear();
			if (budgetMonth == monthPurchased && budgetYear == yearPurchased) {
				totalSpend = totalSpend.add(purchase.getAmount());
			}
		}
		setTotalSpent(totalSpend);
	}

	private void calculateTotalRemaining(BigDecimal totalBudget, BigDecimal totalSpend) {
		setTotalRemaining(totalBudget.subtract(totalSpend));
	}

	public BigDecimal updateCategorySpendAmount(Household household, String category, Budget budget) {
		BigDecimal categorySpend = new BigDecimal(0);
		for (Purchase purchase : household.getPurchasesList()) {
			if (purchase.getDatePurchased().getYear() == budget.getBudgetYear()
					&& purchase.getDatePurchased().getMonthValue() == budget.getBudgetMonth()
					&& purchase.getCategory().toLowerCase().equals(category.toLowerCase())) {
				categorySpend = categorySpend.add(purchase.getAmount());
			}
		}
		switch (category.toLowerCase()) {
		case "housing":
			setHousingSpend(categorySpend);
			break;
		case "utilities":
			setUtilitiesSpend(categorySpend);
			break;
		case "health":
			setHealthSpend(categorySpend);
			break;
		case "car":
			setCarSpend(categorySpend);
			break;
		case "grocery":
			setGrocerySpend(categorySpend);
			break;
		case "dining":
			setDiningSpend(categorySpend);
			break;
		case "fun":
			setFunSpend(categorySpend);
			break;
		case "miscellanous":
			setMiscSpend(categorySpend);
			break;
		}
		return categorySpend;
	}

	public BigDecimal updateCategoryRemainingAmount(String category) {
		BigDecimal categoryRemaining = new BigDecimal(0);
		switch (category.toLowerCase()) {
		case "housing":
			categoryRemaining = getHousingBudget().subtract(getHousingSpend());
			setHousingRemaining(categoryRemaining);
			break;
		case "utilities":
			categoryRemaining = getUtilitiesBudget().subtract(getUtilitiesSpend());
			setUtilitiesRemaining(categoryRemaining);
			break;
		case "health":
			categoryRemaining = getHealthBudget().subtract(getHealthSpend());
			setHealthRemaining(categoryRemaining);
			break;
		case "car":
			categoryRemaining = getCarBudget().subtract(getCarSpend());
			setCarRemaining(categoryRemaining);
			break;
		case "grocery":
			categoryRemaining = getGroceryBudget().subtract(getGrocerySpend());
			setGroceryRemaining(categoryRemaining);
			break;
		case "dining":
			categoryRemaining = getDiningBudget().subtract(getDiningSpend());
			setDiningRemaining(categoryRemaining);
			break;
		case "fun":
			categoryRemaining = getFunBudget().subtract(getFunSpend());
			setFunRemaining(categoryRemaining);
			break;
		case "miscellanous":
			categoryRemaining = getMiscBudget().subtract(getMiscSpend());
			setMiscRemaining(categoryRemaining);
			break;
		}
		return categoryRemaining;
	}
	
	public BigDecimal getCategoryBudgetAmount(String category) {
		switch (category.toLowerCase()) {
		case "housing":
			return getHousingBudget();
		case "utilities":
			return getUtilitiesBudget();
		case "health":
			return getHealthBudget();
		case "car":
			return getCarBudget();
		case "grocery":
			return getGroceryBudget();
		case "dining":
			return getDiningBudget();
		case "fun":
			return getFunBudget();
		case "miscellaneous":
			return getMiscBudget();
		}
		return null;
	}

	public static String budgetMonthString(Budget budget) {
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

	public BigDecimal getTotalBudgeted() {
		return totalBudgeted;
	}

	public void setTotalBudgeted(BigDecimal totalBudgeted) {
		this.totalBudgeted = totalBudgeted;
	}

	public BigDecimal getTotalSpent() {
		return totalSpent;
	}

	public void setTotalSpent(BigDecimal totalSpent) {
		this.totalSpent = totalSpent;
	}

	public BigDecimal getHousingBudget() {
		return housingBudget;
	}

	public void setHousingBudget(BigDecimal amount) {
		this.housingBudget = amount;
	}

	public BigDecimal getHousingSpend() {
		return housingSpend;
	}

	public void setHousingSpend(BigDecimal housingSpend) {
		this.housingSpend = housingSpend;
	}

	public BigDecimal getUtilitiesBudget() {
		return utilitiesBudget;
	}

	public void setUtilitiesBudget(BigDecimal utilitiesBudget) {
		this.utilitiesBudget = utilitiesBudget;
	}

	public BigDecimal getUtilitiesSpend() {
		return utilitiesSpend;
	}

	public void setUtilitiesSpend(BigDecimal utilitiesSpend) {
		this.utilitiesSpend = utilitiesSpend;
	}

	public BigDecimal getHealthBudget() {
		return healthBudget;
	}

	public void setHealthBudget(BigDecimal healthBudget) {
		this.healthBudget = healthBudget;
	}

	public BigDecimal getHealthSpend() {
		return healthSpend;
	}

	public void setHealthSpend(BigDecimal healthSpend) {
		this.healthSpend = healthSpend;
	}

	public BigDecimal getCarBudget() {
		return carBudget;
	}

	public void setCarBudget(BigDecimal carBudget) {
		this.carBudget = carBudget;
	}

	public BigDecimal getCarSpend() {
		return carSpend;
	}

	public void setCarSpend(BigDecimal carSpend) {
		this.carSpend = carSpend;
	}

	public BigDecimal getGroceryBudget() {
		return groceryBudget;
	}

	public void setGroceryBudget(BigDecimal groceryBudget) {
		this.groceryBudget = groceryBudget;
	}

	public BigDecimal getGrocerySpend() {
		return grocerySpend;
	}

	public void setGrocerySpend(BigDecimal grocerySpend) {
		this.grocerySpend = grocerySpend;
	}

	public BigDecimal getDiningBudget() {
		return diningBudget;
	}

	public void setDiningBudget(BigDecimal diningBudget) {
		this.diningBudget = diningBudget;
	}

	public BigDecimal getDiningSpend() {
		return diningSpend;
	}

	public void setDiningSpend(BigDecimal diningSpend) {
		this.diningSpend = diningSpend;
	}

	public BigDecimal getFunBudget() {
		return funBudget;
	}

	public void setFunBudget(BigDecimal funBudget) {
		this.funBudget = funBudget;
	}

	public BigDecimal getFunSpend() {
		return funSpend;
	}

	public void setFunSpend(BigDecimal funSpend) {
		this.funSpend = funSpend;
	}

	public BigDecimal getMiscBudget() {
		return miscBudget;
	}

	public void setMiscBudget(BigDecimal miscBudget) {
		this.miscBudget = miscBudget;
	}

	public BigDecimal getMiscSpend() {
		return miscSpend;
	}

	public void setMiscSpend(BigDecimal miscSpend) {
		this.miscSpend = miscSpend;
	}

	public BigDecimal getHousingRemaining() {
		return housingRemaining;
	}

	public void setHousingRemaining(BigDecimal housingRemaining) {
		this.housingRemaining = housingRemaining;
	}

	public BigDecimal getUtilitiesRemaining() {
		return utilitiesRemaining;
	}

	public void setUtilitiesRemaining(BigDecimal utilitiesRemaining) {
		this.utilitiesRemaining = utilitiesRemaining;
	}

	public BigDecimal getHealthRemaining() {
		return healthRemaining;
	}

	public void setHealthRemaining(BigDecimal healthRemaining) {
		this.healthRemaining = healthRemaining;
	}

	public BigDecimal getCarRemaining() {
		return carRemaining;
	}

	public void setCarRemaining(BigDecimal carRemaining) {
		this.carRemaining = carRemaining;
	}

	public BigDecimal getGroceryRemaining() {
		return groceryRemaining;
	}

	public void setGroceryRemaining(BigDecimal groceryRemaining) {
		this.groceryRemaining = groceryRemaining;
	}

	public BigDecimal getDiningRemaining() {
		return diningRemaining;
	}

	public void setDiningRemaining(BigDecimal diningRemaining) {
		this.diningRemaining = diningRemaining;
	}

	public BigDecimal getFunRemaining() {
		return funRemaining;
	}

	public void setFunRemaining(BigDecimal funRemaining) {
		this.funRemaining = funRemaining;
	}

	public BigDecimal getMiscRemaining() {
		return miscRemaining;
	}

	public void setMiscRemaining(BigDecimal miscRemaining) {
		this.miscRemaining = miscRemaining;
	}

	public BigDecimal getTotalRemaining() {
		return totalRemaining;
	}

	public void setTotalRemaining(BigDecimal totalRemaining) {
		this.totalRemaining = totalRemaining;
	}

	public String[] getBudgetCategories() {
		return budgetCategories;
	}
}
