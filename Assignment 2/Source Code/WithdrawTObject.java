import java.io.*;
import java.util.*;

/**
 *
 * @author Matt Denny - 16mmmd1 - 20065190
 */
public class WithdrawTObject extends AbsTransactionObject {
        
    private int outState;
    private String outString;
    private ArrayList<Integer> accNums;
    private Object[] transactions;
    private BufferedReader consoleIn;
    
    public WithdrawTObject(int curState, BufferedReader in, FrontendObject feObject) throws OutOfOrderException{
        if(curState == 0 || curState == 3) throw new OutOfOrderException("Cannot transfer while logged out.");
        outState = curState;
        accNums = feObject.getAccountList();
        transactions = feObject.getTransactions().toArray();
        consoleIn = in;
        System.out.println("Withdraw selected...");
    }
    
    /**
     * validates that the account number entered is valid
     * @param check String that is the account number to check
     * @return the validated account number as an int
     */
    private int validateAccNum(String check){
        
        
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
     * validates that the amount input entered is indeed valid
     * @param check String that is the amount input to validate
     * @return the validated amount as an int
     */
    private int validateMonetaryValue(String check, int accFrom){
        
        int maxValue = 99999999; //default maxValue to that of agent
        int checkNum;
        final int maxSessionWithdraw = 500000;
        
        if(outState == 1) maxValue = 100000; //change maxValue if in machine mode
        
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
                    
                    //if at any point sessionAmount + checkNum are greater thant the allowed amount, throw an error
                    if(sessionAmount + checkNum > maxSessionWithdraw){
                        
                        int remainder = maxSessionWithdraw - sessionAmount;
                        
                        System.out.println("Error: Cannot withdraw more than $5,000.00 "
                                + "in a single processing day."
                                + "\nYou may still withdraw " + remainder + ".");
                        return -1;
                    }
                }
                    
            }
        }
        
        //if all these conditions are passed, we can return the number
        return checkNum;
    }
    
    /**
     * Handles input for the account number
     * @param in BufferedReader to read the input from
     * @param reEnterMessage String that will be displayed when the user must reenter their input
     * @param confirmMessage String that will be displayed when the account number is confirmed
     * @return the verified account number as an int
     */
    private int getAccount(BufferedReader in, String reEnterMessage, String confirmMessage){
        
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
            
            //if num is -1 then we have an error and we want the user to reenter
            if(num == -1){
                System.out.printf(reEnterMessage);
                continue;
            }
            
            //if there is no error then we return the account number
            System.out.println(confirmMessage);
            return num;
        }
    }
    
    /**
     * Handles input for the amount to transfer
     * @param in BufferedReader to read the input from
     * @param reEnterMessage String that will be displayed when the user must reenter their input
     * @param confirmMessage String that will be displayed when the amount is confirmed
     * @return the verified amount as an int
     */
    private int getAmount(BufferedReader in, int accFrom, String reEnterMessage, String confirmMessage){
        String input;
        int num;
        
        while(true){
            
            //read in the amount from the input
            try{
                input = in.readLine();
            } catch (IOException e){
                //should not get here.
                System.out.println("Unknown IOException..."
                        + "\n" + reEnterMessage);
                continue;
            }
            
            //validate the amount
            num = validateMonetaryValue(input, accFrom);
            
            //if num is -1, then we have an error and the user must reenter
            if(num == -1){
                System.out.printf(reEnterMessage);
                continue;
            }
            
            //if there are no errors then we can simply return the amount
            System.out.println(confirmMessage);
            return num;
        }
    }

    /**
     * called when we want to process the transaction
     */
    @Override
    public void process() {
        
        String input;
        int accFrom, amount;
        final String blankName = "***";
        final String blankAccount = "0000000";
        
        //input for accFrom is handled by the getAccount function
        System.out.printf("Please enter account number to witdraw from: ");
        accFrom = getAccount(consoleIn, "Please re-enter account number: ", "Account number confirmed...");
        
        //input for amount is handled by the getAmount function
        System.out.printf("Please enter amount in cents to witdraw: ");
        amount = getAmount(consoleIn, accFrom, "Please re-enter amount in cents: ", "Amount confirmed...");
        
        //FIXME: needs failure case for over transfer limit. see R24T5...
        
        System.out.println("Successfully withdrew " + amount + " cents from account "
                + accFrom + ".");
        outString = "WDR " + blankAccount + " " + amount + " " + accFrom + " " + blankName+ "\n";
    }

    /**
     * getter for the outState
     * @return the outState as an integer
     */
    @Override
    public int getState() {
            return outState;
    }

    /**
     * getter for the transaction summary
     * @return transaction summary as a string
     */
    @Override
    public String outString() {
            return outString;
    }
}
