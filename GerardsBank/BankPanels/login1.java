package BankPanels;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class login1 extends JPanel {

	/**
	 * Create the panel.
	 */
	public login1() {
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
	}

}
