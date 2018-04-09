package ServerCommunication;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLCommunication {
	
	
	
	
	public SQLCommunication() throws Exception {
		Connection con = (Connection) getConnection();
	}
	
	public static Connection getConnection() throws Exception {
		try {
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql:158.69.126.166:3306/gewadstu_school";
			String username = "gewadstu_bankser";
			String password = "ikbenbank!";
			Class.forName(driver);

			Connection conn = DriverManager.getConnection(url, username, password);
			System.out.println("Connected");
			return conn;

		} catch (Exception e) {
			System.out.println(e);
		}

		return null;
	}
}
