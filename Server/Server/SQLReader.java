package Server;

public class SQLReader {
	private String GerardsID = "69";
	private int GerardsSaldo = 42069;
	private String GerardsKaart = "kaartje";
	private String GerardsPin = "6969";

	public SQLReader() {
		System.out.println("I act like a SQL reader but I am just a stub");
	}

	public int checkUID(String UID) {
		if (UID.equals(GerardsKaart)) {
			return 0;
		} else if (UID.equals("kaartje2")) {
			return 2;
		}
		return 1;
	}

	public AccountData checkData(String UID, String pin) {
		if (UID.equals(GerardsKaart) && pin.equals(GerardsPin)) {
			return new AccountData(true, "1", "1", "Gerard", "van Walraven", 9, null);
		}
		return new AccountData(false, "", "", "", "", 2, null);
	}

	public int getSaldo(String account) {
		return GerardsSaldo;
	}

	public int withdraw(String account, int amount) {
		if (account.equals(GerardsID)) {
			if (amount > 0) {
				if (GerardsSaldo >= amount) {
					GerardsSaldo -= amount;
					return 0;
				}
			}
		}
		return 3;
	}

	public int transfer(String account, String target, int amount) {
		if (account.equals(GerardsID)) {
			if (amount > 0) {
				if (GerardsSaldo >= amount) {
					GerardsSaldo -= amount;
					return 0;
				}
			}
		}
		return 3;
	}

	public int changePin(String UID, String oldPin, String newPin) {
		if (UID.equals(GerardsKaart)) {
			if (oldPin.equals(GerardsPin)) {
				GerardsPin = newPin;
				return 0;
			}
		}
		return 7;
	}
}
