/* 
 * Quinterac Banking System Front End
 * CISC327 Fall 2019 Assignment 2
 * Usage: java quinterac filename1 filename2
 * 
 * Compiled to Java SE 10 
 */

import java.io.*;
import java.util.*;

public class Quinterac {
	
	public static int main(String args[]) {
		BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
		FrontendObject session = null;
		try {
			session = new FrontendObject(args[0], args[1]);
		} catch (Exception e) {
			//will have to catch each exception individually, probably
		}
		while (session.getState() > 0) {
			System.out.printf("Please enter a transaction: ");
			String transaction = null;
			try {
				transaction = consoleIn.readLine().toLowerCase();
			} catch (IOException e) {
				// shouldnt get here....
			}
			String[] parsedTrans = transaction.split(" ");
			switch (parsedTrans[0]) {
			case "login" :
				//
				break;
			case "logout" :
				//
				break;
			case "creatacc" :
				//
				break;
			case "deleteacc" :
				//
				break;
			case "deposit" :
				//
				break;
			case "withdraw" :
				//
				break;
			case "transfer" :
				//
				break;
			default:
				//unrecognized transaction goes here
				continue;
			}
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
