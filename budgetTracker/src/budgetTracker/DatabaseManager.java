package budgetTracker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

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

}
