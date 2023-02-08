package budgetTracker;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

public class DatabaseManager {

	public Connection connectDatabase(String database, String user, String pw) {
		Connection connection = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + database, user, pw);
			if (connection != null) {
				System.out.println("connected");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return connection;
	}
	
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

	public int getUserIdByUsername(Connection connection, String username) {
		Statement statement;
		ResultSet result = null;
		int id = 0;

		try {
			String query = String.format("select userid from users where username = '%s'", username);
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			while (result.next()) {
				id = Integer.valueOf(result.getString("userid"));
			}

		} catch (Exception e) {
			System.out.println(e);
		}

		return id;
	}

}
