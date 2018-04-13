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
import javax.swing.JDialog;

import BankPanels.login1;

public class Background extends JFrame {

	private JPanel panel = new login1();
	private JPanel contentPane;

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
		this.getContentPane().setLayout(null);
		this.getContentPane().add(panel);
	}
	
	public void setPanel(JPanel x) {
		this.setPanelVisible(false);
		this.panel = x;
		this.getContentPane().add(panel);
		this.setPanelVisible(true);
	}
	
	public void setPanelVisible(boolean x) {
		this.panel.setVisible(x);
	}
	
	public JPanel getPanel() {
		return panel;
	}
	
	public void showMessage(int x) {
		JOptionPane error = selectMessage(x);
	    final JDialog errorDialog = error.createDialog("Information");
	    errorDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	    new Thread(new Runnable() {
	      public void run() {
	        try {Thread.sleep(3000);} catch (InterruptedException e) {}
	        errorDialog.setVisible(false);
	      }
	    }).start();
	    errorDialog.setVisible(true);
	}
	
	public JOptionPane selectMessage(int x) {
		String Test = "2917";
		switch(x) {
		case 0: return new JOptionPane("Handeling Succesvol Afgerond.", JOptionPane.INFORMATION_MESSAGE);
		case 1: return new JOptionPane("Pas is niet herkend.", JOptionPane.WARNING_MESSAGE);
		case 2: return new JOptionPane("Pas is geblokkeerd.", JOptionPane.WARNING_MESSAGE);
		case 3: return new JOptionPane("Te weinig saldo op uw rekening.", JOptionPane.WARNING_MESSAGE);
		case 4: return new JOptionPane("Rekening niet herkend.", JOptionPane.WARNING_MESSAGE);
		case 5: return new JOptionPane("Ingevoerde pincode is ongeldig.", JOptionPane.WARNING_MESSAGE);
		case 6: return new JOptionPane(Test.hashCode(), JOptionPane.WARNING_MESSAGE);
		case 7: return new JOptionPane("Ingevoerde pincode is incorrect.", JOptionPane.WARNING_MESSAGE);
		default : return new JOptionPane("Er is iets fout gegaan.", JOptionPane.WARNING_MESSAGE);
		}
		
	}
}
