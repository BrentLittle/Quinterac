import java.util.*;
import java.io.*;

public class DepositTObject extends AbsTransactionObject{
	
	private int outState;
	private String outString;
	
	private int depositTo;
	private int depositAmt;
	
	private int inState;
	private ArrayList <Integer> accNums;
	
	public DepositTObject (int state, ArrayList<Integer> accN) throws OutOfOrderException {
		if (state < 1 || state > 2) throw new OutOfOrderException("Must login before deposit.");
		accNums = accN;
		inState = state;
		depositTo = 0;
		depositAmt= 0;
		outState = -1;
	}

	@Override
	public void process() 
	
	{
		BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
		while (outState < 0) 
		{
			System.out.printf("Please enter destination account number: ");
			String tempStr = null;
			
			try 
			{
				tempStr = consoleIn.readLine();
			} catch (IOException e) {
				// shouldnt get here....
			}
			if (tempStr.length() != 7) {
				System.out.println("1");
				System.out.println("Invalid account number.");
				continue;
			}
			if (tempStr.charAt(0) == '0') {
				System.out.println("2");
				System.out.println("Invalid account number.");
				continue;
			}
			depositTo = Integer.parseInt(tempStr);
			
			for(int i = 0; i< accNums.size();i++)
			{
				System.out.println(accNums.get(i));
			}
			
			if(accNums.contains(depositTo))
			{
				System.out.println("HERE");
			}

			System.out.printf("Please enter deposit amount: ");
			try 
			{
				tempStr = consoleIn.readLine().toLowerCase();
			} catch (IOException e) {
				// shouldnt get here....
			}
			try {
				depositAmt = Integer.parseInt(tempStr);
			} catch (NumberFormatException e) {
				System.out.println("Invalid account number.");
				continue;
			}
			if (inState == 1 && depositAmt > 200000) {
				System.out.println("Invalid deposit amount for ATM mode.");
				continue;
			}
			if (depositAmt > 99999999) {
				System.out.println("Deposit amount exceeds maximum allowed.");
				continue;
			}
			outState = 2;
		}
	}

	@Override
	public int getState() {
		return outState;
	}

	@Override
	public String outString() {
		return "DEP " + depositTo + " " + depositAmt + " 0000000 ***";
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
