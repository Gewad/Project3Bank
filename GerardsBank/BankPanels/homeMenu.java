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

	public homeMenu(String nameCombined) {
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
		txtMid.setText("Welkom " + nameCombined + ".");
		txtMid.setEditable(false);
		this.add(txtMid);
		txtMid.setVisible(true);
		
		JButton btnLeftF = new JButton("A - Saldo");
		btnLeftF.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnLeftF.setBounds(15, 250, 450, 90);
		btnLeftF.setVisible(false);
		this.add(btnLeftF);
		btnLeftF.setVisible(true);
		
		JButton btnLeftS = new JButton("B - Geld opnemen/overboeken");
		btnLeftS.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnLeftS.setBounds(15, 400, 450, 90);
		btnLeftS.setVisible(false);
		this.add(btnLeftS);
		btnLeftS.setVisible(true);
		
		JButton btnLeftT = new JButton("C - Rekening kiezen");
		btnLeftT.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnLeftT.setBounds(15, 550, 450, 90);
		btnLeftT.setVisible(false);
		this.add(btnLeftT);
		btnLeftT.setVisible(true);
		
		JButton btnRightF = new JButton("D - Wijziging doorgeven");
		btnRightF.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnRightF.setBounds(1155, 250, 450, 90);
		btnRightF.setVisible(false);
		this.add(btnRightF);
		btnRightF.setVisible(true);
		
		JButton btnBack = new JButton("Terug.");
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnBack.setBounds(1155, 734, 450, 90);
		btnBack.setVisible(false);
		this.add(btnBack);
		btnBack.setVisible(false);
		
		JButton btnEnd = new JButton("*/# - Beëindig sessie.");
		btnEnd.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnEnd.setBounds(1155, 837, 450, 90);
		btnEnd.setVisible(false);
		this.add(btnEnd);
		btnEnd.setVisible(true);
		
		/*
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
