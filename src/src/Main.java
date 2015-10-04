package src;

/**
 * @author kushdua
 * date September 19, 2015
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
	/******** FINAL COMMAND VARIABLES *********/
	private final static String COMMAND_NAME_SET 					= "SET";
	private final static String COMMAND_NAME_GET 					= "GET";
	private final static String COMMAND_NAME_UNSET 					= "UNSET";
	private final static String COMMAND_NAME_NUMEQVAL 				= "NUMEQUALTO";
	private final static String COMMAND_BEGIN_TRANSACTION 			= "BEGIN";
	private final static String COMMAND_ROLLBACK_TRANSACTION 		= "ROLLBACK";
	private final static String COMMAND_COMMIT_TRANSACTION 			= "COMMIT";
	private final static String COMMAND_END 						= "END";
	
	/************** Transaction Instance Variable **************/
	private static TransactionHandler mTransactionHandler;
		
	/**
	 * Main function to handle input from either console or file
	 * @param args
	 * @throws IOException
	 */
	public static void main (String[] args) throws IOException {
		mTransactionHandler = new TransactionHandler();
		
		//the file is being passes
		if(args.length == 1) {
	    	ArrayList<String> commands = null;
	    	if(!args[0].endsWith(".txt")) {
	    		commands = FileHandler.readFile(args[0]+".txt");
			} else {
				commands = FileHandler.readFile(args[0]);
			}
	    	for(String command : commands) {
	    		commandAnalyser(command);
	    	}
		} else { //need to interactively run the program
			BufferedReader br = new BufferedReader(new
                    InputStreamReader(System.in));
			String userInput;
			do {
				userInput = (String) br.readLine();
				commandAnalyser(userInput);
			} while(!userInput.equals(COMMAND_END));
		}
	}
	
	/**
	 * command parser to call the respective command and print the output on the console
	 * @param command
	 */
	public static void commandAnalyser(String command) {
		String[] commandParts = command.split(" ");
		String name = null;
		String value = null;
		String cmd = commandParts[0];
		
		//input validation
		if(commandParts.length > 1) {
			name = commandParts[1];
		}
		if(commandParts.length > 2) {
			value = commandParts[2];
		} 
		
		switch(cmd) {
			case COMMAND_NAME_SET:
				if(!(value == null || name == null))
					mTransactionHandler.set(name, value);
				else 
					System.out.println("INVALID COMMAND");
				break;
			case COMMAND_NAME_GET:
				String getval = mTransactionHandler.get(name);
				if(getval == null) 
					System.out.println("NULL");
				else 
					System.out.println(getval);
				break;
			case COMMAND_NAME_UNSET:
				mTransactionHandler.unset(name);
				break;
			case COMMAND_NAME_NUMEQVAL:
				System.out.println(mTransactionHandler.numEqualValue(name));
				break;
			case COMMAND_BEGIN_TRANSACTION:
				mTransactionHandler.begin();
				break;
			case COMMAND_ROLLBACK_TRANSACTION:
				mTransactionHandler.rollBack();
				break;
			case COMMAND_COMMIT_TRANSACTION:
				mTransactionHandler.commit();
				break;
			case COMMAND_END:
				break;
			default:
				System.out.println("INVALID COMMAND");
				break;
		}
	}

}
