package BankFinal;

import java.awt.EventQueue;

import BankPanels.*;
import ServerCommunication.ServerCommunication;

public class Main {
	private static boolean gotAccount = false;
	private final static String ATM = "BG100001";
	private int currentScreen = 0;
	Background background;
	ServerCommunication server;
	String UID;
	String pin;

	int pinCharCount;
	String[] stars = { "", "*", "**", "***", "****" };
	String[] numericChars = { "#", "*", "A", "B", "C", "D" };

	public static void main(String[] args) {
		Main machine = new Main();
	}

	public Main() {
		server = new ServerCommunication("localhost", 6789, ATM);

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					background = new Background();
					background.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		Thread arduinoListener = new Thread() {
			public void run() {
				HardwareControl arduino = new HardwareControl();

				while (true) {
					String input = arduino.getInput();
					if (!input.equals("")) {
						System.out.println(input);
						if ((input.contains("UID:")) && currentScreen == 0) {
							input = input.substring(input.indexOf(":") + 1);
							UID = input;

							int serverResponse = server.checkUID(input);
							if (serverResponse != 0) {
								background.showMessage(serverResponse);
							}
							changeScreen(1);
						} else if ((input.contains("KEY:")) && currentScreen == 1) {
							System.out.println("I received a key.");
							input = input.substring(input.indexOf(":") + 1);
							
							if(isNumeric(input)) {
								pin += input;
								pinCharCount++;
							
								background.setPanel(new login2(stars[pinCharCount]));
							
							} else if(input.equals("#")) {
								int serverResponse = server.checkPin(UID, pin);

								if (serverResponse != 0) {
									background.showMessage(serverResponse);
									pin = "";
									pinCharCount = 0;
									background.setPanel(new login2(stars[pinCharCount]));
								}
								server.getData(UID, pin);
								changeScreen(2);
							} else if(input.equals("*")) {
								pin = "";
								pinCharCount = 0;
								background.setPanel(new login2(stars[pinCharCount]));
							}
						}	
					}
				}
			}
		};
		arduinoListener.start();

		try {
			Thread.sleep(1000);
		} catch (Exception e) {
		}
	}

	public void changeScreen(int t) {
		currentScreen = t;
		System.out.println("Screen changed to: " + t);
		switch (t) {
		case 0:
			this.background.setPanel(new login1());
			break;
		case 1:
			this.background.setPanel(new login2(stars[0]));
			break;
		case 2:
			this.background.setPanel(
					new homeMenu(server.getAccountData().getName() + " " + server.getAccountData().getSurname()));
			break;
		case 3:
			// geld opnemen
			break;
		case 4:
			// geld overboeken
			break;
		case 5:
			// wijzigen menu
			break;
		case 6:
			// pincode wijzigen
			break;
		case 7:
			// adres wijzigen | UIT
			break;
		case 8:
			// naam wijzigen | UIT
			break;
		case 9:
			// saldo
			break;
		}
	}
	
	private boolean isNumeric(String input) {
		
		for(int i = 0; i < numericChars.length; i++) {
			if(input.equals(numericChars[i])) {
				return false;
			}
		}
		
		return true;
	}
}