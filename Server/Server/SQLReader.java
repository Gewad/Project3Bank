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
	public int checkPin(String UID, String hashedPin) {

		try {

			String query = "SELECT KlantID, Pin FROM gewadstu_school.Card WHERE CardUID = '" + UID + "'";
			ResultSet rs = st.executeQuery(query);

			if (!rs.next()) {
				return 0;
			}

			if (rs.getString("Pin").equals(hashedPin)) {
				return 1;
			}

			return 0;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}
	
	public AccountData checkData(String UID, String pin) {
		try {
			int isValid = 0;
			isValid = checkPin(UID, pin);
			if(isValid != 1) {
				return new AccountData(0, "", "", "", "", 0, null);
			}
			
			String query = "SELECT Customer.id, Customer.Name, Customer.surName FROM Customer WHERE Customer.id IN(SELECT klantID FROM Card WHERE CardUID = '"+UID+"' && pin = '"+pin+"')";
			ResultSet customerRS = st.executeQuery(query);
			String customerID = customerRS.getString("Customer.id");
					
			query = "SELECT COUNT(Account.id) as AccountCount FROM Account WHERE Account.id IN(SELECT CustomerAccount.AccountID FROM CustomerAccount WHERE CustomerAccount.CustomerID = '"+customerID+"')";
			ResultSet rekeningAmount = st.executeQuery(query);
			int rekeningAmt = rekeningAmount.getInt("AccountCount");
			
			query = "SELECT Account.id as Account FROM Account WHERE Account.id IN(SELECT CustomerAccount.AccountID FROM CustomerAccount WHERE CustomerAccount.CustomerID = '"+customerID+"')";
			ResultSet rekening = st.executeQuery(query);
			
			String[] rekeningen = new String[rekeningAmt];
			for(int i = 0; i > rekeningAmt; i++) {
				rekeningen[i] = rekening.getString("Account");
				rekening.next();
			}
			
			AccountData out = new AccountData(isValid, customerRS.getString("Customer.id"), UID, customerRS.getString("Customer.Name"), customerRS.getString("Customer.surName"), 0, rekeningen);
			return out;
		} catch(Exception e) {
			return new AccountData(0, "", "", "", "", 0, null);
		}
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
	public int withdraw(String rekeningID, int amount) {

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
	public int transfer(String fromID, String targetID, int amount) {

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
	
	public void blockCards(String UID) {
		try {
		String query = "UPDATE gewadstu_school.Card SET Blocked = '1' WHERE CardUID = '" + UID + "'";
		st.executeUpdate(query);
		} catch(SQLException e) {}
		return;
	}
}
