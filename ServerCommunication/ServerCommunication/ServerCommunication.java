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

	public ServerCommunication(String connection, int port, String ATM) {
		System.out.println("Imma try and connect now.");
		try {
			server = new Socket(connection, port);
			input = new BufferedReader(new InputStreamReader(server.getInputStream()));
			output = new PrintStream(server.getOutputStream());
			objectInput = new ObjectInputStream(server.getInputStream());
		} catch (Exception e) {
			connected = false;
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
			}
			if (!message.equals("")) {
				message = message.substring(8);
				System.out.println("Client: " + server.getInetAddress() + " said: " + message);
				break;
			}
		}
		return message;
	}
	
	private void sendToServer(String message) {
		try {Thread.sleep(100);} catch(Exception e) {}
		System.out.println("Sending message: " +message+ ", to Server.");
		output.println("MESSAGE:"+message);
	}

	public int checkUID(String UID) {
		sendToServer("1");
		System.out.println("I requested a check on the current card from server: " + server.getInetAddress());
		String message = waitForResponse();
		if (message.equals("1")) {
			sendToServer(UID);
			message = waitForResponse();
			int messageInt = Integer.parseInt(message);
			return messageInt;
		}
		return 9;
	}

	public int checkData(String UID, String pin) {
		sendToServer("2");
		System.out.println("I requested the AccountData from server: " + server.getInetAddress());
		String message = waitForResponse();
		
		if (!message.equals("1")) {
			return 9;
		}
	
		sendToServer(UID);
		message = waitForResponse();

		if (!message.equals("1")) {
			return 9;
		}
		
		sendToServer(pin);
		message = waitForResponse();

		if (!message.equals("1")) {
			return 9;
		}
		
		String[] accountData = new String[6];
		
		for(int i = 0; i < 6; i++) {
			message = waitForResponse();
			accountData[i] = message;
			sendToServer("1");
		}
		
		int rekeningAmount = Integer.parseInt(accountData[5]);
		String[] rekeningen = new String[rekeningAmount];
		
		for(int i = 0; i < Integer.parseInt(accountData[5]); i++) {
			message = waitForResponse();
			rekeningen[i] = message;
			sendToServer("1");
		}
		
		data = new AccountData(accountData, rekeningen);
		
		if(data.getValid() == 1) {
			return 0;
		}
		
		return 7;
	}

	public int getSaldo(String rekeningID) {
		sendToServer("3");
		String message = waitForResponse();
		if(message.equals("1")) {
			sendToServer(rekeningID);
			message = waitForResponse();
			int messageInt = Integer.parseInt(message);
			return messageInt;
		}
		return 0;
	}
	
	public void quickTest() {
		sendToServer("9");
		waitForResponse();
		return;
	}

	public int withdraw(String rekeningID, String amount) {
		sendToServer("4");
		String message = waitForResponse();
		sendToServer(rekeningID);
		message = waitForResponse();
		sendToServer(amount);
		message = waitForResponse();
		return Integer.parseInt(message);
	}

	public int transfer(String senderID, String targetID, String amount) {
		sendToServer("5");
		String message = waitForResponse();
		sendToServer(senderID);
		message = waitForResponse();
		sendToServer(targetID);
		message = waitForResponse();
		sendToServer(amount);
		message = waitForResponse();
		return Integer.parseInt(message);
	}

	public int changePin(String UID, String currentPin, String newPin) {
		sendToServer("6");
		String message = waitForResponse();
		sendToServer(UID);
		message = waitForResponse();
		sendToServer(currentPin);
		message = waitForResponse();
		sendToServer(newPin);
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
	
	public void clear() {
		data = null;
	}

	public void addLog(String Customer, String Account, String page) {
		sendToServer("7");
	}
	
	public AccountData getAccountData() {
		return data;
	}

	public boolean isConnected() {
		return this.connected;
	}
}
