package main.java.ca.queensu.cisc327;

import java.util.*;

/**
 *
 * @author Matt Denny - 16mmmd1 - 20065190
 */
public class TransactionTransfer extends SummaryRequiredTransaction {
    
    public TransactionTransfer(Scanner in, FrontendObject feObject) throws OutOfOrderException{
    	state = feObject.getState();
        if(state == 0 || state == 3) throw new OutOfOrderException("Cannot transfer while logged out.");
        
        accNums = feObject.getAccountList();
        transactions = feObject.getTransactions().toArray();
        consoleIn = in;
        
        machineMaxAmount = 1000000;
        machineMaxSessionAmount = 1000000;
        
        System.out.println("Transfer selected...");
    }

    /**
     * called when we want to process the transaction
     */
    @Override
    public void process() {
        
        int accTo, accFrom, amount;
        
        //input for accTo is handled by the getAccount function
        accTo = getAccountNumber(consoleIn);
        if(accTo == exitCode) return;
        
        //input for accFrom is handled by the getAccount function with accTo as the previous account
        accFrom = getAccountNumber(consoleIn, accTo);
        if(accFrom == exitCode) return;
        
        //input for amount is handled by the getAmount function
        amount = getAmount(consoleIn, accFrom, "XFR");
        if(amount == exitCode) return;
        
        System.out.println("Successfully transferred " + amount + " cents from account "
                + accFrom + " to " + accTo + ".");
        transactionSummary = "XFR " + accTo + " " + amount + " " + accFrom + " " + blankName + EOL;
    }
    
}