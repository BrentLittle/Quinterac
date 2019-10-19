import java.io.BufferedReader;
import java.util.ArrayList;

/*
 * SHOULD make different transaction object for each type of transaction
 * depending on transaction type will advance the session state
 */

/*
 * Use AbsTransactionObject + concrete transaction classes instead of this one
 */

public class TransactionObject extends AbsTransactionObject{

	protected int state;
	protected String outString;
	protected ArrayList<Integer> accNums;
	protected Object[] transactions;
	protected BufferedReader consoleIn;
	
	protected final String blankName = "***";
	protected final String blankAccount = "0000000";
	protected final String blankAmount = "000";
	
	public TransactionObject() {
		state = -1;
	}
	
	public TransactionObject(int state) {
		this.state = state;
	}
	
	public TransactionObject(BufferedReader in, FrontendObject feObject) {
		state = feObject.getState();
    	accNums = feObject.getAccountList();
        transactions = feObject.getTransactions().toArray();
        consoleIn = in;
	}
	
	/**
     * validates that the account number entered is valid
     * @param check String that is the account number to check
     * @return the validated account number as an int
     */
	protected int validateAccNum(String check){
        
        
        if(!check.matches("[0-9]+")){ //check that input only contains numeric values
            
            System.out.println("Error: Entered account number is in fact, not a number.");
            return -1;
            
        } else if(check.length() > 7){ //check if input contains more than 7 digits
            
            System.out.println("Error: Entered account number conatins more"
                    + "than 7 digits. \nAccount numbers must contain 7 digits.");
            return -1;
            
        } else if(check.length() < 7){ //check if input contains less than 7 digits
            
            System.out.println("Error: Entered account number conatins less"
                    + "than 7 digits. \nAccount numbers must contain 7 digits.");
            return -1;
            
        } else if(check.charAt(0) == '0'){ //check if input begins with 0
            
            System.out.println("Error: Account number cannot begin with a 0");
            return -1;
            
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
        
        //if account number was not found, print error and return error value -1
        System.out.println("Error: Entered account number does not exist.");
        return -1;
    }
    
    /**
     * validates that the account number entered is valid and does not match the previous account number
     * @param check String that is the account number to check
     * @param last int that is the last account number entered
     * @return the validated account number as an int
     */
	protected int validateAccNum(String check, int last){
        
        if(!check.matches("[0-9]+")){ //check that input only contains numeric values
            
            System.out.println("Error: Entered account number is in fact, not a number.");
            return -1;
            
        } else if(check.length() > 7){ //check if input contains more than 7 digits
            
            System.out.println("Error: Entered account number conatins more"
                    + "than 7 digits. \nAccount numbers must contain 7 digits.");
            return -1;
            
        } else if(check.length() < 7){ //check if input contains less than 7 digits
            
            System.out.println("Error: Entered account number conatins less"
                    + "than 7 digits. \nAccount numbers must contain 7 digits.");
            return -1;
            
        } else if(check.charAt(0) == '0'){ //check if input begins with 0
            
            System.out.println("Error: Account number cannot begin with a 0");
            return -1;
            
        }
        
        //We can now parse the input to an int since it passed all checks
        int checkNum = Integer.parseInt(check);
        
        //compare account number with last account number
        if(checkNum == last){
            
            //if they match, we must have an error as you cannot transfer to the same account
            System.out.println("Error: From account cannot be the same as to account.");
            return -1;
            
        }
        
        //check to see if the account exists
        for (int i = 0; i < accNums.size(); i++) {
            if(checkNum == accNums.get(i)){
                
                //if so, simply return account number
                return checkNum;
            }
        }
        
        //if account number was not found, print error and return error value -1
        System.out.println("Error: Entered account number does not exist.");    
        return -1;
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
            //if the input is not an integer, then display an error
            System.out.println("Error: Monetary values must be entered in cents as an integer.");
            return -1;
        }
        
        
        if(checkNum < 0){ //check to see if the amount is not negative
            
            System.out.println("Error: Amount cannot be less than nothing.");
            return -1;
            
        } else if(checkNum > maxValue){ //check to see if the amount is above maxValue
            
            if(state == 1){ //if we are in machine mode, show matching error
                System.out.println("Error: Maximum allowable amount of " + maxValue + " cents while on an ATM.");
                return -1;
            }
            
            //otherwise show agent error
            System.out.println("Error: Maximum allowable amount of " + maxValue + " cents while on an ATM.");
            return -1;
            
        }
        
        //if all these conditions are passed, we can return the number
        return checkNum;
    }
	
	/**
     * validates that the amount input entered is indeed valid
     * @param check String that is the amount input to validate
     * @param max allowable amount in machine mode
     * @return the validated amount as an int
     */
	protected int validateAmount(String check, int machineMax){
        
        int maxValue = 99999999; //default maxValue to that of agent
        int checkNum;
        
        if(state == 1) maxValue = machineMax; //change maxValue if in machine mode
        
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
            
            System.out.println("Error: Amount cannot be less than nothing.");
            return -1;
            
        } else if(checkNum > maxValue){ //check to see if the amount is above maxValue
            
            if(state == 1){ //if we are in machine mode, show matching error
                System.out.println("Error: Maximum allowable amount of " + maxValue + " cents while on an ATM.");
                return -1;
            }
            
            //otherwise show agent error
            System.out.println("Error: Maximum allowable amount of " + maxValue + " cents while on an ATM.");
            return -1;
            
        }
        
        //if all these conditions are passed, we can return the number
        return checkNum;
    }
	
	/**
     * validates that the amount input entered is indeed valid
     * @param check String that is the amount input to validate
     * @param max allowable amount in machine mode
     * @return the validated amount as an int
     */
	protected int validateAmount(String check, int machineMax, String transCode, int machineSessionMax, int checkAccount){
        
        int maxValue = 99999999; //default maxValue to that of agent
        int checkNum;
        
        if(state == 1) maxValue = machineMax; //change maxValue if in machine mode
        
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
            
            System.out.println("Error: Amount cannot be less than nothing.");
            return -1;
            
        } else if(checkNum > maxValue){ //check to see if the amount is above maxValue
            
            if(state == 1){ //if we are in machine mode, show matching error
                System.out.println("Error: Maximum allowable amount of " + maxValue + " cents while on an ATM.");
                return -1;
            }
            
            //otherwise show agent error
            System.out.println("Error: Maximum allowable amount of " + maxValue + " cents while on an ATM.");
            return -1;
            
        }
        
        //if we are in machine mode, we cannot transfer more than $10,000.00 in single session
        if(state == 1){
            
            int sessionAmount = 0; //init session amount
            
            //we must check each transaction in this session to get transfer amount for FROM account
            for (int i = 1; i < transactions.length; i++) {
                
                String current = (String)transactions[i]; //current transaction
                String[] segments = current.split("\\s+"); //split the transaction summary at white space
                
                //check to see if the transaction code is transfer and if the FROM account matches
                if(segments[0].equals(transCode) && segments[3].equals("" + checkAccount)){
                    
                    //add the transaction to the session amount
                    sessionAmount += Integer.parseInt(segments[2]);
                    
                    //if at any point sessionAmount + checkNum are greater thant the allowed amount, throw an error
                    if(sessionAmount + checkNum > machineSessionMax){
                        
                        int remainder = machineSessionMax - sessionAmount;
                        
                        System.out.println("Error: Cannot transfer more than $10,000.00 "
                                + "in a single processing day."
                                + "\nYou may still transfer " + remainder + ".");
                        return -1;
                    }
                }
                    
            }
        }
        
        //if all these conditions are passed, we can return the number
        return checkNum;
    }

	@Override
	public void process() {}

	@Override
	public int getState() {
		return 0;
	}

	@Override
	public String outString() {
		return null;
	}
}
