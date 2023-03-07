package budgetTracker;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
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
					DatabaseManager.getUserIdByUsername(connection, FamilyMember.capitalizeName(purchasedBy)),
					purchaseAmount);
			statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void updatePurchase(Connection connection, int purchaseId, LocalDate newPurchaseDate, String newCategory,
			String newPurchasedBy, BigDecimal newPurchaseAmount) {
		Statement statement;
		try {
			String query = String.format(
					"update purchases set purchasedate = '%s', category = '%s', purchasedby = '%s', purchaseamount = '%s' where purchaseid = '%s';",
					java.sql.Date.valueOf(newPurchaseDate), newCategory,
					DatabaseManager.getUserIdByUsername(connection, newPurchasedBy), newPurchaseAmount, purchaseId);
			statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void readAllPurchases(Connection connection, Household household) {
		Statement statement;
		ResultSet result;
		try {
			String query = "select * from purchases;";
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			while (result.next()) {
				boolean alreadyExists = false;
				String[] purchaseDate = result.getString("purchasedate").split("-");
				int purchYear = Integer.valueOf(purchaseDate[0]);
				int purchMonth = Integer.valueOf(purchaseDate[1]);
				int purchDay = Integer.valueOf(purchaseDate[2]);
				LocalDate purchDate = LocalDate.of(purchYear, purchMonth, purchDay);
				String purchCategory = result.getString("category");
				double purchAmount = Double.valueOf(result.getString("purchaseamount"));
				String purchBy = DatabaseManager.getUsernameByUserId(connection,
						Integer.valueOf(result.getString("purchasedby")));
				for (int i = 0; i < household.getPurchasesList().size(); i++) {
					if (household.getPurchasesList().get(i).getCategory().toLowerCase()
							.equals(purchCategory.toLowerCase())
							&& household.getPurchasesList().get(i).getAmount() == purchAmount
							&& household.getPurchasesList().get(i).getPurchasedBy().toLowerCase()
									.equals(purchBy.toLowerCase())
							&& household.getPurchasesList().get(i).getDatePurchased().equals(purchDate)) {
						alreadyExists = true;
					}
				}

				if (!alreadyExists) {
					Purchase currPurchase = new Purchase(purchCategory, purchAmount, purchBy, purchDate);
					household.getPurchasesList().add(currPurchase);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
