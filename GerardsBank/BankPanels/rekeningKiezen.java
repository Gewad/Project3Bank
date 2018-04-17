package BankPanels;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class rekeningKiezen extends JPanel {
	String linesCombined;
	String[] lines;
	
	
	public rekeningKiezen(int rekeningAmount, String[] rekeningen, int currentRekeningIndex) {
		this.setBounds(150, 150, 1620, 930);
		this.setBackground(Color.WHITE);
		setLayout(null);
		
		final JLabel txtTitle = new JLabel("Welkom bij Gerard's bank.");
		txtTitle.setBounds(610, 10, 400, 200);
		txtTitle.setFont(new Font("Tahoma", Font.BOLD, 30));
		txtTitle.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		this.add(txtTitle);
		
		linesCombined = "";
		lines = new String[rekeningAmount];
		for(int i = 0; i < rekeningAmount; i++) {
			if(i != currentRekeningIndex) {
				lines[i] = (i+1)+ " - " + rekeningen[i] + "\n\r";
				linesCombined += lines[i];
			} else {
				lines[i] = (i+1) + " - " + rekeningen[i] + " (chosen) \n\r";
				linesCombined += lines[i];
			}
			
		}
		JTextArea txtMid = new JTextArea();
		txtMid.setBounds(500, 250, 900, 809);
		txtMid.setFont(new Font("Tahoma", Font.PLAIN, 30));
		txtMid.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		txtMid.setEditable(false);
		txtMid.setText("Kies uw rekening door op het nummer te \n\r drukken dat er voor staat. \n\r \n\r" + linesCombined);
		this.add(txtMid);
		txtMid.setVisible(true);
		
		JButton btnBack = new JButton("* - Terug.");
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnBack.setBounds(1155, 734, 450, 90);
		btnBack.setVisible(false);
		this.add(btnBack);
		btnBack.setVisible(true);
		
		JButton btnEnd = new JButton("# - Beëindig sessie.");
		btnEnd.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnEnd.setBounds(1155, 837, 450, 90);
		btnEnd.setVisible(false);
		this.add(btnEnd);
		btnEnd.setVisible(true);
	}

}
