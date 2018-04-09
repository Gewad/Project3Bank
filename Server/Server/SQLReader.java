package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SQLReader {
	private Connection SQLConnection;
	private String dbms = "mysql";
	private String host = "gewadstudios.nl";
	private String port = "3306";
	private String data = "gewadstu_school";
	private String user = "";
	private String pass = "";
	/*
	public static void main(String[] args) {
		System.out.println("I at lest got here you know this is better than not running at all.");
		SQLReader SQL = new SQLReader("gewadstu_school","S_eK8^?FUam!");
	}
	*/
	public SQLReader(String username, String passwd) {
		user = username;
		pass = passwd;
		System.out.println("I got here at least you know gave it a shot.");
		try {
		SQLConnection = getConnection();
		System.out.println("I tried to get a connection.");
		} catch(SQLException e) {
			System.out.println("I caught an error.");
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() throws SQLException {

	    Connection conn = null;
	    Properties connectionProps = new Properties();
	    connectionProps.put("user", this.user);
	    connectionProps.put("password", this.pass);
	    
	    if (this.dbms.equals("mysql")) {
	        conn = DriverManager.getConnection(
	                   "jdbc:" + this.dbms + "://" +
	                   this.host +
	                   ":" + this.port + "/" + this.data,
	                   this.user, this.pass);
	    }
	    System.out.println("I LIVE");
	    if(conn.isValid(1000)) {
	    	System.out.println("Connected to database");
	    	return conn;
	    }else {
	    	System.out.println("Not connected to database");
	    	return null;
	    }
	}
}
