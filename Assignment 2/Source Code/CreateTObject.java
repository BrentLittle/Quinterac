import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;



public class CreateTObject extends AbsTransactionObject {
	
	
	String accName;
	String accNum;
	int state;
	ArrayList <Integer> accList;
	String outString;
	
	public CreateTObject(int state , ArrayList <Integer> accList) throws OutOfOrderException
	{
		if(state == 0) 
		{
			throw new OutOfOrderException("Can't create account before login\n");
		}
		
		if(state != 2) 
		{
			throw new OutOfOrderException("Can't create account outside of agent login\n");
		}
		
		this.state = state;
		this.accList = accList;
	}
	
	// at this point, all permissions are assumed.
	public void process()
	{
		BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));

		while(true) // while loop to get valid account number that does not exist
		{
			System.out.println("Enter Account Number");
			try 
			{
				accNum = consoleIn.readLine(); // get input from User on what Account Number they want to use
			}
			catch(IOException e) {
				//shouldn't get here
			}

			if (!checkValidAccountNumber(accNum)) // check if it is a valid account number
			{
				System.out.println("Account Number is Not Valid");
				continue;
			}

			if(accList.contains(Integer.parseInt(accNum))) // check if it does not already exist
			{
				System.out.println("Account Number Already in Use");
				continue;
			}
			else // we have a valid number
			{
				break;
			}
		}


		while(true) // while loop to get valid account name
		{
			System.out.println("Enter Account Name");
			try 
			{
				accName = consoleIn.readLine(); // get input from user on their name to go with the account
			}
			catch(IOException e) 
			{
				//shouldn't get here
			}
			if(!checkValidAccountName(accName)) // check if the name is properly formatted
			{
				System.out.println("Account Name is  Not Valid");
				continue;
			}
			else
			{
				break; // we have a valid account name
			}
		}
		System.out.println("Account Number "+accNum+" for "+accName+" has been created");
	}

			
		public int getState() 
		{
			return 0;
		}
		
		public String outString()
		{
			return "NEW "+accNum+" 000 0000000 "+accName+"\n";
		}
		
		public boolean checkValidAccountNumber(String input) // will return true if account number is valid
		{
			if(input.length() != 7) // is not exactly 7 digits in account number
	    	{
	    		return false;
	    	}
	    	else if ( !(input.matches("[0-9]+")) ) // account number contains non-numeric characters
	    	{
	    		return false;
	    	}
	    	else if (input.startsWith("0") && !(input.equals("0000000"))) // starts with a 0 but is not the ending line of 0000000
	    	{
	    		return false;
	    	}
			return true;
		}
		
		public boolean checkValidAccountName(String input) // will return true if account name is validly formatted
		{
			if(input.length() < 3 || input.length() > 30) // account name bust be between 3 and 30 characters inclusive
			{
				return false;
			}
			else if(input.startsWith(" ") || input.endsWith(" ")) // account name must not start or end with a space
			{
				return false;
			}
			return true;
		}
}


			
			
		
	

