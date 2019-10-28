package ca.queensu.cisc327;

import java.io.*;
import java.util.*;

/* 
 * Quinterac Banking System Front End
 * 
 * CISC327 Fall 2019 Assignment 2
 * 
 * Usage: java Quinterac "InputFileName.txt" "OutputFileName.txt"
 * 
 * Compiled to Java SE 10 
 */

public class Quinterac 
{
	
	public final static ArrayList<String> commands = new ArrayList<String>(
	Arrays.asList("login", "logout", "createacc", "deleteacc", "deposit", "withdraw", "transfer"));
	
	// Table for States of the Session 
	//  0 = not logged in,
	//  1 = logged in ATM,
	//  2 = logged in agent,
	// -1 = error
	
	public static void main ( String args[] ) 
	{
		FrontendObject session = null;
		
		session = new FrontendObject(args[0], args[1]); // create a new instance of a FrontendObject
		
		if(session == null) {
			return;
		}

		Scanner consoleIn = new Scanner(System.in);
		
		while (session.getState() >= 0) // when in a logged in state
		{
			
			System.out.println("Please enter a transaction: ");
			
			String input = null;
			
			input = consoleIn.nextLine().toLowerCase(); // get input from the user to collect what transaction they would like to do
			
			Transaction transaction = null;
			
			if(input.equals("--quit")) {
				terminateProgram(session, consoleIn);
				return;
			}
			
			switch (commands.indexOf(input))  // get index of the transaction from the ArrayList at the top of this class
			{
			case 0: // Login Transaction
				try 
				{
					
					transaction = new TransactionLogin(consoleIn, session.getState()); // create a Login Object
					transaction.process();	// Process the login Transaction
					session.setState(transaction.getState()); // set the state of the Frontend Object to the expected outcome after process was run
					
				} 
				catch (OutOfOrderException e) 
				{
					System.out.println(e.getMessage());
					session.setState(0);
					break;
				}
				
				break;
			       
			

			case 1: // Logout Transaction
				try 
				{
					
					transaction = new TransactionLogout(session.getState()); // create a Logout Object
					transaction.process(); // Process the Logout transaction
					session.setState(transaction.getState()); // set the state of the Frontend Object to the expected outcome after process was run
					session.stackPush(((WritableTransaction)transaction).getTransactionSummary()); // push the output string to the stack to print to the Output File
					
					try
					{
						session.createSummary(); // create the Output file for all the transactions in the session
					} 
					catch (IOException e) 
					{
						System.out.println("Error encountered while writing to file" + args[1]);
					}
					
					System.out.println("Session completed, output printed to file.");
					
				} 
				catch (OutOfOrderException e) 
				{
					System.out.println(e.getMessage());
					break;
				}
			    break;
			
			
			
			case 2: // Create Account Transaction
				try 
				{
					
					transaction = new TransactionCreateAcct(consoleIn, session); // create a Create Account Object
					transaction.process(); // Process the Create Account Transaction
					session.stackPush(((WritableTransaction)transaction).getTransactionSummary()); // push the output string to the stack to print to the Output File
					
				} 
				catch (OutOfOrderException e) 
				{
					System.out.println(e.getMessage());
					break;
				}
				break;
				
			case 3: // Delete Account Transaction
				try 
				{
					
					transaction = new TransactionDeleteAcct(consoleIn, session); // create a Delete Account Object
					transaction.process(); // Process the Delete Account Transaction
					session.stackPush(((WritableTransaction)transaction).getTransactionSummary()); // push the output string to the stack to print to the Output File
					
				} 
				catch (OutOfOrderException e) 
				{
					System.out.println(e.getMessage());
					break;
				}
				break;
				
			case 4: // Deposit Transaction
				try
			     {
					
			    	 transaction = new TransactionDeposit(consoleIn, session); // Create a Deposit Object
			    	 transaction.process(); // Process the Deposit Transaction
			    	 session.stackPush(((WritableTransaction)transaction).getTransactionSummary()); // push the output string to the stack to print to the Output File
						
                } 
			     catch (OutOfOrderException e) 
			     {
                    System.out.println(e.getMessage());
                    break;
                }
				break;
				
			case 5: // Withdraw Transaction
			     try
			     {
			    	 
			    	 transaction = new TransactionWithdraw(consoleIn, session); // Create a Withdraw Object
			    	 transaction.process(); // Process the Withdraw Transaction
					 session.stackPush(((WritableTransaction)transaction).getTransactionSummary()); // push the output string to the stack to print to the Output File
				     
                 } 
			     catch (OutOfOrderException e) 
			     {
                     System.out.println(e.getMessage());
                     break;
                 }
			     break;
			     
			case 6: // Transfer Transaction
			    try
			    {
			    	
			    	transaction = new TransactionTransfer(consoleIn, session); // Create a Transfer Object
			    	transaction.process(); // Process the Transfer Transaction
					session.stackPush(((WritableTransaction)transaction).getTransactionSummary()); // push the output string to the stack to print to the Output File
					
                } 
			    catch (OutOfOrderException e) 
			    {
                    System.out.println(e.getMessage());
                    break;
                }
			    break;
			    
			default: // Unknown Transaction
				System.out.println("Unrecognized transaction: " + input);
				continue; // get user to re-enter transaction they would like to perform
			}
		}
	}
	
	public static void terminateProgram(FrontendObject session, Scanner scan) {
		
		scan.close();
		
		if(session.getState() != 0) {
			try
			{
				session.createSummary(); // create the Output file for all the transactions in the session
			} 
			catch (IOException e) 
			{
				System.out.println("Error writing to file...");
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


