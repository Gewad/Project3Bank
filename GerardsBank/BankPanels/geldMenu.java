package BankPanels;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;

public class geldMenu extends JPanel {

	public geldMenu() {
		this.setBounds(150, 150, 1620, 930);
		this.setBackground(Color.WHITE);
		setLayout(null);
		
		JButton btnLeftF = new JButton("1 - Geld opnemen");
		btnLeftF.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnLeftF.setBounds(15, 250, 450, 90);
		btnLeftF.setVisible(false);
		this.add(btnLeftF);
		btnLeftF.setVisible(true);
		
		JButton btnLeftS = new JButton("2 - Geld overboeken");
		btnLeftS.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnLeftS.setBounds(15, 400, 450, 90);
		btnLeftS.setVisible(false);
		this.add(btnLeftS);
		btnLeftS.setVisible(true);
		
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
		btnEnd.setVisible(true);
	}

}
