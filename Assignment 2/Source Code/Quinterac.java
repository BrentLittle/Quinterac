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
	public static void main(String args[]) 
	{
		FrontendObject session = null;
		
		try 
		{
			session = new FrontendObject(args[0], args[1]);
		} catch (Exception e) 
		{
			System.out.println(e.getMessage());
			//will have to catch each exception individually, probably
			//return 1;
		}
		//System.out.println("FEO created");
		BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
		
		while (session.getState() >= 0) 
		{
			if (session.getState() == 3) 
			{
				try
				{
					session.createSummary();
				} 
				catch (IOException e) 
				{
					System.out.println("Error encountered while writing to file" + args[1]);
					//return 1;
				}
				
				System.out.println("Session completed, output printed to " + args[1]);
			} //ie, if "logout" has been encountered
			
			System.out.printf("Please enter a transaction: ");
			String transaction = null;
			try 
			{
				transaction = consoleIn.readLine().toLowerCase();
			} catch (IOException e) {
				// shouldn't get here....
			}
			AbsTransactionObject currentT = null;
			
			switch (commands.indexOf(transaction)) 
			{
			
			case 0: // Login //Good
				try 
				{
					currentT = new LoginTObject(session.getState());
				} catch (OutOfOrderException e) {
					System.out.printf(e.getMessage());
					break;
				}
				currentT.process();
				session.setState(currentT.getState());
				break;
			       
			

			case 1: // Logout
				try 
				{
					currentT = new LogoutTObject(session.getState());
				} catch (OutOfOrderException e) {
					System.out.printf(e.getMessage());
					break;
				}
				currentT.process();
				session.setState(currentT.getState());
				session.stackPush(currentT.outString());
			    break;
			
			
			
			case 2: // createAcc
				try 
				{
					currentT = new CreateTObject(session.getState(), session.getAccountList());
				} 
				catch (OutOfOrderException e) 
				{
					System.out.printf(e.getMessage());
					break;
				}
				currentT.process();
				session.stackPush(currentT.outString());
				break;
			
			
			
			case 3: // deleteAcct
				try 
				{
					currentT = new DeleteAcctTObject(session.getState(), session.getAccountList());
				} 
				catch (OutOfOrderException e) 
				{
					System.out.printf(e.getMessage());
					break;
				}
				currentT.process();
				session.stackPush(currentT.outString());
				break;
			
			
			case 4: // deposit
				try
			     {
			    	 currentT = new DepositTObject(session.getState(),session.getAccountList(), session);
                } 
			     catch (OutOfOrderException e) 
			     {
                    System.out.println(e.getMessage());
                    break;
                }
                currentT.process();
                session.stackPush(currentT.outString());
			    break;
			
			
			
			case 5: // withdraw
			     try
			     {
			    	 currentT = new WithdrawTObject(session.getState(), consoleIn, session);
                 } 
			     catch (OutOfOrderException e) 
			     {
                     System.out.println(e.getMessage());
                     break;
                 }
                 currentT.process();
                 session.stackPush(currentT.outString());

                 break;
			
			
                 
			case 6: // transfer
			    try
			    {
			    	currentT = new TransferTObject(session.getState(), consoleIn, session);
                } 
			    catch (OutOfOrderException e) 
			    {
                    System.out.println(e.getMessage());
                    break;
                }
                currentT.process();
                session.stackPush(currentT.outString());
                break;
			
			
			
			default: 
				System.out.println("Unrecognized transaction: " + transaction);
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
		//return 0;
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
