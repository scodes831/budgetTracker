package budgetTracker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
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

}
