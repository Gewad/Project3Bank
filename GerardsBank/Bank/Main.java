package Bank;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main {
	ServerCommunication server = new ServerCommunication();
	HardwareControl arduino = new HardwareControl();
	
	String pin;
	String UID = "0";
	
	private JFrame frame;
	JTextArea txtMid;
	JButton btnLeftF;
	JButton btnLeftS;
	JButton btnLeftT;
	JButton btnRightF;
	JButton btnRightS;
	JButton btnRightT;
	JButton btnBack;
	JButton btnEnd;
	int currentScreen = 0;
	ActionListener leftFListener = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			if(currentScreen == 1){
				changeScreen(2);
			}
			else if(currentScreen == 5){
				changeScreen(6);
			}
		}
	};
	ActionListener leftSListener = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			if(currentScreen == 1){
				changeScreen(3);
			}
			else if(currentScreen == 5){
				changeScreen(7);
			}
		}
	};
	ActionListener leftTListener = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			if(currentScreen == 1){
				changeScreen(4);
			}
			else if(currentScreen == 5){
				changeScreen(8);
			}
		}
	};
	ActionListener rightFListener = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			changeScreen(5);
		}
	};
	
	private JTextField textField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
					System.out.println("Waddup");
					window.changeScreen(2);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Main() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(150, 120, 1620, 960);
		frame.setLayout(null);
		frame.setUndecorated(true);
		
		final JLabel txtTitle = new JLabel("Welkom bij Gerard's bank.");
		txtTitle.setFont(new Font("Tahoma", Font.BOLD, 30));
		txtTitle.setBounds(610, 90, 400, 122);
		txtTitle.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		frame.getContentPane().add(txtTitle);
		txtTitle.setVisible(true);
		
		txtMid = new JTextArea();
		txtMid.setFont(new Font("Tahoma", Font.PLAIN, 30));
		txtMid.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		txtMid.setText("Houdt uw kaart tegen de kaartlezer.");
		txtMid.setBounds(500, 250, 620, 179);
		txtMid.setEditable(false);
		frame.getContentPane().add(txtMid);
		txtMid.setVisible(true);
		
		btnLeftF = new JButton("");
		btnLeftF.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnLeftF.setBounds(15, 250, 450, 90);
		btnLeftF.addActionListener(leftFListener);
		btnLeftF.setVisible(false);
		frame.getContentPane().add(btnLeftF);
		
		btnLeftS = new JButton("");
		btnLeftS.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnLeftS.setBounds(15, 400, 450, 90);
		btnLeftS.addActionListener(leftSListener);
		btnLeftS.setVisible(false);
		frame.getContentPane().add(btnLeftS);
		
		btnLeftT = new JButton("");
		btnLeftT.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnLeftT.setBounds(15, 550, 450, 90);
		btnLeftT.addActionListener(leftTListener);
		btnLeftT.setVisible(false);
		frame.getContentPane().add(btnLeftT);
		
		btnRightF = new JButton("");
		btnRightF.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnRightF.setBounds(1155, 250, 450, 90);
		btnRightF.addActionListener(rightFListener);
		btnRightF.setVisible(false);
		frame.getContentPane().add(btnRightF);
		
		btnRightS = new JButton("");
		btnRightS.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnRightS.setBounds(1155, 400, 450, 90);
		btnRightS.setVisible(false);
		frame.getContentPane().add(btnRightS);
		
		btnRightT = new JButton("");
		btnRightT.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnRightT.setBounds(1155, 550, 450, 90);
		btnRightT.setVisible(false);
		frame.getContentPane().add(btnRightT);
		
		btnBack = new JButton("Terug.");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				determineBack();
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnBack.setBounds(1155, 734, 450, 90);
		btnBack.setVisible(false);
		frame.getContentPane().add(btnBack);
		
		btnEnd = new JButton("Beëindig sessie.");
		btnEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UID = "0";
				changeScreen(0);
			}
		});
		btnEnd.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnEnd.setBounds(1155, 837, 450, 90);
		btnEnd.setVisible(false);
		frame.getContentPane().add(btnEnd);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()== KeyEvent.VK_ENTER && currentScreen == 9){
		            String checkPin = textField.getText();
		            server.checkData(UID, checkPin);
				}
			}
		});
		textField.setFont(new Font("Dialog", Font.PLAIN, 30));
		textField.setBounds(690, 440, 240, 50);
		frame.getContentPane().add(textField);
		textField.setColumns(4);
		textField.setVisible(false);
		frame.getContentPane().setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		
		JButton btnExit = new JButton("exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnExit.setBounds(1507, 12, 98, 26);
		frame.getContentPane().add(btnExit);
		
	}
	
	public void changeScreen(int t){
		currentScreen = t;
		System.out.println("Screen changed to: " + t);
		switch(t){
			case 0:
				txtMid.setText("Houdt uw kaart tegen de kaartlezer.");
				txtMid.setVisible(true);
				btnLeftF.setText("");
				btnLeftF.setVisible(false);
				btnLeftS.setText("");
				btnLeftS.setVisible(false);
				btnLeftT.setText("");
				btnLeftT.setVisible(false);
				btnRightF.setText("");
				btnRightF.setVisible(false);
				btnRightS.setText("");
				btnRightS.setVisible(false);
				btnRightT.setText("");
				btnRightT.setVisible(false);
				btnBack.setVisible(false);
				btnEnd.setVisible(false);
				textField.setVisible(false);
				while(true){
					if(UID.equals("0")){
						UID = arduino.getUID();
					}
					else{
						changeScreen(9);
						break;
					}
					try{Thread.sleep(1000);}catch(Exception e){}
				}
				break;
			case 1:
				txtMid.setText("Welkom, Gerard van Walraven.\r\n Druk om een van de knoppen om van de functie gebruik te maken");
				txtMid.setVisible(true);
				btnLeftF.setText("Saldo");
				btnLeftF.setVisible(true);
				btnLeftS.setText("Geld opnemen");
				btnLeftS.setVisible(true);
				btnLeftT.setText("Geld overboeken");
				btnLeftT.setVisible(true);
				btnRightF.setText("Wijziging doorgeven");
				btnRightF.setVisible(true);
				btnRightS.setText("");
				btnRightS.setVisible(false);
				btnRightT.setText("");
				btnRightT.setVisible(false);
				btnBack.setVisible(false);
				btnEnd.setVisible(true);
				textField.setVisible(false);
				break;
			case 2:
				txtMid.setText("Uw huidige saldo bedraagt: $291,21");
				txtMid.setVisible(true);
				btnLeftF.setText("");
				btnLeftF.setVisible(false);
				btnLeftS.setText("");
				btnLeftS.setVisible(false);
				btnLeftT.setText("");
				btnLeftT.setVisible(false);
				btnRightF.setText("");
				btnRightF.setVisible(false);
				btnRightS.setText("");
				btnRightS.setVisible(false);
				btnRightT.setText("");
				btnRightT.setVisible(false);
				btnBack.setVisible(true);
				btnEnd.setVisible(true);
				textField.setVisible(false);
				break;
			case 3:
				txtMid.setText("Geld opnemen. \r\n Deze functionaliteit word nog aan gewerkt.");
				txtMid.setVisible(true);
				btnLeftF.setText("");
				btnLeftF.setVisible(false);
				btnLeftS.setText("");
				btnLeftS.setVisible(false);
				btnLeftT.setText("");
				btnLeftT.setVisible(false);
				btnRightF.setText("");
				btnRightF.setVisible(false);
				btnRightS.setText("");
				btnRightS.setVisible(false);
				btnRightT.setText("");
				btnRightT.setVisible(false);
				btnBack.setVisible(true);
				btnEnd.setVisible(true);
				textField.setVisible(false);
				break;
			case 4:
				txtMid.setText("Geld overboeken. \r\n Deze functionaliteit word nog aan gewerkt.");
				txtMid.setVisible(true);
				btnLeftF.setText("");
				btnLeftF.setVisible(false);
				btnLeftS.setText("");
				btnLeftS.setVisible(false);
				btnLeftT.setText("");
				btnLeftT.setVisible(false);
				btnRightF.setText("");
				btnRightF.setVisible(false);
				btnRightS.setText("");
				btnRightS.setVisible(false);
				btnRightT.setText("");
				btnRightT.setVisible(false);
				btnBack.setVisible(true);
				btnEnd.setVisible(true);
				textField.setVisible(false);
				break;
			case 5:
				txtMid.setText("Wat wilt u wijzigen.");
				txtMid.setVisible(true);
				btnLeftF.setText("Pincode");
				btnLeftF.setVisible(true);
				btnLeftS.setText("Adres");
				btnLeftS.setVisible(true);
				btnLeftT.setText("Naam");
				btnLeftT.setVisible(true);
				btnRightF.setText("");
				btnRightF.setVisible(false);
				btnRightS.setText("");
				btnRightS.setVisible(false);
				btnRightT.setText("");
				btnRightT.setVisible(false);
				btnBack.setVisible(true);
				btnEnd.setVisible(true);
				textField.setVisible(false);
				break;
			case 6:
				txtMid.setText("Pincode wijzigen.");
				txtMid.setVisible(true);
				btnLeftF.setText("");
				btnLeftF.setVisible(false);
				btnLeftS.setText("");
				btnLeftS.setVisible(false);
				btnLeftT.setText("");
				btnLeftT.setVisible(false);
				btnRightF.setText("");
				btnRightF.setVisible(false);
				btnRightS.setText("");
				btnRightS.setVisible(false);
				btnRightT.setText("");
				btnRightT.setVisible(false);
				btnBack.setVisible(true);
				btnEnd.setVisible(true);
				textField.setVisible(true);
				break;
			case 7:
				txtMid.setText("Adres wijzigen. \r\n Deze functionaliteit word nog aan gewerkt.");
				txtMid.setVisible(true);
				btnLeftF.setText("");
				btnLeftF.setVisible(false);
				btnLeftS.setText("");
				btnLeftS.setVisible(false);
				btnLeftT.setText("");
				btnLeftT.setVisible(false);
				btnRightF.setText("");
				btnRightF.setVisible(false);
				btnRightS.setText("");
				btnRightS.setVisible(false);
				btnRightT.setText("");
				btnRightT.setVisible(false);
				btnBack.setVisible(true);
				btnEnd.setVisible(true);
				textField.setVisible(false);
				break;
			case 8:
				txtMid.setText("Naam wijzigen. \r\n Deze functionaliteit word nog aan gewerkt.");
				txtMid.setVisible(true);
				btnLeftF.setText("");
				btnLeftF.setVisible(false);
				btnLeftS.setText("");
				btnLeftS.setVisible(false);
				btnLeftT.setText("");
				btnLeftT.setVisible(false);
				btnRightF.setText("");
				btnRightF.setVisible(false);
				btnRightS.setText("");
				btnRightS.setVisible(false);
				btnRightT.setText("");
				btnRightT.setVisible(false);
				btnBack.setVisible(true);
				btnEnd.setVisible(true);
				textField.setVisible(false);
				break;
			case 9:
				txtMid.setText("Voer uw pincode in.");
				txtMid.setVisible(true);
				btnLeftF.setText("");
				btnLeftF.setVisible(false);
				btnLeftS.setText("");
				btnLeftS.setVisible(false);
				btnLeftT.setText("");
				btnLeftT.setVisible(false);
				btnRightF.setText("");
				btnRightF.setVisible(false);
				btnRightS.setText("");
				btnRightS.setVisible(false);
				btnRightT.setText("");
				btnRightT.setVisible(false);
				btnBack.setVisible(true);
				btnEnd.setVisible(true);
				textField.setVisible(true);
				textField.setEditable(true);
				break;
		}
		frame.setVisible(false);
		frame.setVisible(true);
	}
	
	public void determineBack(){
		switch(currentScreen){
			case 0:
				break;
			case 1:
				break;
			case 2:
				changeScreen(1);
				break;
			case 3:
				changeScreen(1);
				break;
			case 4:
				changeScreen(1);
				break;
			case 5:
				changeScreen(1);
				break;
			case 6:
				changeScreen(5);
				break;
			case 7:
				changeScreen(5);
				break;
			case 8:
				changeScreen(5);
				break;
			case 9:
				changeScreen(0);
				break;
		}
	}
}