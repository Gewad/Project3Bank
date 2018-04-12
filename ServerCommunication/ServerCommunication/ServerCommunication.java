package ServerCommunication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import ServerCommunication.AccountData;

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

	public final int SERVER_ERROR = -1;

	public final int OK = 0;
	public final int UNKNOWN_CARD = 1;
	public final int BLOCKED = 2;
	public final int LOW_BALANCE = 3;
	public final int UNKNOWN_ACCOUNT = 4;
	public final int INVALID_PIN = 5;
	public final int INCORRECT_PIN = 7;

	public ServerCommunication(String connection, int port, String ATM) {
		System.out.println("Imma try and connect now.");
		try {
			server = new Socket(connection, port);
			input = new BufferedReader(new InputStreamReader(server.getInputStream()));
			output = new PrintStream(server.getOutputStream());
			objectInput = new ObjectInputStream(server.getInputStream());
		} catch (Exception e) {
			connected = false;
			e.printStackTrace();
			return;
		}
		connected = true;
		atmID = ATM;
	}

	public String waitForResponse() {
		System.out.println("Waiting for input.");
		String message = "";
		while (true) {
			try {
				message = input.readLine();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (!message.equals("")) {
				System.out.println("Client: " + server.getInetAddress() + " said: " + message);
				return message;
			}
		}
	}
	
	private void sendToServer(String message) {
		System.out.println("Sending message to server: " +message);
		output.println(message);
	}

	public int checkUID(String UID) {
		sendToServer("CHECK_UID " + UID);

		String response = waitForResponse();

		return getStatusFromResponse(response);
	}

	public int checkData(String UID, String pin) {
		sendToServer("CHECK_DATA " + UID + " " + pin);

		String response = waitForResponse();

		return getStatusFromResponse(response);
	}

	public int getSaldo(String rekeningID, String pin) {
		sendToServer("GET_SALDO " + rekeningID + " " + pin);

		String response = waitForResponse();

		return getStatusFromResponse(response);
	}

	public int withdraw(String rekeningID, String pin, String amount) {
		sendToServer("WITHDRAW " + rekeningID + " " + pin + " " + amount);

		String response = waitForResponse();

		return getStatusFromResponse(response);
	}

	public int transfer(String senderID, String pin, String targetID, String amount) {
		sendToServer("TRANSFER " + senderID + " " + pin + " " + targetID + " " + amount);

		String response = waitForResponse();

		return getStatusFromResponse(response);
	}

	public int changePin(String UID, String currentPin, String newPin) {
		sendToServer("CHANGE_PIN " + UID + " " + currentPin + " " + newPin);

		String response = waitForResponse();

		return getStatusFromResponse(response);
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
	
	public void clear() {
		data = null;
	} 
	
	public AccountData getAccountData() {
		return data;
	}

	public boolean isConnected() {
		return this.connected;
	}

	private int getStatusFromResponse(String response) {
		String[] parts = response.split(" ", 3);
		
		return parts[1];
	}
}
