package Server;

import java.sql.*;

public class SQLReader {

	private Statement st;
	private Statement rt;
	private Connection con;

	public SQLReader() {

		try {

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://gewadstudios.nl:3306/gewadstu_school", "gewadstu_bankser",
					"ikbenbank!");
			st = con.createStatement();
			rt = con.createStatement();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//@Override
	public int checkUID(String UID) {

		try {

			String query = "SELECT Blocked FROM gewadstu_school.Card WHERE CardUID = '" + UID + "'";
			ResultSet rs = st.executeQuery(query);

			if (!rs.next()) {
				return 1;
			}

			if (rs.getInt("Blocked") == 0) {
				return 0;
			} else if (rs.getInt("Blocked") == 1) {
				return 2;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;

	}

	//@Override
	public String checkPin(String UID, String hashedPin) {

		try {

			String query = "SELECT KlantID, Pin FROM gewadstu_school.Card WHERE CardUID = '" + UID + "'";
			ResultSet rs = st.executeQuery(query);

			if (!rs.next()) {
				return "1000";
			}

			if (rs.getString("Pin").equals(hashedPin)) {
				return rs.getString("KlantID");
			}

			return null;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "-1";
	}

	//@Override
	public int getSaldo(String rekeningID) {

		try {

			String query = "SELECT id, Amount FROM gewadstu_school.Account WHERE id = '" + rekeningID + "'";
			ResultSet rs = st.executeQuery(query);

			if (!rs.next()) {
				return -1;
			}

			return rs.getInt("Amount");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	//@Override
	public int withdraw(int rekeningID, int amount) {

		try {

			String query = "SELECT id, Amount FROM gewadstu_school.Account WHERE id = '" + rekeningID + "'";
			ResultSet rs = st.executeQuery(query);

			if (!rs.next()) {
				return -1;
			}

			if (rs.getInt("Amount") >= amount) {

				int newAmount = rs.getInt("Amount") - amount;

				query = "UPDATE gewadstu_school.Account SET Amount = '" + newAmount + "' WHERE id = '" + rekeningID
						+ "'";
				st.executeUpdate(query);

				return 0;
			}

			return 3;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	//@Override
	public int transfer(int fromID, int targetID, int amount) {

		try {

			String fromQuery = "SELECT id, Amount FROM gewadstu_school.Account WHERE id = '" + fromID + "'";

			ResultSet fromRs = st.executeQuery(fromQuery);

			String targetQuery = "SELECT id, Amount FROM gewadstu_school.Account WHERE id = '" + targetID + "'";

			ResultSet targetRs = rt.executeQuery(targetQuery);

			if (!fromRs.next()) {
				return -1;
			}

			if (!targetRs.next()) {
				return 4;
			}

			if (fromRs.getInt("Amount") >= amount) {

				int newFromAmount = fromRs.getInt("Amount") - amount;
				int newTargetAmount = targetRs.getInt("Amount") + amount;

				String query = "UPDATE gewadstu_school.Account SET Amount = CASE id WHEN '" + fromID + "' THEN '"
						+ newFromAmount + "' WHEN '" + targetID + "' THEN '" + newTargetAmount
						+ "' ELSE Amount END WHERE id IN('" + fromID + "', '" + targetID + "')";
				st.executeUpdate(query);

				return 0;

			}

			return 3;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	//@Override
	public int changePin(String kaartUID, String oldHashedPin, String newHashedPin) {

		try {

			String query = "SELECT CardUID, Pin FROM gewadstu_school.Card WHERE CardUID = '" + kaartUID + "'";
			ResultSet rs = st.executeQuery(query);
			
			if(!rs.next()) {
				return 5;
			}
			
			if(rs.getString("Pin") != oldHashedPin) {
				return 7;
			}

			if (rs.getString("CardUID").equals(kaartUID)) {
				query = "UPDATE gewadstu_school.Card SET Pin = '" + newHashedPin + "' WHERE CardUID = '" + kaartUID + "'";
				st.executeUpdate(query);
			}
			return 0;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 9;
	}

}
