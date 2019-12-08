package main.java.ca.queensu.cisc327;

import java.util.*;

public abstract class AccountsRequiredTransaction extends WritableTransaction{
	
	protected ArrayList<Integer> accNums;
	
	/**
     * Handles input for the account number
     * @param in BufferedReader to read the input from
     * @return the verified account number as an int
     */
	protected int getAccountNumber(Scanner in){
        
        String input;
        int num;
        
        while(true){
            
        	System.out.println("Please enter account number:");
        	
            input = in.nextLine();
            
            if(input.equals(exitString)) {
            	return exitCode; //return the exit code
            }
            
            //validate account number and store it in num
            num = validateAccNum(input);
            
            //if num is less than 0 then we have an error and we want the user to reenter
            if(num < 0){
            	
            	handleAccountNumberError(num);
                continue;
            }
            
            //if there is no error then we return the account number
            System.out.println("Account number confirmed...");
            return num;
        }
    }
    
    /**
     * Handles input for the account number
     * @param in BufferedReader to read the input from
     * @param last int that was the last account number used
     * @return the verified account number as an int
     */
    protected int getAccountNumber(Scanner in, int last){
        String input;
        int num;
        
        while(true){
            
        	System.out.println("Please enter account number:");
        	
            //get account number from the input
        	input = in.nextLine();
            
            if(input.equals(exitString)) {
            	return exitCode; //return the exit code
            }
            
            //validate account number and store it in num
            num = validateAccNum(input, last);
            
            //if num is less than 0 then we have an error and we want the user to reenter
            if(num < 0){
            	
            	handleAccountNumberError(num);
                continue;
            }
            
            //if there is no error then we return the account number
            System.out.println("Account number confirmed...");
            return num;
        }
    }
	
    /**
     * Handles input for the account number
     * @param in BufferedReader to read the input from
     * @return the verified account number name as a String
     */
    protected String getAccountName(Scanner in) {
    	
    	String input;
        String name;
        
        while(true){
            
        	System.out.println("Please enter account name:");
            //get account number from the input
        	input = in.nextLine();
            
            if(input.equals(exitString)) {
            	return exitString; //return the exit string to indicate the user wants to cancel transaction
            }
            
            //validate account number and store it in num
            name = validateAccName(input);
            
            //if num is less than 0 then we have an error and we want the user to reenter
            if(!name.equals(input)){
            	
            	handleAccountNameError(name);
            	
                continue;
            }
            
            //if there is no error then we return the account number
            System.out.println("Account name confirmed...");
            return name;
        }
    }
    
	/**
     * validates that the account number entered is valid
     * @param check String that is the account number to check
     * @return the validated account number as an int
     */
    protected int validateAccNum(String check){
        
        
        if(!check.matches("[0-9]+")){ //check that input only contains numeric values
            
            return -1;
            
        } else if(check.length() > 7){ //check if input contains more than 7 digits
            
            return -2;
            
        } else if(check.length() < 7){ //check if input contains less than 7 digits
            
            return -3;
            
        } else if(check.charAt(0) == '0'){ //check if input begins with 0
            
            return -4;
            
        }
        
        //We can now parse the input to an int since it passed all checks
        int checkNum = Integer.parseInt(check);
        
        //check to see if account number actually exists
        for (int i = 0; i < accNums.size(); i++) {
            if(checkNum == accNums.get(i)){
                
                //if so, we simply return account number
                return checkNum;
            }
        }
        
        //if account number was not found, print error and return error value -5
        return -5;
    }
    
    /**
     * validates that the account number entered is valid and does not match the previous account number
     * @param check String that is the account number to check
     * @param last int that is the last account number entered
     * @return the validated account number as an int
     */
    protected int validateAccNum(String check, int last){
        
        if(!check.matches("[0-9]+")){ //check that input only contains numeric values
            
            return -1;
            
        } else if(check.length() > 7){ //check if input contains more than 7 digits
        	
            return -2;
            
        } else if(check.length() < 7){ //check if input contains less than 7 digits
            
            return -3;
            
        } else if(check.charAt(0) == '0'){ //check if input begins with 0
            
            return -4;
            
        }
        
        //We can now parse the input to an int since it passed all checks
        int checkNum = Integer.parseInt(check);
        
        //compare account number with last account number
        if(checkNum == last){
            
            //if they match, we must have an error as you cannot transfer to the same account
            return -6;
            
        }
        
        //check to see if the account exists
        for (int i = 0; i < accNums.size(); i++) {
            if(checkNum == accNums.get(i)){
                
                //if so, simply return account number
                return checkNum;
            }
        }
        
        //if account number was not found, print error and return error value -1
        return -5;
    }

    /**
     * validates the account name
     * @param input the account name as a String
     * @return the validated account name or an error code as a String
     */
    public String validateAccName(String input)
	{
		if(input.length() < 3 || input.length() > 30) // account name must be between 3 and 30 characters inclusive
		{
			return "#1";
		}
		else if(input.startsWith(" ") || input.endsWith(" ")) // account name must not start or end with a space
		{
			return "#2";
		}
		
		return input;
	}
    
    /**
     * handles the account number errors
     * @param num the error number
     */
    protected void handleAccountNumberError(int num) {
    	
    	switch(num) {
    	
			case -1:
				System.out.println("Error: Entered account number is in fact, not a number.");
				return;
				
			case -2:
				System.out.println("Error: Account numbers must contain 7 digits.");
				return;
				
			case -3:
				System.out.println("Error: Account numbers must contain 7 digits.");
				return;
				
			case -4:
				System.out.println("Error: Account numbers cannot begin with a 0.");
				return;
				
			case -5:
				System.out.println("Error: Entered account number does not exist.");
				return;
				
			case -6:
				System.out.println("Error: From account cannot be the same as to account.");
				return;
				
			case -7:
				System.out.println("Error: Account number must not already exist.");
				return;
		}
    }

    /**
     * handles the account name errors
     * @param num the error code as a String
     */
    protected void handleAccountNameError(String error) {
    	
    	switch(error){
    		case "#1":
    			System.out.println("Error: Account name must be between 3 and 30 characters (inclusive).");
    			return;
    			
    		case "#2":
    			System.out.println("Error: Account name cannot begin or end with white space.");
    			return;
    	}
    }
}
