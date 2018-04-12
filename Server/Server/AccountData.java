package Server;
import java.io.Serializable;
public class AccountData implements Serializable{
	private static final long serialVersionUID = 1183645979221194797L;
	private int isValid;	// Zo weet de client of de pin is geaccepteerd
	private String customer_id;	// Dan weet de client met wie hij bezig is
	private String pas_id;		// Lekker handig ofzo
	private String name;		// voor client functionaliteit
	private String surName;		// voor client functionaliteit
	private int rekeningAmount;	// Hiermee kiest de client een rekening
	private String[] rekeningen;// Hiermee kiest de client een rekening
	
	public AccountData(int valid, String customer, String pas, String nm, String srnm, int rekeningamt, String[] rekeningArray) {
		isValid = valid;
		customer_id = customer;
		pas_id = pas;
		name = nm;
		surName = srnm;
		rekeningAmount = rekeningamt;
		rekeningen = rekeningArray;
	}
	
	public int getValid() {
		return this.isValid;
	}
	
	public String getCustomerID() {
		return customer_id;
	}
	
	public String getCardID() {
		return pas_id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getSurname() {
		return surName;
	}
	
	public int getRekeningAmount() {
		return rekeningAmount;
	}
	
	public String getRekening(int i) {
		return rekeningen[i];
	}
	
	public String[] toStringarray() {
		String[] temp = new String[6];
		temp[0] = Integer.toString(isValid);
		temp[1] = customer_id;
		temp[2] = pas_id;
		temp[3] = name;
		temp[4] = surName;
		temp[5] = Integer.toString(rekeningAmount);
		
		return temp;
	}
	
	public String[] getRekeningen() {
		return rekeningen;
	}
}
