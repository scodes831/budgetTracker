package budgetTracker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

public class BudgetActualTable {

	public void createBudgetVsActualTable(Connection connection) {
		Statement statement;
		try {
			String query = "CREATE TABLE IF NOT EXISTS budgetVsActual (rowId SERIAL PRIMARY KEY, budgetName DATE, "
					+ "category VARCHAR(20), budgetAmount NUMERIC(10,2), "
					+ "spendAmount NUMERIC (10,2), remainingAmount NUMERIC(10,2))";
			statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void insertBudgetRow(Connection connection, LocalDate budgetName, String category, BigDecimal budgetAmount,
			BigDecimal spendAmount, BigDecimal remainingAmount) {
		Statement statement;
		try {
			String query = String.format(
					"insert into budgetvsactual (budgetname, category, budgetamount, spendamount, remainingamount) values ('%s','%s','%s','%s','%s');",
					budgetName, category, budgetAmount, spendAmount, remainingAmount);
			statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void updateBudget(Connection connection, int rowId, LocalDate newBudgetName, String category,
			BigDecimal newBudgetAmount, BigDecimal newSpendAmount, BigDecimal newRemainingAmount) {
		Statement statement;
		try {
			String query = String.format(
					"update budgetvsactual set budgetname = '%s', category = '%s', budgetamount = '%s', spendamount = '%s', remainingamount = '%s' where rowid = '%s';",
					newBudgetName, category, newBudgetAmount.setScale(2, RoundingMode.HALF_UP),
					newSpendAmount.setScale(2, RoundingMode.HALF_UP),
					newRemainingAmount.setScale(2, RoundingMode.HALF_UP), rowId);
			statement = connection.createStatement();
			statement.executeUpdate(query);

		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void readMonthlyBudget(Connection connection, Household household, LocalDate budgetName) {
		Statement statement;
		ResultSet result = null;
		try {
			String query = String.format("select * from budgetvsactual where budgetname = '%s';", budgetName);
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			while (result.next()) {
				int rowId = Integer.valueOf(result.getString("rowid"));
				String[] date = result.getString("budgetname").split("-");
				int[] name = {Integer.valueOf(date[0]), Integer.valueOf(date[1])};
				Budget currentBudget = Budget.initializeBudget(household, name);
				household.getBudgets().add(currentBudget);
				double budgetAmount = Double.valueOf(result.getString("budgetamount"));
				double spendAmount = Double.valueOf(result.getString("spendamount"));
				double remainingAmount = Double.valueOf(result.getString("remainingamount"));
				String category = result.getString("category");
				switch (category.toLowerCase()) {
				case "housing":
					currentBudget.setHousingBudget(budgetAmount);
					currentBudget.setHousingSpend(spendAmount);
					currentBudget.setHousingRemaining(remainingAmount);
					break;
				case "utilities":
					currentBudget.setUtilitiesBudget(budgetAmount);
					currentBudget.setUtilitiesSpend(spendAmount);
					currentBudget.setUtilitiesRemaining(remainingAmount);
					break;
				case "health":
					currentBudget.setHealthBudget(budgetAmount);
					currentBudget.setHealthSpend(spendAmount);
					currentBudget.setHealthRemaining(remainingAmount);
				case "car":
					currentBudget.setCarBudget(budgetAmount);
					currentBudget.setCarSpend(spendAmount);
					currentBudget.setCarRemaining(remainingAmount);
					break;
				case "grocery":
					currentBudget.setGroceryBudget(budgetAmount);
					currentBudget.setGrocerySpend(spendAmount);
					currentBudget.setGroceryRemaining(remainingAmount);
					break;
				case "dining":
					currentBudget.setDiningBudget(budgetAmount);
					currentBudget.setDiningSpend(spendAmount);
					currentBudget.setDiningRemaining(remainingAmount);
					break;
				case "fun":
					currentBudget.setFunBudget(budgetAmount);
					currentBudget.setFunSpend(spendAmount);
					currentBudget.setFunRemaining(remainingAmount);
					break;
				case "miscellaneous":
					currentBudget.setMiscBudget(budgetAmount);
					currentBudget.setMiscSpend(spendAmount);
					currentBudget.setMiscRemaining(remainingAmount);
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
