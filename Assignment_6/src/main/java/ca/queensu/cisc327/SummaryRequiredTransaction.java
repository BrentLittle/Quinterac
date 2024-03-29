package main.java.ca.queensu.cisc327;

import java.util.*;

public abstract class SummaryRequiredTransaction extends AccountsRequiredTransaction {

	protected Object[] transactions;
	protected int machineMaxAmount;
	protected int machineMaxSessionAmount;
    
    /**
     * Handles input for the amount to transfer
     * @param in Scanner to read the input from
     * @param checkAcc the account to check summary of
     * @param transCode the transaction code
     * @return the verified amount as an int
     */
    protected int getAmount(Scanner in, int checkAcc, String transCode){
        String input;
        int num;
        
        while(true){
            
        	System.out.println("Please enter amount:");
            //read in the amount from the input
        	input = in.nextLine();
            
            if(input.equals(exitString)) {
            	return exitCode; //return the exit code
            }
            
            if(state == 2) {
            	num = validateAmount(input);
            } else {
            	num = validateAmount(input, machineMaxAmount, checkAcc, machineMaxSessionAmount, transCode);
            }
            
            //if num is -1, then we have an error and the user must reenter
            if(num < 0){
            	
            	handleAmountError(num);
                continue;
            }
            
            //if there are no errors then we can simply return the amount
            System.out.println("Amount confirmed...");
            return num;
        }
    }
	
	/**
     * validates that the amount input entered is indeed valid
     * @param check String that is the amount input to validate
     * @return the validated amount as an int
     */
    protected int validateAmount(String check){
        
        int maxValue = 99999999; //default maxValue to that of agent
        int checkNum;
        
        //check to see if we can parse the input to an int
        try{
            //no error will occur if input is indeed an int, we can simply proceed
            checkNum = Integer.parseInt(check);
        } catch (Exception e){
            //if the input is not an integer, return error
            return -1;
        }
        
        if(checkNum < 0){ //check to see if the amount is not negative
            
        	//if it is, return error
        	return -2;
            
        } else if(checkNum > maxValue){ //check to see if the amount is above maxValue
            
        	//if it is, return error
            return -3;
            
        }
        
        //if all these conditions are passed, we can return the number
        return checkNum;
    }
	
	/**
     * validates that the amount input entered is indeed valid and checks for max session amount
     * @param check String that is the amount input to validate
     * @return the validated amount as an int
     */
    protected int validateAmount(String check, int maxMachineAmount, int checkAcc, int maxMachineSessionAmount, String transCode){
        
    	int checkNum;
    	int maxValue = maxMachineAmount; //default maxValue to that of agent
        int sessionAmount = 0;
        final int maxSessionAmount = maxMachineSessionAmount;
        
        //check to see if we can parse the input to an int
        try{
            //no error will occur if input is indeed an int, we can simply proceed
            checkNum = Integer.parseInt(check);
        } catch (Exception e){
            //if the input is not an integer, then display an error
            return -1;
        }
        
        
        if(checkNum < 0){ //check to see if the amount is not negative
            
            return -2;
            
        } else if(checkNum > maxValue){ //check to see if the amount is above maxValue
            
            return -3;
            
        }
        
        if(transCode.equals("DEP")) {
        	//we must check each transaction in this session to get transfer amount for FROM account
            for (int i = 0; i < transactions.length; i++) {
                
                String current = (String)transactions[i]; //current transaction
                String[] segments = current.split("\\s+"); //split the transaction summary at white space
                
                //check to see if the transaction code is transfer and if the FROM account matches
                if(segments[0].equals(transCode) && segments[1].equals("" + checkAcc)){
                    
                    //add the transaction to the session amount
                    sessionAmount += Integer.parseInt(segments[2]);
                    
                    //if at any point sessionAmount + checkNum are greater than the allowed amount, throw an error
                    if(sessionAmount + checkNum > maxSessionAmount){
                        
                        return -4;
                    }
                }
                    
            }
        }
        else
        {
        	//we must check each transaction in this session to get transfer amount for FROM account
            for (int i = 0; i < transactions.length; i++) {
                
                String current = (String)transactions[i]; //current transaction
                String[] segments = current.split("\\s+"); //split the transaction summary at white space
                
                //check to see if the transaction code is transfer and if the FROM account matches
                if(segments[0].equals(transCode) && segments[3].equals("" + checkAcc)){
                    
                    //add the transaction to the session amount
                    sessionAmount += Integer.parseInt(segments[2]);
                    
                    //if at any point sessionAmount + checkNum are greater than the allowed amount, throw an error
                    if(sessionAmount + checkNum > maxSessionAmount){
                        
                        return -4;
                    }
                }
                    
            }
        }
        
        //if all these conditions are passed, we can return the number
        return checkNum;
    }
	
    private void handleAmountError(int num) {
    	
    	switch(num) {
    	
			case -1:
				System.out.println("Error: Entered amount must be an integer.");
				return;
				
			case -2:
				System.out.println("Error: Entered amount cannot be less than nothing.");
				return;
				
			case -3:
				System.out.println("Error: Entered amount cannot exceed the maximum allowable amount.");
				return;
				
			case -4:
				System.out.println("Error: Entered amount cannot exceed the maximum allowable "
						+ "session amount for this type of transaction.");
				return;
		}
    }
}
