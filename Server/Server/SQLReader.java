package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SQLReader {
	public SQLReader() {
		System.out.println("I act like a SQL reader but I am just a stub");
	}
	
	public int checkUID(String UID) {
		if(UID.equals("kaartje")) {
			return 0;
		} else if(UID.equals("kaartje2")) {
			return 2;
		}
		return 1;
	}
	
	public AccountData checkData(String UID, String pin) {
		if(UID.equals("kaartje") && pin.equals("6969")) {
			return new AccountData(true, "1", "1", "Gerard", "van Walraven", 9, null);
		}
		return new AccountData(false, "", "", "", "", 2, null);
	}
}
