package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class serverMain {
	
	public static void main(String [ ] args){
		serverMain server = new serverMain();
	}
	
	public serverMain() {
		System.out.println("I'm doing something");
		try {
		ServerSocket test =  new ServerSocket(6789, 100);
		System.out.println("Loading done, waiting for possible clients.");
		while(true) {
			Socket socket = test.accept();
			System.out.println("New Connection Established: "+socket.getInetAddress());
			new serverThread(socket).start();
		}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
