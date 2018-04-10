package ServerCommunication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import Server.AccountData;

public class ServerCommunication {

	String atmID;
	String testPin = "2817";
	Socket server;
	BufferedReader input;
	PrintStream output;
	ObjectInputStream objectInput;
	ObjectOutputStream objectOutput;
	protected AccountData data;
	boolean connected = false;

	public ServerCommunication(String connection, int port, String ATM) {
		System.out.println("Imma try and connect now.");
		try {
			server = new Socket(connection, port);
			input = new BufferedReader(new InputStreamReader(server.getInputStream()));
			output = new PrintStream(server.getOutputStream());
			objectInput = new ObjectInputStream(server.getInputStream());
			// objectOutput = new ObjectOutputStream(server.getOutputStream());
		} catch (Exception e) {
			connected = false;
			return;
		}
		connected = true;
		atmID = ATM;
	}

	public String waitForResponse() {
		String mes = "";
		while (true) {
			try {
				mes = input.readLine();
			} catch (Exception e) {
			}
			if (!mes.equals("")) {
				System.out.println("I received message: " + mes);
				return mes;
			}
		}
	}

	private AccountData waitForObject() {
		AccountData ad = new AccountData(false, null, null, null, null, 1, null);
		while (true) {
			try {
				ad = (AccountData) objectInput.readObject();
			} catch (Exception e) {
			}
			if (ad.getValid()) {
				return ad;
			}
		}
	}

	public int checkUID(String UID) {
		output.println("1");
		System.out.println("I requested a check on the current card from server: " + server.getInetAddress());
		String message = waitForResponse();
		if (message.equals("1")) {
			output.println(UID);
			message = waitForResponse();
			int messageInt = Integer.parseInt(message);
			return messageInt;
		}
		return 9;
	}

	public int checkData(String UID, String pin) {
		output.println("2");
		System.out.println("I requested the AccountData from server: " + server.getInetAddress());
		String message = waitForResponse();
		if (message.equals("1")) {
			output.println(UID);
			message = waitForResponse();
			if (message.equals("1")) {
				output.println(pin);
				message = waitForResponse();
				if (message.equals("1")) {
					System.out.println("Waiting for object.");
					data = waitForObject();
					System.out.println("AccountData received.");
					return 0;
				}
			}
		}
		return 7;
	}

	public int getSaldo(String rekeningID) {
		output.println("3");
		String message = waitForResponse();
		if(message.equals("1")) {
			output.println(rekeningID);
			message = waitForResponse();
			int messageInt = Integer.parseInt(message);
			return messageInt;
		}
		return 0;
	}

	public int withdraw(String rekeningID, String amount) {
		output.println("4");
		String message = waitForResponse();
		output.println(rekeningID);
		message = waitForResponse();
		output.println(amount);
		message = waitForResponse();
		return Integer.parseInt(message);
	}

	public int transfer(String senderID, String targetID, String amount) {
		output.println("5");
		String message = waitForResponse();
		output.println(senderID);
		message = waitForResponse();
		output.println(targetID);
		message = waitForResponse();
		output.println(amount);
		message = waitForResponse();
		return Integer.parseInt(message);
	}

	public int changePin(String UID, String currentPin, String newPin) {
		output.println("6");
		String message = waitForResponse();
		output.println(UID);
		message = waitForResponse();
		output.println(currentPin);
		message = waitForResponse();
		output.println(newPin);
		message = waitForResponse();
		return Integer.parseInt(message);
	}

	public void closeConnection() {
		try {
			server.close();
			System.out.println("Connection closed.");
			return;
		} catch (IOException e) {
		}
		System.out.println("Connection couldn't be closed.");
	}

	public void addLog(String Customer, String Account, String page) {
		output.println("7");
	}

	public boolean isConnected() {
		return this.connected;
	}
}
