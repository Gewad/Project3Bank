package Server;

public class AccountData {
	private boolean isValid;
	private String customer_id;
	private String pas_id;
	private String name;
	private String surName;
	private int rekeningAmount;
	private String[] rekeningen;
	
	public AccountData(boolean valid, String customer, String pas, String nm, String srnm, int rekeningamt, String[] rekeningArray) {
		isValid = valid;
		customer_id = customer;
		pas_id = pas;
		name = nm;
		surName = srnm;
		rekeningAmount = rekeningamt;
		rekeningen = rekeningArray;
	}
}
