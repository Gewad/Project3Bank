package BankPanels;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class selectTarget extends JPanel {

	public selectTarget(String input) {
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
		txtMid.setText("Bepaal naar welke rekening u geld wilt overmaken.");
		txtMid.setEditable(false);
		this.add(txtMid);
		txtMid.setVisible(true);
		
		String machinedInput = "BG" + input.substring(0);
		if(input.length() > 2) {
			System.out.println("test 1");
			machinedInput = "BG" + input.substring(0, 2);
			machinedInput += "-" + input.substring(2);
		}
		if(input.length() > 5) {
			System.out.println("test 2");
			machinedInput = "BG" + input.substring(0, 2);
			machinedInput += "-" + input.substring(2, 5);
			machinedInput += "-" + input.substring(5);
		}
		
		JTextField textField = new JTextField();
		textField.setBounds(690, 440, 240, 50);
		textField.setFont(new Font("Dialog", Font.PLAIN, 30));
		textField.setText(machinedInput);
		this.add(textField);
		textField.setColumns(4);
		textField.setVisible(true);
		textField.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		
		JButton btnOneBack = new JButton("* - Een terug");
		btnOneBack.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnOneBack.setBounds(530, 503, 250, 90);
		this.add(btnOneBack);
		btnOneBack.setVisible(true);
		
		JButton btnConfirm = new JButton("# - Bevestig");
		btnConfirm.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnConfirm.setBounds(840, 503, 250, 90);
		this.add(btnConfirm);
		btnConfirm.setVisible(true);
		
		
	}

}
