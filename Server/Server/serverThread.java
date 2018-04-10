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
			System.out.println("Client: " + socket.getInetAddress() + " said " + message);
			if(message.equals("1")){
				this.checkUID();
			}
			else if(message.equals("2")) {
				this.checkData();
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
			Thread.sleep(3000);
			System.out.println("Sending object.");
			objectOutput.writeObject(SQL.checkData(UID, pin));
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
