package BankPanels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class homeMenu extends JPanel {

	public homeMenu() {
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
		txtMid.setText("Houdt uw kaart tegen de kaartlezer.");
		txtMid.setEditable(false);
		this.add(txtMid);
		txtMid.setVisible(true);
		/*
		JButton btnLeftF = new JButton("");
		btnLeftF.setBounds(939, 23, 33, 9);
		btnLeftF.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnLeftF.setVisible(false);
		this.add(btnLeftF);
		
		JButton btnLeftS = new JButton("");
		btnLeftS.setBounds(977, 23, 33, 9);
		btnLeftS.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnLeftS.setVisible(false);
		this.add(btnLeftS);
		
		JButton btnLeftT = new JButton("");
		btnLeftT.setBounds(1015, 23, 33, 9);
		btnLeftT.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnLeftT.setVisible(false);
		this.add(btnLeftT);
		
		JButton btnRightF = new JButton("");
		btnRightF.setBounds(1053, 23, 33, 9);
		btnRightF.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnRightF.setVisible(false);
		this.add(btnRightF);
		
		JButton btnRightS = new JButton("");
		btnRightS.setBounds(1091, 23, 33, 9);
		btnRightS.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnRightS.setVisible(false);
		this.add(btnRightS);
		
		JButton btnRightT = new JButton("");
		btnRightT.setBounds(1129, 23, 33, 9);
		btnRightT.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnRightT.setVisible(false);
		this.add(btnRightT);
		
		JButton btnBack = new JButton("Terug.");
		btnBack.setBounds(1167, 5, 129, 45);
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnBack.setVisible(false);
		this.add(btnBack);
		
		JButton btnEnd = new JButton("Beëindig sessie.");
		btnEnd.setBounds(1301, 5, 273, 45);
		btnEnd.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnEnd.setVisible(false);
		this.add(btnEnd);
		
		final JTextField textField = new JTextField();
		textField.setBounds(757, 55, 106, 45);
		textField.setFont(new Font("Dialog", Font.PLAIN, 30));
		this.add(textField);
		textField.setColumns(4);
		textField.setVisible(false);
		textField.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		*/
	}

}
