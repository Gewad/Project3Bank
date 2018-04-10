package Bank;

public class ServerCommunication {
	
	String testPin = "2817";
	
	public ServerCommunication(){
		System.out.println("Connection established");
	}
	
	public boolean checkData(String UID, String pin){
		if (UID == "E4 E4 06 85" && pin == testPin){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void updatePassword(String newPin){
		if(newPin != testPin){
			testPin = newPin;
		}
	}
}
