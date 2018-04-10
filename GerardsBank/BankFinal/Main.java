package BankFinal;

import ServerCommunication.ServerCommunication;

public class Main {
	private static boolean gotAccount = false;
	private final static String ATM = "BG100001";
	
	public static void main(String[] args) {
		ServerCommunication server = new ServerCommunication("localhost", 6789, ATM);
	}
}