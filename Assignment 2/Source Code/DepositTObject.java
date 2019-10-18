import java.util.*;
import java.io.*;

public class DepositTObject extends AbsTransactionObject
{
	
	private int outState;
	private String outString;
	
	private int depositTo;
	private int depositAmt;
	private String tempStr;
	
	private int inState;
	private ArrayList <Integer> accNums;
	private Object[] transactions;
	
	public DepositTObject (int state, ArrayList<Integer> accN, FrontendObject fe) throws OutOfOrderException 
	{
		if (state < 1 || state > 2) throw new OutOfOrderException("Must login before deposit.");
		accNums = accN;
		inState = state;
		depositTo = 0;
		depositAmt= 0;
		outState = -1;
		transactions = fe.getTransactions().toArray();
	}

	@Override
	public void process() 
	{
		BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
		
		while(true) // account number
		{
			System.out.printf("Please enter destination account number: ");
			String tempStr = null;
			
			try 
			{
				tempStr = consoleIn.readLine();
			} 
			catch (IOException e) 
			{
				// shouldnt get here....
			}
			if (!checkvalidAccountNumber(tempStr) )
			{
				System.out.println("Invalid account number.");
				continue;
			}
			
			depositTo = Integer.parseInt(tempStr);
			
			if(!accNums.contains(depositTo))
			{
				System.out.println("Account Number Entered is valid but not in Accounts List.");
				continue;
			}
			break;
		}
		
		while(true)
		{
			System.out.printf("Please enter deposit amount: ");
			try 
			{
				tempStr = consoleIn.readLine();
			} catch (IOException e) {
				// shouldnt get here....
			}
			int validAmount = validateMonetaryValue(tempStr, depositTo);
			if (validAmount == -1)
			{
				continue;
			}
			break;
		}
	}

	@Override
	public int getState() 
	{
		return outState;
	}

	@Override
	public String outString() 
	{
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
	
	private int validateMonetaryValue(String check, int accFrom)
	{
        
        int maxValue = 99999999; //default maxValue to that of agent
        int checkNum;
        final int maxSessionWithdraw = 500000;
        
        if(outState == 1) maxValue = 200000; //change maxValue if in machine mode
        
        //check to see if we can parse the input to an int
        try{
            //no error will occur if input is indeed an int, we can simply proceed
            checkNum = Integer.parseInt(check);
        } catch (Exception e){
            //if the input is not an integer, then display an error
            System.out.println("Error: Monetary values must be entered in cents as an integer.");
            return -1;
        }
        
        
        if(checkNum < 0){ //check to see if the amount is not negative
            
            System.out.println("Error: Cannot withdraw less than nothing.");
            return -1;
            
        } else if(checkNum > maxValue){ //check to see if the amount is above maxValue
            
            if(outState == 1){ //if we are in machine mode, show matching error
                System.out.println("Error: Cannot withdraw more than $1,000.00 while on an ATM.");
                return -1;
            }
            
            //otherwise show agent error
            System.out.println("Error: Cannot withdraw more than $999,999.00.");
            return -1;
            
        }
        
        //if we are in machine mode, we cannot transfer more than $10,000.00 in single session
        if(outState == 1){
            
            int sessionAmount = 0; //init session amount
            
            //we must check each transaction in this session to get withdraw amount for FROM account
            for (int i = 1; i < transactions.length; i++) {
                
                String current = (String)transactions[i]; //current transaction
                String[] segments = current.split(" "); //split the transaction summary at white space
                
                //check to see if the transaction code is transfer and if the FROM account matches
                if(segments[0].equals("WDR") && segments[3].equals("" + accFrom)){
                    
                    //add the transaction to the session amount
                    sessionAmount += Integer.parseInt(segments[2]);
                    
                    //if at any point sessionAmount + checkNum are greater that the allowed amount, throw an error
                    if(sessionAmount + checkNum > maxSessionWithdraw){
                        
                        int remainder = maxSessionWithdraw - sessionAmount;
                        
                        System.out.println("Error: Cannot deposit more than $5,000.00 "
                                + "in a single processing day."
                                + "\nYou may still deposit " + remainder + ".");
                        return -1;
                    }
                }
                    
            }
        }
        //if all these conditions are passed, we can return the number
        return checkNum;
    }
}
