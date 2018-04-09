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
	String message;
	ObjectInputStream objectInput;
	ObjectOutputStream objectOutput;
	//private SQLReader SQL = new SQLReader("gewadstu_school","S_eK8^?FUam!");
	
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
		System.out.println("Thread started for: " + socket.getInetAddress());
		while(true) {
			message = waitForInput();
			System.out.println("Client: " + socket.getInetAddress() + " said " + message);
			if(message.equals("1")){
				System.out.println("Client: " + socket.getInetAddress() + " says Hi");
				output.println("Hoi");
			}
			else if(message.equals("2")) {
				System.out.println("Client: " + socket.getInetAddress() + " requests Object");
				AccountData sendTest = new AccountData(true, message, message, message, message, 2, null);
				try {
					objectOutput.writeObject(sendTest);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			message = "";
			try {Thread.sleep(1000);} catch (InterruptedException e) {}
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
				break;
			}
		}
		return mes;
	}
}
