package Server;
import java.io.Serializable;
public class AccountData implements Serializable{
	private static final long serialVersionUID = 1183645979221194797L;
	private boolean isValid;	// Zo weet de client of de pin is geaccepteerd
	private String customer_id;	// Dan weet de client met wie hij bezig is
	private String pas_id;		// Lekker handig ofzo
	private String name;		// voor client functionaliteit
	private String surName;		// voor client functionaliteit
	private int rekeningAmount;	// Hiermee kiest de client een rekening
	private String[] rekeningen;// Hiermee kiest de client een rekening
	
	public AccountData(boolean valid, String customer, String pas, String nm, String srnm, int rekeningamt, String[] rekeningArray) {
		isValid = valid;
		customer_id = customer;
		pas_id = pas;
		name = nm;
		surName = srnm;
		rekeningAmount = rekeningamt;
		rekeningen = rekeningArray;
	}
	
	public boolean getValid() {
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
}
