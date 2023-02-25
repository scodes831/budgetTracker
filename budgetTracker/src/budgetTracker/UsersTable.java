package budgetTracker;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class UsersTable {

	public void createUsersTable(Connection connection) {
		Statement statement;
		try {
			String query = "CREATE TABLE IF NOT EXISTS users (userId SERIAL PRIMARY KEY, userName VARCHAR(30) NOT NULL, salary NUMERIC(10,2), "
					+ "UNIQUE(username))";
			statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void insertUsersRow(Connection connection, String username, BigDecimal salary) {
		Statement statement;
		try {
			String query = String.format("insert into users (username, salary) values ('%s','%s');", username, salary);
			statement = connection.createStatement();
			statement.executeUpdate(query);
			System.out.println("users row inserted");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void updateUser(Connection connection, String newUsername, String oldUsername, BigDecimal newSalary) {
		Statement statement;
		try {
			String query = String.format("update users set username = '%s', salary = '%s' where userid = '%s';", newUsername, newSalary,
					DatabaseManager.getUserIdByUsername(connection, oldUsername));
			statement = connection.createStatement();
			statement.executeUpdate(query);
			System.out.println("users row updated");

		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void readAllUsers(Connection connection, Household household) {
		Statement statement;
		ResultSet result;
		try {
			String query = "select * from users;";
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			while (result.next()) {
				boolean alreadyExists = false;
				String username = result.getString("username");
				double salary = Double.valueOf(result.getString("salary"));
				for (int i = 0; i < household.getHouseholdMembers().size(); i++) {
					if (household.getHouseholdMembers().get(i).getName().toLowerCase().equals(username.toLowerCase())) {
						alreadyExists = true;
					}
				}
				if (!alreadyExists) {
					FamilyMember newUser = new FamilyMember(username, salary);
					household.getHouseholdMembers().add(newUser);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
