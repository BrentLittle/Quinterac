/* 
 * Quinterac Banking System Front End
 * 
 * CISC327 Fall 2019 Assignment 2
 * 
 * Usage: java Quinterac "InputFileName.txt" "OutputFileName.txt"
 * 
 * Compiled to Java SE 10 
 */

import java.io.*;
import java.util.*;

public class Quinterac 
{
	
	public final static ArrayList<String> commands = new ArrayList<String>(
	Arrays.asList("login", "logout", "createacc", "deleteacc", "deposit", "withdraw", "transfer"));
	
	// Table for States of the Session 
	//  0 = not logged in,
	//  1 = logged in ATM,
	//  2 = logged in agent,
	//  3 = logged out,
	// -1 = error
	
	public static void main ( String args[] ) 
	{
		FrontendObject session = null;
		
		try 
		{
			session = new FrontendObject(args[0], args[1]); // create a new instance of a FrontendObject
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}

		BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
		
		while (session.getState() >= 0) // when in a logged in state
		{
			if (session.getState() == 3) // when in a logged out state
			{
				try
				{
					session.createSummary(); // create the Output file for all the transactions in the session
				} 
				catch (IOException e) 
				{
					System.out.println("Error encountered while writing to file" + args[1]);
				}
				
				System.out.println("Session completed, output printed to " + args[1]);
			}
			
			System.out.printf("Please enter a transaction: ");
			
			String transaction = null;
			try 
			{
				transaction = consoleIn.readLine().toLowerCase(); // get input from the user to collect what transaction they would like to do
			} catch (IOException e) {
				// shouldn't get here....
			}
			AbsTransactionObject currentT = null;
			
			switch (commands.indexOf(transaction))  // get index of the transaction from the ArrayList at the top of this class
			{
			case 0: // Login Transaction
				try 
				{
					currentT = new LoginTObject(session.getState()); // create a Login Object
				} 
				catch (OutOfOrderException e) 
				{
					System.out.printf(e.getMessage());
					break;
				}
				currentT.process();	// Process the login Transaction
				session.setState(currentT.getState()); // set the state of the Frontend Object to the expected outcome after process was run
				break;
			       
			

			case 1: // Logout Transaction
				try 
				{
					currentT = new LogoutTObject(session.getState()); // create a Logout Object
				} 
				catch (OutOfOrderException e) 
				{
					System.out.printf(e.getMessage());
					break;
				}
				currentT.process(); // Process the Logout transaction
				session.setState(currentT.getState()); // set the state of the Frontend Object to the expected outcome after process was run
				session.stackPush(currentT.outString()); // push the output string to the stack to print to the Output File
			    break;
			
			
			
			case 2: // Create Account Transaction
				try 
				{
					currentT = new CreateTObject(session.getState(), session.getAccountList()); // create a Create Account Object
				} 
				catch (OutOfOrderException e) 
				{
					System.out.printf(e.getMessage());
					break;
				}
				currentT.process(); // Process the Create Account Transaction
				session.stackPush(currentT.outString()); // push the output string to the stack to print to the Output File
				break;									 // We do not need to worry about the state change since we have already 
														 // logged in and it can only change when we logout
			
			
			
			
			case 3: // Delete Account Transaction
				try 
				{
					currentT = new DeleteAcctTObject(session.getState(), session.getAccountList()); // create a Delete Account Object
				} 
				catch (OutOfOrderException e) 
				{
					System.out.printf(e.getMessage());
					break;
				}
				currentT.process(); // Process the Delete Account Transaction
				session.stackPush(currentT.outString()); // push the output string to the stack to print to the Output File
				break;  								 // We do not need to worry about the state change since we have already 
														 // logged in and it can only change when we logout
				
			
			
			case 4: // Deposit Transaction
				try
			     {
			    	 currentT = new DepositTObject(session.getState(),session.getAccountList(), session); // Create a Deposit Object
                } 
			     catch (OutOfOrderException e) 
			     {
                    System.out.println(e.getMessage());
                    break;
                }
				currentT.process(); // Process the Deposit Transaction
				session.stackPush(currentT.outString()); // push the output string to the stack to print to the Output File
				break;  								 // We do not need to worry about the state change since we have already 
														 // logged in and it can only change when we logout
			
			
			
			case 5: // Withdraw Transaction
			     try
			     {
			    	 currentT = new WithdrawTObject(session.getState(), consoleIn, session); // Create a Withdraw Object
                 } 
			     catch (OutOfOrderException e) 
			     {
                     System.out.println(e.getMessage());
                     break;
                 }
			     currentT.process(); // Process the Withdraw Transaction
				 session.stackPush(currentT.outString()); // push the output string to the stack to print to the Output File
			     break;  								  // We do not need to worry about the state change since we have already 
														  // logged in and it can only change when we logout
			
			
                 
			case 6: // Transfer Transaction
			    try
			    {
			    	currentT = new TransferTObject(session.getState(), consoleIn, session); // Create a Transfer Object
                } 
			    catch (OutOfOrderException e) 
			    {
                    System.out.println(e.getMessage());
                    break;
                }
			    currentT.process(); // Process the Transfer Transaction
				session.stackPush(currentT.outString()); // push the output string to the stack to print to the Output File
				break;  								 // We do not need to worry about the state change since we have already 
														 // logged in and it can only change when we logout
			
			
			default: // Unknown Transaction
				System.out.println("Unrecognized transaction: " + transaction);
				continue; // get user to re-enter transaction they would like to perform
			}
		}
	}
}
//This function should probably be in an interface of some sort
//along with the rest of input validation
//	public boolean validString(String s) {
//		for (char c : s.toCharArray()) {
//			if (!Character.isLetterOrDigit(c)) {
//				return false;
//			}
//		}
//		return true;
//	}


