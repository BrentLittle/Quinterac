package ca.queensu.cisc327;

import java.util.*;

public class TransactionCreateAcct extends AccountsRequiredTransaction {
	
public TransactionCreateAcct(Scanner in, FrontendObject feObject) throws OutOfOrderException{
    	
    	state = feObject.getState();
    	
        if(state == 0 || state == 3) throw new OutOfOrderException("Cannot create account while logged out.");
        
        if(state == 1) throw new OutOfOrderException("Cannot create account while in ATM mode.");
        
        accNums = feObject.getAccountList();
        consoleIn = in;
        
        System.out.println("Delete Account selected...");
    }

    /**
     * called when we want to process the transaction
     */
    @Override
    public void process() {
        
        int accNum;
        String name;
        
        //input for accTo is handled by the getAccount function
        accNum = getAccountNumber(consoleIn);
        if(accNum == exitCode) return;
        
        //input for amount is handled by the getAmount function
        name = getAccountName(consoleIn);
        if(name.equals(exitString)) return;
        
        System.out.println("Successfully created account " + accNum + " belonging to "
                + name + ".");
        
        transactionSummary = "NEW " + accNum + " " + blankAmount + " " + blankAccount + " " + name + "\n";
        
    }

    /**
     * validates that the account number entered is valid
     * @param check String that is the account number to check
     * @return the validated account number as an int
     */
    @Override
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
                return -7;
            }
        }
        
        return checkNum;
    }
}


			
			
		
	

