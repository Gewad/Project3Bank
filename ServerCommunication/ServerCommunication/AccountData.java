package ServerCommunication;
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
	
	public AccountData(String[] temp, String[] rek) {
		isValid = Integer.parseInt(temp[0]);
		customer_id = temp[1];
		pas_id = temp[2];
		name = temp[3];
		surName = temp[4];
		rekeningAmount = Integer.parseInt(temp[5]);
		rekeningen = rek;
	}
	
	public void input(String temp[]) {
		rekeningen = temp;
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
}
