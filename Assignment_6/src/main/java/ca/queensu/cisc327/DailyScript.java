package main.java.ca.queensu.cisc327;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import main.java.ca.queensu.cisc327.Quinterac;
import main.java.ca.queensu.cisc327.QuinteracBackend;
import java.util.*;

public class DailyScript {

	public static void main(String args[]) {
		
		//Front end args are "valid accounts file" and "transaction file"
	    String[] frontEndArgs = {"C:\\Users\\Denny\\eclipse-workspace\\Quinterac\\src\\main\\java\\ca\\queensu\\cisc327\\ValidAccountsFile.txt"
	    		, "C:\\Users\\Denny\\eclipse-workspace\\Quinterac\\src\\main\\java\\ca\\queensu\\cisc327\\"};
	    //Back end args are "transaction summary file directory" and "master accounts file"
	    String[] backEndArgs = {"C:\\Users\\Denny\\eclipse-workspace\\Quinterac\\src\\main\\java\\ca\\queensu\\cisc327",
	    		"C:\\Users\\Denny\\eclipse-workspace\\Quinterac\\src\\main\\java\\ca\\queensu\\cisc327\\"};
		
	    List<String> terminal_input = getInputCases(args);
	    
	    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	    
	    PrintStream defaultOut = System.out;
	    
	    //setup user input
        String userInput = String.join(System.lineSeparator(), terminal_input);
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);
       // System.setOut(new PrintStream(outContent));
	    
		Quinterac.main(frontEndArgs);
		QuinteracBackend.main(backEndArgs);
		
		//System.setOut(defaultOut);
		
		if(args.length != 0) {
			System.out.println("Daily run " + args[0] + " complete...");
			return;
		}
		
		System.out.println("Daily run complete...");
		
	}
	
	private static List<String> getInputCases(String[] iCase){
		
		List<String> out = Arrays.asList("--quit");
		
		if(iCase.length == 0) {
			return out;
		}
		
		switch(iCase[0]) {
		
			case "0":
				
				out = Arrays.asList("login", "agent", "createacc", "1111111", "Bob", "logout",
						"login", "agent", "createacc", "2222222", "Rob", "logout",
						"login", "agent", "createacc", "3333333", "Cob", "logout", "--quit");
				
				break;
				
			case "1":
				
				out = Arrays.asList("login", "machine", "deposit", "1111111", "2000", "logout",
						"login", "machine", "deposit", "2222222", "5000", "logout",
						"login", "machine", "transfer", "3333333", "2222222", "500", "logout", "--quit");
				
				break;
				
			case "2":
				
				out = Arrays.asList("login", "machine", "withdraw", "3333333", "500", "logout",
						"login", "machine", "deposit", "1111111", "1000", "logout",
						"login", "machine", "deposit", "2222222", "500", "logout","--quit");
				
				break;
				
			case "3":
				
				out = Arrays.asList("login", "machine", "transfer", "2222222", "1111111", "1000", "logout",
						"login", "machine", "deposit", "2222222", "500", "logout",
						"login", "agent", "deleteacc", "3333333", "Cob", "logout", "--quit");
				
				break;
				
			case "4":
				
				out = Arrays.asList("login", "agent", "deposit", "1111111", "500000", "logout",
						"login", "machine", "transfer", "2222222", "1111111", "5000", "logout",
						"login", "machine", "withdraw", "2222222", "1000", "logout", "--quit");
				
				break;
		}
		
		return out;
	}
}
