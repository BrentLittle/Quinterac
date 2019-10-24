import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;



public class TransactionCreateAcct extends AccountsRequiredTransaction {
	
public TransactionCreateAcct(BufferedReader in, FrontendObject feObject) throws OutOfOrderException{
    	
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
        
        int accNumber;
        String name;
        
        //input for accTo is handled by the getAccount function
        System.out.printf("Please enter the number of the new account: ");
        accNumber = getAccountNumber(consoleIn, "Please re-enter account number: ", "Account number confirmed...");
        
        //input for amount is handled by the getAmount function
        System.out.printf("Please enter account name: ");
        name = getAccountName(consoleIn, "Please re-enter account name: ", "Account name confirmed...");
        
        System.out.println("Successfully created account " + accNumber + " belonging to "
                + name + ".");
        
        transactionSummary = "NEW " + accNumber + " " + blankAmount + " " + blankAccount + " " + name + "\n";
        
    }
    
    /**
     * Handles input for the account number
     * @param in BufferedReader to read the input from
     * @param reEnterMessage String that will be displayed when the user must reenter their input
     * @param confirmMessage String that will be displayed when the account number is confirmed
     * @return the verified account number as an int
     */
    @Override
	protected int getAccountNumber(BufferedReader in, String reEnterMessage, String confirmMessage){
        
        String input;
        int num;
        
        while(true){
            
            //get account number from the input
            try{
                input = in.readLine();
            } catch (IOException e){
                //You really shouldnt be able to get here but we will still throw an error
                System.out.println("Unknown IOException..."
                        + "\n" + reEnterMessage);
                continue;
            }
            
            //validate account number and store it in num
            num = validateAccNum(input);
            
            //if num is less than 0 then we have an error and we want the user to reenter
            if(num < 0){
            	
            	handleAccountNumberError(num);
            	
                System.out.printf(reEnterMessage);
                continue;
            }
            
            //if there is no error then we return the account number
            System.out.println(confirmMessage);
            return num;
        }
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


			
			
		
	

