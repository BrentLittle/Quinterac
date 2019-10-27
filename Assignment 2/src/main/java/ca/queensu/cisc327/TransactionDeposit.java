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
        System.out.printf("Please enter account number to deposit to: ");
        accTo = getAccountNumber(consoleIn, "Please re-enter account number: ", "Account number confirmed...");
        
        if(accTo == exitCode) return;
        
        //input for amount is handled by the getAmount function
        System.out.printf("Please enter amount in cents to deposit: ");
        amount = getAmount(consoleIn, accTo, "Please re-enter amount in cents: ", "Amount confirmed...");
        
        if(amount == exitCode) return;
        
        System.out.println("Successfully deposited " + amount + " cents to account "
                + accTo + ".");
        
        transactionSummary = "WDR " + accTo + " " + amount + " " + blankAccount + " " + blankName+ "\n";
        
    }
}
