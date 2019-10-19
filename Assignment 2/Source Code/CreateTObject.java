import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;



public class CreateTObject extends TransactionObject {
	
	public CreateTObject(BufferedReader in, FrontendObject feObject) throws OutOfOrderException{
		super(in, feObject);
		if(state == 0 || state == 3) {
			throw new OutOfOrderException("Can't create account before login\n");
		}
		if(state != 2) {
			throw new OutOfOrderException("Can't create account outside of agent login\n");
		}
	}
	
		// at this point, all permissions are assumed.
	public void process()
	{
		String accName;
		String accNum;
		
		while(true)
		{
			System.out.println("Enter Account Number");
			try 
			{
				accNum = consoleIn.readLine();
			}
			catch(IOException e) {
				System.out.println("Unknown IOException...");
                continue;
			}
			
			if (!checkValidAccountNumber(accNum))
			{
				System.out.println("Account Number is Not Valid");
				continue;
			}
			
			if(accNums.contains(Integer.parseInt(accNum)))
			{
				System.out.println("Account Number Already in Use");
				continue;
			}
			
			else
			{
				break;
			}
		}
		
		
		while(true)
		{
			System.out.println("Enter Account Name");
			try 
			{
				accName = consoleIn.readLine();
			}
			catch(IOException e) 
			{
				System.out.println("Unknown IOException...");
                continue;
			}
			if(!checkValidAccountName(accName))
			{
				System.out.println("Account Name is  Not Valid");
				continue;
			}
			else
			{
				break;
			}
		}
		
		System.out.println("Account Number "+accNum+" for "+accName+" has been created");
		
		outString = "NEW "
	}
			
	public int getState() {
		return 0;
	}
	
	public String outString() {
		return outString;
		
	}
	
	public boolean checkValidAccountNumber(String input)
	{
		if(input.length() != 7) // less than 7 digits in account number
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
	
	public boolean checkValidAccountName(String input)
	{
		if(input.length() < 3 || input.length() > 30) // account name bust be between 3 and 30 characters inclusive
		{
			return false;
		}
		else if(input.startsWith(" ") || input.endsWith(" "))
		{
			return false;
		}
		return true;
	}
}


			
			
		
	

