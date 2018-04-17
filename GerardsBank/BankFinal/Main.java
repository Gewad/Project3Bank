package BankFinal;

import java.awt.EventQueue;

import BankPanels.*;
import ServerCommunication.AccountData;
import ServerCommunication.ServerCommunication;

public class Main {
	private final static String ATM = "BG100001";
	private int currentScreen = 0;
	Background background;
	ServerCommunication server;
	
	int currentAction = 0;
	String currentAmount = "";
	String currentTarget = "";

	String UID;
	String pin;
	AccountData data;
	String currentRekening = "";
	int currentRekeningIndex = 0;

	int pinCharCount;
	String[] stars = { "", "*", "**", "***", "****" };
	String[] numericChars = { "#", "*", "A", "B", "C", "D" };

	public static void main(String[] args) {
		Main machine = new Main();
	}

	public Main() {
		// 192.168.178.38
		server = new ServerCommunication("145.24.235.86", 8989, ATM);		// Paul
		//server = new ServerCommunication("145.24.222.245", 8989, ATM);	// Server

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
							} else {
								pinCharCount = 0;
								changeScreen(1);
							}
						} else if (input.contains("KEY:")) {
							input = input.substring(input.indexOf(":") + 1);

							if (currentScreen == 1) {
								if (isNumeric(input)) {
									pin += input;
									pinCharCount++;

									changeScreen(1);

								} else if (input.equals("#")) {
									int serverResponse = server.checkPin(UID, pin);

									if (serverResponse != 0) {
										background.showMessage(serverResponse);
										pin = "";
										pinCharCount = 0;
										changeScreen(0);
									} else {
										data = server.getData(UID, pin);
										currentRekening = data.getRekening(currentRekeningIndex);
										changeScreen(2);
									}
								} else if (input.equals("*")) {
									pin = "";
									pinCharCount = 0;
									changeScreen(1);
								} else if (input.equals("D")) {
									changeScreen(0);
								}
							} else if (currentScreen == 4) {
								if(input.equals("1")) {
									currentAction = 1;
									changeScreen(7);
								} else if (input.equals("2")) {
									currentAction = 2;
									changeScreen(7);
								} else {
									changeScreen(getNextScreen(input));
								}
							} else if (currentScreen == 7) {
								if(isNumeric(input)) {
									currentAmount += Integer.parseInt(input);
									changeScreen(7);
								} else if (input.equals("*") && currentAmount.length() > 0) {
									currentAmount = currentAmount.substring(0, (currentAmount.length()-1));
									changeScreen(7);
								} else if (input.equals("#")) {
									if(currentAction == 1) {
										int message = server.withdraw(UID, pin, currentRekening, currentAmount);
										background.showMessage(message);
										currentRekening = "";
										currentAmount = "";
										changeScreen(4);
									} else {
										changeScreen(8);
									}
								} else {
									changeScreen(getNextScreen(input));
								}
							} else if (currentScreen == 8) {
								if(isNumeric(input)) {
									currentTarget += Integer.parseInt(input);
									changeScreen(8);
								} else if (input.equals("*") && currentTarget.length() > 0) {
									currentTarget = currentTarget.substring(0, (currentTarget.length()-1));
									changeScreen(8);
								} else if (input.equals("#")) {
										int message = server.withdraw(UID, pin, currentRekening, currentAmount);
										background.showMessage(message);
										currentRekening = "";
										currentAmount = "";
										changeScreen(4);
								} else {
									changeScreen(getNextScreen(input));
								}
							} else if (currentScreen == 9) {
								if (isNumeric(input)) {
									int newRekeningIndex = Integer.parseInt(input) - 1;
									if (newRekeningIndex < data.getRekeningAmount() && newRekeningIndex > -1) {
										currentRekening = data.getRekening(newRekeningIndex);
										currentRekeningIndex = newRekeningIndex;
									}

									changeScreen(7);
								} else {
									changeScreen(getNextScreen(input));
								}
							} else {
								changeScreen(getNextScreen(input));
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
			data = null;
			UID = null;
			pin = null;
			currentRekening = null;
			this.background.setPanel(new login1());
			break;
		case 1:
			this.background.setPanel(new login2(stars[pinCharCount]));
			break;
		case 2:
			this.background.setPanel(new homeMenu(data.getName() + " " + data.getSurname()));
			break;
		case 3:
			this.background.setPanel(new saldo(currentRekening, server.getSaldo(UID, pin, currentRekening)));
			break;
		case 4:
			this.background.setPanel(new geldMenu());
			break;
		case 5:
			this.background.setPanel(new geldOpnemen());
			break;
		case 6:
			this.background.setPanel(new geldOverboeken());
			break;
		case 7:
			this.background.setPanel(new selectAmount(currentAmount));
			break;
		case 8:
			this.background.setPanel(new selectTarget(currentTarget));
			break;
		case 9:
			this.background.setPanel(new rekeningKiezen(data.getRekeningAmount(), data.getRekeningen(), currentRekeningIndex));
			break;
		case 10:
			this.background.setPanel(new pinWijzigen());
			break;
		}
	}

	private boolean isNumeric(String input) {

		for (int i = 0; i < numericChars.length; i++) {
			if (input.equals(numericChars[i])) {
				return false;
			}
		}

		return true;
	}

	private int getNextScreen(String key) {
		return this.background.getNextScreen(key, currentScreen);
	}
}