package BankFinal;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JButton;

public class Background extends JFrame {

	private JPanel contentPane;
	private JPanel panel;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public Background() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0, 0, 1920, 1080);
		this.setUndecorated(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(new JLabel(new ImageIcon("D:\\Data\\GitWorkspace\\School\\Project3Bank\\GerardsBank\\External\\test3.png")));
		contentPane.setLayout(null);
		
		
		panel = new JPanel();
		panel.setBounds(150, 150, 1620, 930);
		contentPane.add(panel);
		
		panel.setVisible(true);
	}
	
	public void setPanel(JPanel x) {
		this.setPanelVisible(false);
		this.panel = x;
		this.setPanelVisible(true);
	}
	
	public void setPanelVisible(boolean x) {
		if(x) {
			this.panel.setVisible(true);
		} else {
			this.panel.setVisible(false);
		}
	}
	
	public void showMessage(int x) {
		Messenger message = new Messenger();
		Messenger.showMessageDialog(this, selectMessage(x));
	}
	
	public String selectMessage(int x) {
		switch(x) {
		case 0: return "Handeling Succesvol Afgerond.";
		case 1: return "Pas is nit herkend.";
		case 2: return "Pas is geblokkeerd.";
		case 3: return "Te weinig saldo op uw rekening.";
		case 4: return "Rekening niet herkend.";
		case 5: return "Ingevoerde pincode is ongeldig.";
		case 6: return "";
		case 7: return "Ingevoerde pincode is incorrect.";
		default : return "";
		}
		
	}
}
