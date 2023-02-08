package budgetTracker;

import java.math.BigDecimal;
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
			String query = String.format("insert into budgetvsactual (budgetname, category, budgetamount, spendamount, remainingamount) values ('%s','%s','%s','%s','%s');", budgetName, category, budgetAmount, spendAmount, remainingAmount);
			statement = connection.createStatement();
			statement.executeUpdate(query);
			System.out.println("budget row inserted");
		} catch (Exception e) {
			System.out.println(e);
		}

	}


}
