package Server;

import java.sql.*;
import java.util.ArrayList;

public class SQLReader {

	public final static int SERVER_ERROR = -1;

	public final static int OK = 0;
	public final static int UNKNOWN_CARD = 1;
	public final static int BLOCKED = 2;
	public final static int LOW_BALANCE = 3;
	public final static int UNKNOWN_ACCOUNT = 4;
	public final static int INVALID_PIN = 5;
	public final static int INCORRECT_PIN = 7;


	private Connection con;

	public SQLReader() {

		try {

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
				"jdbc:mysql://gewadstudios.nl:3306/gewadstu_school",
				"gewadstu_bankser",
				"ikbenbank!"
				);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// @Override
	public int checkUID(String UID) {

		try {

			String query = "SELECT Blocked FROM gewadstu_school.Card WHERE CardUID = '" + UID + "'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);

			if (!rs.next()) {
				return UNKNOWN_CARD;
			} else if (rs.getInt("Blocked") == 1) {
				return BLOCKED;
			} else {
				return OK;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return SERVER_ERROR;

	}

	// @Override
	public int checkPin(String UID, String pin) {

		try {

			String query = "SELECT Pin FROM gewadstu_school.Card WHERE CardUID = '" + UID + "'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);

			if (!rs.next()) {
				return UNKNOWN_CARD;
			} else if (pin.equals(rs.getString("Pin"))) {
				return OK;
			} else {
				return INCORRECT_PIN;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return SERVER_ERROR;
	}

	public AccountData getData(String UID) {

		try {
		
			String query1 = "SELECT id, Name, surName FROM Customer WHERE id IN "+ 
				"( SELECT klantID FROM Card WHERE CardUID = '" + UID + "')";
			Statement st1 = con.createStatement();			
			ResultSet customerRS = st1.executeQuery(query1);

			if (!customerRS.next()) {
				return null; // Klant onbekend
			}

			String customerID = customerRS.getString("id");

			Statement st2 = con.createStatement();
			String query2 = "SELECT id FROM Account WHERE id IN " + 
				"(SELECT AccountID FROM CustomerAccount WHERE CustomerID = '" + customerID + "')";
			ResultSet rekeningRS = st2.executeQuery(query2);
			
			ArrayList<String> rekeningen = new ArrayList<String>();

			while(rekeningRS.next()) {
				rekeningen.add(rekeningRS.getString("id"));
			}

			return new AccountData(
				customerID,
				UID,
				customerRS.getString("Name"),
				customerRS.getString("surName"),
				rekeningen.size(),
				rekeningen.toArray(new String[rekeningen.size()])
				);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Something went wrong af.");
			return null;
		}
	}

	// @Override
	public int getSaldo(String rekeningID) {

		try {

			String query = "SELECT id, Amount FROM gewadstu_school.Account WHERE id = '" + rekeningID + "'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);

			if (!rs.next()) {
				return SERVER_ERROR;
			}

			return rs.getInt("Amount");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return SERVER_ERROR;
	}

	// @Override
	public int withdraw(String rekeningID, int amount) {

		try {

			String query = "SELECT id, Amount FROM gewadstu_school.Account WHERE id = '" + rekeningID + "'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);

			if (!rs.next()) {
				return SERVER_ERROR;
			}

			if (rs.getInt("Amount") >= amount) {

				int newAmount = rs.getInt("Amount") - amount;

				query = "UPDATE gewadstu_school.Account SET Amount = '" + newAmount + "' WHERE id = '" + rekeningID
				+ "'";
				st.executeUpdate(query);

				return OK;
			}

			return LOW_BALANCE;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return SERVER_ERROR;
	}

	// @Override
	public int transfer(String fromID, String targetID, int amount) {

		try {

			String fromQuery = "SELECT id, Amount FROM gewadstu_school.Account WHERE id = '" + fromID + "'";
			Statement st1 = con.createStatement();
			ResultSet fromRs = st1.executeQuery(fromQuery);

			String targetQuery = "SELECT id, Amount FROM gewadstu_school.Account WHERE id = '" + targetID + "'";
			Statement st2 = con.createStatement();
			ResultSet targetRs = st2.executeQuery(targetQuery);

			if (!fromRs.next()) {
				return SERVER_ERROR;
			}

			if (!targetRs.next()) {
				return UNKNOWN_ACCOUNT;
			}

			if (fromRs.getInt("Amount") >= amount) {

				int newFromAmount = fromRs.getInt("Amount") - amount;
				int newTargetAmount = targetRs.getInt("Amount") + amount;

				String query = "UPDATE gewadstu_school.Account SET Amount = CASE id WHEN '" + fromID + "' THEN '"
				+ newFromAmount + "' WHEN '" + targetID + "' THEN '" + newTargetAmount
				+ "' ELSE Amount END WHERE id IN('" + fromID + "', '" + targetID + "')";
				st1.executeUpdate(query);

				return OK;

			}

			return LOW_BALANCE;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return SERVER_ERROR;
	}

	// @Override
	public int changePin(String kaartUID, String newHashedPin) {

		try {

			String query = "SELECT CardUID, Pin FROM gewadstu_school.Card WHERE CardUID = '" + kaartUID + "'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);

			if (!rs.next()) {
				return UNKNOWN_CARD;
			}

			if (rs.getString("CardUID").equals(kaartUID)) {
				query = "UPDATE gewadstu_school.Card SET Pin = '" + newHashedPin + "' WHERE CardUID = '" + kaartUID
				+ "'";
				st.executeUpdate(query);
			}

			return OK;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return SERVER_ERROR;
	}

	public void blockCard(String UID) {
		try {
			String query = "UPDATE gewadstu_school.Card SET Blocked = '1' WHERE CardUID = '" + UID + "'";
			Statement st = con.createStatement();
			st.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
