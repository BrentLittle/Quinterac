/* 
 * Quinterac Banking System Front End
 * CISC327 Fall 2019 Assignment 2
 * Usage: java quinterac "filename1.txt" "filename2.txt"
 * 
 * Compiled to Java SE 10 
 */

import java.io.*;
import java.util.*;

public class Quinterac {
	
	public final static ArrayList<String> commands = new ArrayList<String>(
			Arrays.asList(
		"login", "logout", "createacc", "deleteacc", 
		"deposit", "withdraw", "transfer"));
	
	@SuppressWarnings("unused")
	public static int main(String args[]) {
		FrontendObject session = null;
		try {
			session = new FrontendObject(args[0], args[1]);
		} catch (Exception e) {
			//will have to catch each exception individually, probably
			return 1;
		}
		BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
		while (session.getState() > 0) {
			if (session.getState() == 3) {
				try{
					session.createSummary();
				} catch (IOException e) {
					System.out.printf("Error encountered while writing to file" + args[1]);
					return 1;
				}
				System.out.printf("Session completed, output printed to " + args[1]);
			} //ie, if "logout" has been encountered
			System.out.printf("Please enter a transaction: ");
			String transaction = null;
			try {
				transaction = consoleIn.readLine().toLowerCase();
			} catch (IOException e) {
				// shouldnt get here....
			}
			AbsTransactionObject currentT = null;
			switch (commands.indexOf(transaction)) {
			case 0:
				try {
					currentT = new LoginTObject(session.getState());
				} catch (OutOfOrderException e) {
					System.out.printf(e.getMessage());
					break;
				}
				currentT.process();
				session.setState(currentT.getState());
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			default: 
				System.out.printf("Unrecognized transaction: " + transaction);
				continue;
			}
//			TransactionObject currentT = null; 
//			try {
//				currentT = new TransactionObject(transaction, session.getState());
//			} catch (InvalidStringException e) {
//				System.out.printf(e.getMessage());
//				continue;
//			}
//
//			try {
//				session.stackPush(currentT.process()); 
//			} catch (OutOfOrderException e) {
//				System.out.printf(e.getMessage());
//			}
		}
		return 0;
	}
	
	//This function should probably be in an interface of some sort
	//along with the rest of input validation
	public boolean validString(String s) {
		for (char c : s.toCharArray()) {
			if (!Character.isLetterOrDigit(c)) {
				return false;
			}
		}
		return true;
	}

}
