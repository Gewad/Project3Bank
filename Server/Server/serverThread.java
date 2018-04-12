package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class serverThread extends Thread {
	Socket socket;
	BufferedReader input;
	PrintStream output;
	ObjectInputStream objectInput;
	ObjectOutputStream objectOutput;
	String attemptUID = "";
	int attemptCount = 0;
	private SQLReader SQL = new SQLReader();

	public serverThread(Socket inputSocket) {
		this.socket = inputSocket;
		try {
			this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.output = new PrintStream(socket.getOutputStream());
			// this.objectInput = new ObjectInputStream(socket.getInputStream());
			this.objectOutput = new ObjectOutputStream(socket.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Server Thread for " + socket.getInetAddress() + " done loading.");
	}

	public void run() {
		String message;
		while (true) {
			message = waitForInput();
			if (message.equals("1")) {
				this.checkUID();
			} else if (message.equals("2")) {
				this.checkData();
			} else if (message.equals("3")) {
				this.getSaldo();
			} else if (message.equals("4")) {
				this.withdraw();
			} else if (message.equals("5")) {
				this.transfer();
			} else if (message.equals("6")) {
				this.changePin();
			} else if (message.equals("7")) {
				this.addLog();
			} else if (message.equals("9")) {
				sendToClient("");
			}
			message = "";
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}

	public void checkUID() {
		System.out.println("Client requested UIDCheck");
		sendToClient("1");
		String message = waitForInput();
		sendToClient(Integer.toString(SQL.checkUID(message)));
		System.out.println("I returned: ");
	}

	public void checkData() {
		String UID;
		String pin;
		sendToClient("1");
		UID = waitForInput();
		sendToClient("1");
		pin = waitForInput();
		sendToClient("1");
		
		String message;
		
		AccountData out = SQL.checkData(UID, pin);
		String[] data = out.toStringarray();
		String[] rekeningen = out.getRekeningen();
		try{Thread.sleep(1000);}catch(Exception e) {}
		for(int i = 0; i < 6; i++) {
			sendToClient(data[i]);
			message = waitForInput();
			if(!message.equals("1")) { return; }
		}
		for(int i = 0; i < Integer.parseInt(data[5]); i++) {
			sendToClient(rekeningen[i]);
			message = waitForInput();
			if(!message.equals("1")) { return; }
		}

		System.out.println("Checking input");
		if (!(out.getValid() == 1)) {
			if (attemptUID.equals(UID)) {
				System.out.println("Input was invalid");
				attemptCount += 1;
				if (attemptCount == 3) {
					SQL.blockCards(UID);
				}
			} else {
				attemptUID = UID;
				attemptCount = 0;
			}
		}
	}

	public void getSaldo() {
		String account;
		sendToClient("1");
		account = waitForInput();
		sendToClient(Integer.toString(SQL.getSaldo(account)));
	}

	public void withdraw() {
		String account;
		String amount;
		sendToClient("1");
		account = waitForInput();
		sendToClient("1");
		amount = waitForInput();
		sendToClient(Integer.toString(SQL.withdraw(account, Integer.parseInt(amount))));
	}

	public void transfer() {
		String target;
		String sender;
		String amount;
		sendToClient("1");
		sender = waitForInput();
		sendToClient("1");
		target = waitForInput();
		sendToClient("1");
		amount = waitForInput();
		sendToClient(Integer.toString(SQL.transfer(sender, target, Integer.parseInt(amount))));
	}

	public void changePin() {
		String UID;
		String oldPin;
		String newPin;
		sendToClient("1");
		UID = waitForInput();
		sendToClient("1");
		oldPin = waitForInput();
		sendToClient("1");
		newPin = waitForInput();
		sendToClient(Integer.toString(SQL.changePin(UID, oldPin, newPin)));
	}

	public void addLog() {

	}

	public String waitForInput() {
		System.out.println("Waiting for input.");
		String message = "";
		while (true) {
			try {
				message = input.readLine();
			} catch (Exception e) {
			}
			if (!message.equals("")) {
				message = message.substring(8);
				System.out.println("Client: " + socket.getInetAddress() + " said: " + message);
				break;
			}
		}
		return message;
	}
	
	private void sendToClient(String message) {
		try {Thread.sleep(100);} catch(Exception e) {}
		System.out.println("Sending message: " +message+ ", to client.");
		output.println("MESSAGE:"+message);
	}
}
