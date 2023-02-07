package budgetTracker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

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

	public void createUsersTable(Connection connection) {
		Statement statement;
		try {
			String query = "CREATE TABLE IF NOT EXISTS users (userId SERIAL PRIMARY KEY, userName VARCHAR(30))";
			statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void createPurchasesTable(Connection connection) {
		Statement statement;
		try {
			String query = "CREATE TABLE IF NOT EXISTS purchases (purchaseId SERIAL PRIMARY KEY, "
					+ "purchaseDate DATE, category VARCHAR(20), "
					+ "purchasedBy INTEGER, purchaseAmount NUMERIC(10,2), "
					+ "FOREIGN KEY (purchasedBy) REFERENCES users(userId))";
			statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (Exception e) {
			System.out.println(e);
		}
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
}
