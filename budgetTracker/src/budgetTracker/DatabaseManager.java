package budgetTracker;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseManager {
	
	public Connection connectDatabase(String database, String user, String pw) {
		Connection connection = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+ database,user,pw);
			if (connection != null) {
				System.out.println("connected");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return connection;
	}

}
