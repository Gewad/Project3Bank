package ServerCommunication;

import java.util.Scanner;

import ServerCommunication.ServerCommunication;

public class testMain {
	static ServerCommunication server;
	private final static String ATM = "BG100001";
	
	public static void main(String[] args) {
		server = new ServerCommunication("localhost", 6789, ATM);
		if(server.isConnected()) {
		Thread inputReader = new Thread() {
			public void run() {
				Scanner sc = new Scanner(System.in);
				while(true) {
					System.out.print("Enter command to run: ");
					while(!sc.hasNextLine()) {}
					String input = sc.nextLine();
					if(input.equals("checkData")) {
						System.out.print("Give UID: ");
						while(!sc.hasNextLine()) {}
						String UID = sc.nextLine();
						System.out.print("Give pin: ");
						while(!sc.hasNextLine()) {}
						String pin = sc.nextLine();
						if(server.checkData(UID, pin) == 0) {
							System.out.println("Server confirms this data.");
						} else {
							System.out.println("Login does not check out.");
						}
					} else if(input.equals("checkUID")) {
						System.out.print("Give UID: ");
						while(!sc.hasNextLine()) {}
						String UID = sc.nextLine();
						int temp = server.checkUID(UID);
						if(temp == 0) {
							System.out.println("Card is recognised and not blocked.");
						} else if(temp == 1) {
							System.out.println("Card is not recognised.");
						} else if(temp == 2) {
							System.out.println("Card is blocked");
						}
					} else if(input.equals("")) {
						System.out.print("Give UID: ");
						while(!sc.hasNextLine()) {}
						String UID = sc.nextLine();
						int temp = server.checkUID(UID);
						if(temp == 0) {
							System.out.println("Card is recognised and not blocked.");
						} else if(temp == 1) {
							System.out.println("Card is not recognised.");
						} else if(temp == 2) {
							System.out.println("Card is blocked");
						}
					}
				}
			}
		};
		inputReader.start();
		} else {
			System.out.println("Connection with server could not be established.");
		}

	}
	
	

}
