import java.io.*;
import java.util.*;

/**
 *
 * @author Matt Denny - 16mmmd1 - 20065190
 */
public class TransferTObject extends TransactionObject {
    
    
    
    public TransferTObject(BufferedReader in, FrontendObject feObject) throws OutOfOrderException{
        super(in, feObject);
    	if(state == 0 || state == 3) throw new OutOfOrderException("Cannot transfer while logged out.");
        System.out.println("Transfer selected...");
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
     * Handles input for the account number
     * @param in BufferedReader to read the input from
     * @param last int that was the last account number used
     * @param reEnterMessage String that will be displayed when the user must reenter their input
     * @param confirmMessage String that will be displayed when the account number is confirmed
     * @return the verified account number as an int
     */
    private int getAccount(BufferedReader in, int last, String reEnterMessage, String confirmMessage){
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
            num = validateAccNum(input, last);
            
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
            num = validateAmount(input, accFrom);
            
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
        int accTo, accFrom, amount;
        
        //input for accTo is handled by the getAccount function
        System.out.printf("Please enter account number to transfer to: ");
        accTo = getAccount(consoleIn, "Please re-enter account number: ", "Account number confirmed...");
        
        //input for accFrom is handled by the getAccount function with accTo as the previous account
        System.out.printf("Please enter account number to transfer from: ");
        accFrom = getAccount(consoleIn, accTo, "Please re-enter account number: ", "Account number confirmed...");
        
        //input for amount is handled by the getAmount function
        System.out.printf("Please enter amount in cents to transfer: ");
        amount = getAmount(consoleIn, accFrom, "Please re-enter amount in cents: ", "Amount confirmed...");
        
        //FIXME: needs failure case for over transfer limit. see R24T5...
        
        System.out.println("Successfully transferred " + amount + " cents from account "
                + accFrom + " to " + accTo + ".");
        outString = "XFR " + accTo + " " + amount + " " + accFrom + " " + blankName;
    }

    /**
     * getter for the outState
     * @return the outState as an integer
     */
    @Override
    public int getState() {
            return state;
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