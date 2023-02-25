package budgetTracker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

public class DatabaseManager {

	public static Connection connectDatabase(String database, String user, String pw) {
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

	public static int getUserIdByUsername(Connection connection, String username) {
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

	public static int getPurchaseIdByPurchase(Connection connection, LocalDate purchaseDate, String category,
			String purchasedBy, BigDecimal purchaseAmount) {
		Statement statement;
		ResultSet result = null;
		int id = 0;
		try {
			String query = String.format(
					"select purchaseid from purchases where purchasedate = '%s' and category = '%s' and purchasedby = '%s' and purchaseamount = '%s';",
					java.sql.Date.valueOf(purchaseDate), category,
					DatabaseManager.getUserIdByUsername(connection, purchasedBy),
					purchaseAmount.setScale(2, RoundingMode.HALF_UP));
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			while (result.next()) {
				id = Integer.valueOf(result.getString("purchaseid"));
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		return id;
	}

	public static int getBudgetRowIdByBudget(Connection connection, LocalDate budgetName, String category) {
		Statement statement;
		ResultSet result = null;
		int id = 0;
		try {
			String query = String.format(
					"select rowid from budgetvsactual where budgetname = '%s' and category = '%s';",
					java.sql.Date.valueOf(budgetName), category);
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			while (result.next()) {
				id = Integer.valueOf(result.getString("rowid"));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return id;
	}

}
