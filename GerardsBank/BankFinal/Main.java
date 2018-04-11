package BankFinal;

import java.awt.EventQueue;

import ServerCommunication.ServerCommunication;

public class Main {
	private static boolean gotAccount = false;
	private final static String ATM = "BG100001";
	private int currentScreen = 0;
	
	public static void main(String[] args) {
		ServerCommunication server = new ServerCommunication("localhost", 6789, ATM);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Background background = new Background();
					background.setVisible(true);
					background.showMessage(6);
					background.showMessage(4);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		Thread arduinoListener = new Thread() {
			public void run() {
				
			}
		};
		arduinoListener.start();
	}
	
	public void arduinoListener() {
		
	}
	
	public void changeScreen(int t){
		currentScreen = t;
		System.out.println("Screen changed to: " + t);
		switch(t){
			case 0:
				//login 1: UID
				break;
			case 1:
				//login 2: Pin
				break;
			case 2:
				//home menu keuze knoppen
				break;
			case 3:
				//geld opnemen
				break;
			case 4:
				//geld overboeken
				break;
			case 5:
				//wijzigen menu
				break;
			case 6:
				//pincode wijzigen
				break;
			case 7:
				//adres wijzigen | UIT
				break;
			case 8:
				//naam wijzigen | UIT
				break;
			case 9:
				// saldo
				break;
		}
	}
}