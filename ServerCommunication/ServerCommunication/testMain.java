package ServerCommunication;

import java.util.Scanner;

import ServerCommunication.ServerCommunication;

public class testMain {
	static ServerCommunication server;
	private final static String ATM = "BG100001";

	public static void main(String[] args) {
		server = new ServerCommunication("localhost", 6789, ATM);
		
		if(!server.isConnected()) {
			System.out.println("Connection with server could not be established.");
			return;
		}
		
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
						
						server.checkData(UID, pin);
						if(server.getAccountData().getValid() == 1) {
							System.out.println("Server confirms this data.");
						} else {
							System.out.println("Data send was incorrect.");
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
					} else if(input.equals("getSaldo")) {
						System.out.print("Give accountID: ");
						
						while(!sc.hasNextLine()) {}
						
						String account = sc.nextLine();
						
						System.out.println(server.getSaldo(account));
						System.out.println("I sent Gerard's saldo.");
					} else if(input.equals("withdraw")) {
						System.out.print("Give accountID: ");
						
						while(!sc.hasNextLine()) {}
						
						String account = sc.nextLine();
						System.out.print("Give amount: ");
						
						while(!sc.hasNextLine()) {}
						
						String amount = sc.nextLine();
						server.withdraw(account, amount);
					} else if(input.equals("transfer")) {
						System.out.print("Give accountID: ");
						
						while(!sc.hasNextLine()) {}
						
						String account = sc.nextLine();
						System.out.print("Give target: ");
						
						while(!sc.hasNextLine()) {}
						
						String target = sc.nextLine();
						System.out.print("Give amount: ");
						
						while(!sc.hasNextLine()) {}
						
						String amount = sc.nextLine();
						server.transfer(account, target, amount);
					} else if(input.equals("changePin")) {
						System.out.print("Give UID: ");
						
						while(!sc.hasNextLine()) {}
						String UID = sc.nextLine();
						System.out.print("Give currentPin: ");
						
						while(!sc.hasNextLine()) {}
						String currentPin = sc.nextLine();
						System.out.print("Give newPin: ");
						
						while(!sc.hasNextLine()) {}
						
						String newPin = sc.nextLine();
						server.changePin(UID, currentPin, newPin);
					} else if(input.equals("stop")) {
						server.closeConnection();
						sc.close();
						break;
					}
				}
			}
		};
		
		inputReader.start();
	}
}
