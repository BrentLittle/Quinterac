package main.java.ca.queensu.cisc327;

import java.util.*;

/**
 *
 * @author Matt Denny - 16mmmd1 - 20065190
 */
public class TransactionWithdraw extends SummaryRequiredTransaction {
    
    public TransactionWithdraw(Scanner in, FrontendObject feObject) throws OutOfOrderException{
    	
    	state = feObject.getState();
        if(state == 0 || state == 3) throw new OutOfOrderException("Cannot transfer while logged out.");
        
        accNums = feObject.getAccountList();
        transactions = feObject.getTransactions().toArray();
        consoleIn = in;
        
        machineMaxAmount = 100000;
        machineMaxSessionAmount = 1000000;
        
        System.out.println("Withdraw selected...");
    }

    /**
     * called when we want to process the transaction
     */
    @Override
    public void process() {
        
        int accFrom, amount;
        
        //input for accFrom is handled by the getAccount function
        System.out.printf("Please enter account number to witdraw from: ");
        accFrom = getAccountNumber(consoleIn, "Please re-enter account number: ", "Account number confirmed...");
        
        if(accFrom == exitCode) return;
        
        //input for amount is handled by the getAmount function
        System.out.printf("Please enter amount in cents to witdraw: ");
        amount = getAmount(consoleIn, accFrom, "Please re-enter amount in cents: ", "Amount confirmed...");
        
        if(accFrom == exitCode) return;
        
        System.out.println("Successfully withdrew " + amount + " cents from account "
                + accFrom + ".");
        transactionSummary = "WDR " + blankAccount + " " + amount + " " + accFrom + " " + blankName+ "\n";
        
    }
}
