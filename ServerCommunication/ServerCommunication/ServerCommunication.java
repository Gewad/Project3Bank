package bankFinal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import Server.AccountData;

public class ServerCommunication {
	
	String testPin = "2817";
	Socket server;
	BufferedReader input;
	PrintStream output;
	ObjectInputStream objectInput;
	ObjectOutputStream objectOutput;
	AccountData data;
	String message;
	boolean connected = false;
	
	public ServerCommunication(String connection, int port){
		System.out.println("Imma try and connect now.");
		try {
			server = new Socket(connection, port);
			input = new BufferedReader(new InputStreamReader(server.getInputStream()));
			output = new PrintStream(server.getOutputStream());
			objectInput = new ObjectInputStream(server.getInputStream());
			//objectOutput = new ObjectOutputStream(server.getOutputStream());
		}catch(Exception e) {
			System.out.println("Connection ded af");
			connected = false;
			return;
		}
		System.out.println("Connection established");
		connected = true;
	}
	
	public void sayHi() {
		output.println("1");
		System.out.println("I said Hi to the server: " + server.getInetAddress());
		waitForResponse();
	}
	
	public void waitForResponse() {
		Thread getString = new Thread() {
			String mes = "";
			public void run() {
				while(true) {
					try {
						mes = input.readLine();
					}catch(Exception e) {
					}
					if(!mes.equals("")) {
						message = mes;
						System.out.println(message);
						break;
					}
				}
			}
		};
		getString.start();
	}
	
	public void waitForObject() {
		Thread getObject = new Thread() {
			AccountData ad;
			public void run() {
				while(true) {
					try {
						ad = (AccountData) objectInput.readObject();
					}catch(Exception e) {
					}
					if(!(ad.equals(null))) {
						data = ad;
						System.out.println("Data object received.");
						break;
					}
				}
			}
		};
		getObject.start();
	}
	
	public boolean isConnected() {
		return connected;
	}
	
	public AccountData waitForAccountData() {
		return null;
	}
}
