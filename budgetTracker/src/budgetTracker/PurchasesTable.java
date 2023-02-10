package budgetTracker;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;

public class PurchasesTable {

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

	public void insertPurchasesRow(Connection connection, LocalDate purchaseDate, String category, String purchasedBy,
			BigDecimal purchaseAmount) {
		Statement statement;
		try {
			String query = String.format(
					"insert into purchases (purchaseDate, category, purchasedBy, purchaseAmount) values ('%s','%s','%s','%s');",
					java.sql.Date.valueOf(purchaseDate), category,
					DatabaseManager.getUserIdByUsername(connection, purchasedBy), purchaseAmount);
			statement = connection.createStatement();
			statement.executeUpdate(query);
			System.out.println("purchase row inserted");

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void updatePurchase(Connection connection, int purchaseId, LocalDate newPurchaseDate, String newCategory,
			String newPurchasedBy, BigDecimal newPurchaseAmount) {
		Statement statement;
		try {
			String query = String.format(
					"update purchases set purchasedate = '%s', category = '%s', purchasedby = '%s', purchaseamount = '%s';",
					java.sql.Date.valueOf(newPurchaseDate), newCategory, DatabaseManager.getUserIdByUsername(connection, newPurchasedBy), newPurchaseAmount);
			statement = connection.createStatement();
			statement.executeUpdate(query);
			System.out.println("purchase row updated");
		} catch (Exception e) {
			System.out.println(e);
		}

	}
}
