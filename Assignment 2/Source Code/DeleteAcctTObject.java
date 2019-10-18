import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class DeleteAcctTObject extends AbsTransactionObject
{
	private int outState;
	private int state;
	private ArrayList<Integer> accountsList;
	
	String accountNumber = null;
	String accountName = null;
	
	public DeleteAcctTObject(int s, ArrayList<Integer> al) throws OutOfOrderException
	{
		accountsList = al;
		state = s;
		
		if (s != 2) throw new OutOfOrderException("Cannot Delete Account when not logged in as Machine \n");
		{
			outState = -1;
		}
	}
	
	
	public void process()
	{
		BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
		
		
		while (true) { // collect a valid account Number from the User
			System.out.printf("Please enter valid Account Number to Delete from System: ");
			try 
			{
				accountNumber = consoleIn.readLine();
				if(checkvalidAccountNumber(accountNumber))
				{
					
					if(checkDuplicate(Integer.parseInt(accountNumber)))
					{
						System.out.println("Account Number Entered is valid...");
					}
					else
					{
						System.out.println("Account Number Entered is valid but not in the Valid Accounts List...");
						continue;
					}
					break;
				}
				else
				{
					System.out.println("Account Number " + accountNumber + " is Not Valid");
					continue;
				}
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}			
		}
		// collect a correctly formatted account name
		while(true)
		{
			System.out.printf("Please enter Account Name to Delete from System: ");
			try 
			{
				accountName = consoleIn.readLine();
				if(checkValidAccountName(accountName))
				{
					System.out.println("Account Name Entered is Valid...");
					break;
				}
				else
				{
					System.out.println("Account Name: '" + accountName + "' is Not Valid");
					continue;
				}
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		// at this stage we have a valid accountNumber and accountName variables
		System.out.println("Account "+accountNumber+" owned by "+accountName+" will be deleted at the end of the session. The account has been locked.");
		deleteAccountFromList(accountNumber);
		
	}// end of process method
	
	public int getState()
	{
		return outState;
	}
	
	public String outString()
	{
		return "DEL "+accountNumber+" 000 0000000 "+ accountName+ "\n";
	}
	
	
	public void deleteAccountFromList(String account)
	{
		for (int i = 0; i < accountsList.size(); i++) {
			if (accountsList.get(i) == Integer.parseInt(account)) {
				accountsList.remove(i);
				break;
			}
		}
	}
	
	
	public boolean checkDuplicate(int n) {
		for (int i = 0; i < accountsList.size(); i++) {
			if (accountsList.get(i) == n) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkvalidAccountNumber(String input)
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
