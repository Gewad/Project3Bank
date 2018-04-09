package ServerCommunication;

import ServerCommunication.ServerCommunication;

public class testMain {
	static ServerCommunication server;
	private static boolean gotAccount = false;
	
	public static void main(String[] args) {
		server = new ServerCommunication("localhost", 6789);
		if(server.isConnected()){
			server.sayHi();
			int tmpINT = server.requestObject();
			if(tmpINT == 0) {
				gotAccount = true;
			} else {
				gotAccount = false;
			}
			
		}
	}
	
	
	private void zegHallo() {
		server.sayHi();
	}
	
	private int checkData(String UID, String Pin) {
		return 0;
	}
}
