package budgetTracker;

import java.sql.Connection;
import java.sql.Statement;

public class UsersTable {
	
	public void createUsersTable(Connection connection) {
		Statement statement;
		try {
			String query = "CREATE TABLE IF NOT EXISTS users (userId SERIAL PRIMARY KEY, userName VARCHAR(30), "
					+ "UNIQUE(username))";
			statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void insertUsersRow(Connection connection, String username) {
		Statement statement;
		try {
			String query = String.format("insert into users (username) values ('%s');", username);
			statement = connection.createStatement();
			statement.executeUpdate(query);
			System.out.println("users row inserted");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
