package BankPanels;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class login2 extends JPanel {
	
	String pin;
	int pinCharCount = 0;

	/**
	 * Create the panel.
	 */
	public login2(String input) {
		this.setBounds(150, 150, 1620, 930);
		this.setBackground(Color.WHITE);
		setLayout(null);
		
		final JLabel txtTitle = new JLabel("Welkom bij Gerard's bank.");
		txtTitle.setBounds(610, 10, 400, 37);
		txtTitle.setFont(new Font("Tahoma", Font.BOLD, 30));
		txtTitle.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		this.add(txtTitle);
		
		JTextArea txtMid = new JTextArea();
		txtMid.setBounds(500, 250, 620, 179);
		txtMid.setFont(new Font("Tahoma", Font.PLAIN, 30));
		txtMid.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		txtMid.setText("Voer uw pincode in.");
		txtMid.setEditable(false);
		this.add(txtMid);
		txtMid.setVisible(true);
		
		JTextField textField = new JTextField();
		textField.setBounds(690, 440, 240, 50);
		textField.setFont(new Font("Dialog", Font.PLAIN, 30));
		textField.setText(input);
		this.add(textField);
		textField.setColumns(4);
		textField.setVisible(true);
		textField.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		
	}
	
	public int setPin(String input) {
		pinCharCount += 1;
		pin += input;
		
		return pinCharCount;
	}
	
	public String getPin() {
		return pin;
	}

}
