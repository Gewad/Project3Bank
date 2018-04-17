package BankPanels;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class selectAmount extends JPanel {

	public selectAmount(String input) {
		this.setBounds(150, 150, 1620, 930);
		this.setBackground(Color.WHITE);
		setLayout(null);
		
		final JLabel txtTitle = new JLabel("Welkom bij Gerard's bank.");
		txtTitle.setBounds(610, 10, 400, 37);
		txtTitle.setFont(new Font("Tahoma", Font.BOLD, 30));
		txtTitle.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		this.add(txtTitle);
		
		JTextArea txtMid = new JTextArea();
		txtMid.setBounds(500, 250, 771, 179);
		txtMid.setFont(new Font("Tahoma", Font.PLAIN, 30));
		txtMid.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		txtMid.setText("Bepaal welk bedrag u wilt overmaken/overboeken.");
		txtMid.setEditable(false);
		this.add(txtMid);
		txtMid.setVisible(true);
		
		JTextField textField = new JTextField();
		textField.setBounds(560, 440, 500, 50);
		textField.setFont(new Font("Dialog", Font.PLAIN, 30));
		textField.setText(input);
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
		
		JButton btnBack = new JButton("C - Terug.");
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnBack.setBounds(1155, 734, 450, 90);
		btnBack.setVisible(false);
		this.add(btnBack);
		btnBack.setVisible(true);
		
		JButton btnEnd = new JButton("D - Beëindig sessie.");
		btnEnd.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnEnd.setBounds(1155, 837, 450, 90);
		btnEnd.setVisible(false);
		this.add(btnEnd);
	}
}
