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
		//this.objectInput = new ObjectInputStream(socket.getInputStream());
		this.objectOutput = new ObjectOutputStream(socket.getOutputStream());
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("Server Thread for " + socket.getInetAddress() + " done loading.");
	}
	
	public void run() {

		while(true) {
			String message = waitForInput();
			if(message.equals("1")){
				this.checkUID();
			} else if(message.equals("2")) {
				this.checkData();
			} else if(message.equals("3")) {
				this.getSaldo();
			} else if(message.equals("4")) {
				this.withdraw();
			} else if(message.equals("5")) {
				this.transfer();
			} else if(message.equals("6")) {
				this.changePin();
			}  else if(message.equals("7")) {
				this.addLog();
			}
			message = "";
			try {Thread.sleep(1000);} catch (InterruptedException e) {}
		}
	}
	
	public void checkUID() {
		System.out.println("Client requested UIDCheck");
		output.println("1");
		String message = waitForInput();
		output.println(Integer.toString(SQL.checkUID(message)));
		System.out.println("I returned: ");
	}
	
	public void checkData() {
		String UID;
		String pin;
		output.println("1");
		UID = waitForInput();
		output.println("1");
		pin = waitForInput();
		output.println("1");
		try {
			Thread.sleep(5000);
			System.out.println("Sending object.");
			AccountData out = SQL.checkData(UID, pin);
			objectOutput.writeObject(out);
			
			if(!out.getValid()) {
				if(attemptUID.equals(UID)) {
					attemptCount += 1;
					if(attemptCount == 3) {
						SQL.blockCards(UID);
					}
				} else {
					attemptUID = UID;
					attemptCount = 0;
				}
			}
		} catch (Exception e) {
			System.out.println("Something went wrong with sending the object.");
			try {
				objectOutput.writeObject(new AccountData(false, "", "", "", "", 2, null));
			} catch (IOException e1) {
				System.out.println("Something went wrong with sending the second object.");
				e1.printStackTrace();
			}
		}
	}
	
	public void getSaldo() {
		String account;
		output.println("1");
		account = waitForInput();
		output.println(Integer.toString(SQL.getSaldo(account)));
	}
	
	public void withdraw() {
		String account;
		String amount;
		output.println("1");
		account = waitForInput();
		output.println("1");
		amount = waitForInput();
		output.println(Integer.toString(SQL.withdraw(account, Integer.parseInt(amount))));
	}
	
	public void transfer() {
		String target;
		String sender;
		String amount;
		output.println("1");
		sender = waitForInput();
		output.println("1");
		target = waitForInput();
		output.println("1");
		amount = waitForInput();
		output.println(Integer.toString(SQL.transfer(sender, target, Integer.parseInt(amount))));
	}
	
	public void changePin() {
		String UID;
		String oldPin;
		String newPin;
		output.println("1");
		UID = waitForInput();
		output.println("1");
		oldPin = waitForInput();
		output.println("1");
		newPin = waitForInput();
		output.println(Integer.toString(SQL.changePin(UID, oldPin, newPin)));
	}
	
	public void addLog() {
		
	}
	
	public String waitForInput() {
		String mes = "";
		while(true) {
			try {
				mes = input.readLine();
			}catch(Exception e) {
			}
			if(!mes.equals("")) {
				System.out.println("Client: "+socket.getInetAddress()+" said: "+mes);
				break;
			}
		}
		return mes;
	}
}
