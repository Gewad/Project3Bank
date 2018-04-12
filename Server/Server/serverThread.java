package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class serverThread extends Thread {
	Socket socket;
	BufferedReader input;
	PrintStream output;
	ObjectInputStream objectInput;
	ObjectOutputStream objectOutput;
	String attemptUID = "";
	int attemptCount = 0;

	final int UNKNOWN_REQUEST = 100;
	final int WRONG_NUM_ARGS = 101;
	private SQLReader SQL = new SQLReader();

	public serverThread(Socket inputSocket) {
		this.socket = inputSocket;
		try {
			this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.output = new PrintStream(socket.getOutputStream());
			// this.objectInput = new ObjectInputStream(socket.getInputStream());
			this.objectOutput = new ObjectOutputStream(socket.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Server Thread for " + socket.getInetAddress() + " done loading.");
	}

	public void run() {
		String request;
		while (true) {
			// Receive request string
			request = waitForInput();

			// Operation
			String[] parts = request.split(" ");
			String operation = parts[0];

			String response;

			System.out.println("Client request: "+request);

			switch(operation) {
				case "CHECK_UID": 
				response = checkUID(parts);
				break;

				case "CHECK_DATA":
				response = checkData(parts);
				break;

				case "GET_SALDO":
				response = getSaldo(parts);
				break;

				case "WITHDRAW":
				response = withdraw(parts);
				break;

				case "TRANSFER":
				response = transfer(parts);
				break;

				case "CHANGE_PIN":
				response = changePin(parts);
				break;

				default:
				response = makeErrorResponse(UNKNOWN_REQUEST);
				break;
			}

			sendToClient(response);
		}
	}

	/** CHECK_UID uid */
	public String checkUID(String[] arguments) {
		
		if(arguments.length != 2){
			return makeErrorResponse(WRONG_NUM_ARGS);
		}

		int result = SQL.checkUID(arguments[1]);

		if(result == 0) {
			return "OK 0";
		} else {
			return makeErrorResponse(result);
		}
	}

	/** CHECK_DATA uid pin */
	public String checkData(String[] arguments) {

		if(arguments.length != 3){
			return makeErrorResponse(WRONG_NUM_ARGS);
		}

		if(SQL.checkPin(arguments[1], arguments[2]) != 0) {
			return makeErrorResponse(SQLReader.INCORRECT_PIN);
		}

		AccountData ad = SQL.getData(arguments[1]);

		if(ad == null) {
			return makeErrorResponse(-10);
		} else {
			return "OK 0 " + ad.toString();
		}
	}

	/** GET_SALDO uid pin */
	public String getSaldo(String[] arguments) {

		if(arguments.length != 3){
			return makeErrorResponse(WRONG_NUM_ARGS);
		}

		if(SQL.checkPin(arguments[1], arguments[2]) != 0) {
			return makeErrorResponse(SQL.INCORRECT_PIN);
		}

		int result = SQL.getSaldo(arguments[1]);

		if(result >= 0) {
			return "OK " + result; // FIXME hacky
		} else {
			return makeErrorResponse(result);
		}
	}

	/** WITHDRAW uid pin amount */
	public String withdraw(String[] arguments) {

		if(arguments.length != 4){
			return makeErrorResponse(WRONG_NUM_ARGS);
		}

		if(SQL.checkPin(arguments[1], arguments[2]) != 0) {
			return "ERROR pin incorrect";
		}

		int result = SQL.withdraw(arguments[1], Integer.parseInt(arguments[3]));

		if(result == SQL.OK) {
			return "OK 0";
		} else {
			return makeErrorResponse(result);
		}
	}

	/** TRANSFER uid pin target_uid amount */
	public String transfer(String[] arguments) {

		if(arguments.length != 5){
			return makeErrorResponse(WRONG_NUM_ARGS);
		}

		if(SQL.checkPin(arguments[1], arguments[2]) != 0) {
			return makeErrorResponse(SQL.INCORRECT_PIN);
		}

		int result = SQL.transfer(arguments[1], arguments[3], Integer.parseInt(arguments[4]));

		if(result == SQL.OK) {
			return "OK 0";
		} else {
			return makeErrorResponse(result);
		}
	}

	/** CHANGE_PIN uid pin pin2 */
	public String changePin(String[] arguments) {

		if(arguments.length != 4){
			return makeErrorResponse(WRONG_NUM_ARGS);
		}

		if(SQL.checkPin(arguments[1], arguments[2]) != 0) {
			return "ERROR pin incorrect";
		}

		int result = SQL.changePin(arguments[1], arguments[3]);

		if(result == SQL.OK) {
			return "OK 0";
		} else {
			return makeErrorResponse(result);
		}
	}

	public String waitForInput() {
		System.out.println("Waiting for input.");
		String message = "";
		while (true) {
			try {
				message = input.readLine();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (!message.equals("")) {
				System.out.println("Client: " + socket.getInetAddress() + " said: " + message);
				return message;
			}
		}
	}
	
	private void sendToClient(String message) {
		System.out.println("Sending message to client: " +message);
		output.println(message);
	}
 
	private String makeErrorResponse(int errorCode) {
		switch(errorCode) {
			case WRONG_NUM_ARGS: return "ERROR " + errorCode + " wrong number of args";
			case UNKNOWN_REQUEST: return "ERROR " + errorCode + " unknown request";
			case SQLReader.SERVER_ERROR: return "ERROR "+errorCode+" server error";
			case SQLReader.UNKNOWN_CARD: return "ERROR "+errorCode+" unknown card";
			case SQLReader.BLOCKED: return "ERROR "+errorCode+" card blocked";
			case SQLReader.LOW_BALANCE: return "ERROR "+errorCode+" low balance";
			case SQLReader.UNKNOWN_ACCOUNT: return "ERROR "+errorCode+" unknown account";
			case SQLReader.INVALID_PIN: return "ERROR "+errorCode+" invalid pin";
			case SQLReader.INCORRECT_PIN: return "ERROR "+errorCode+" incorrect pin";
			default: return "ERROR 0 unknown error";
		}
	}

}
