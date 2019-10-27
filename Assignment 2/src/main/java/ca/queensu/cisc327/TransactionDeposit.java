package main.java.ca.queensu.cisc327;

import java.util.*;

public class TransactionDeposit extends SummaryRequiredTransaction
{
	
	public TransactionDeposit(Scanner in, FrontendObject feObject) throws OutOfOrderException{
    	
    	state = feObject.getState();
        if(state == 0 || state == 3) throw new OutOfOrderException("Cannot deposit while logged out.");
        
        accNums = feObject.getAccountList();
        transactions = feObject.getTransactions().toArray();
        consoleIn = in;
        
        machineMaxAmount = 200000;
        machineMaxSessionAmount = 500000;
        
        System.out.println("Deposit selected...");
    }

    /**
     * called when we want to process the transaction
     */
    @Override
    public void process() {
        
        int accTo, amount;
        
        //input for accTo is handled by the getAccount function
        accTo = getAccountNumber(consoleIn);
        if(accTo == exitCode) return;
        
        //input for amount is handled by the getAmount function
        amount = getAmount(consoleIn, accTo);
        if(amount == exitCode) return;
        
        System.out.println("Successfully deposited " + amount + " cents to account "
                + accTo + ".");
        
        transactionSummary = "WDR " + accTo + " " + amount + " " + blankAccount + " " + blankName+ "\n";
        
    }
}
