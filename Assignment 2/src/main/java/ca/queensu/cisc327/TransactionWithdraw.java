package ca.queensu.cisc327;

import java.util.*;

/**
 *
 * @author Matt Denny - 16mmmd1 - 20065190
 */
public class TransactionWithdraw extends SummaryRequiredTransaction {
    
    public TransactionWithdraw(Scanner in, FrontendObject feObject) throws OutOfOrderException{
    	
    	state = feObject.getState();
        if(state == 0 || state == 3) throw new OutOfOrderException("Cannot withdraw while logged out.");
        
        accNums = feObject.getAccountList();
        transactions = feObject.getTransactions().toArray();
        consoleIn = in;
        
        machineMaxAmount = 100000;
        machineMaxSessionAmount = 500000;
        
        System.out.println("Withdraw selected...");
    }

    /**
     * called when we want to process the transaction
     */
    @Override
    public void process() {
        
        int accFrom, amount;
        
        //input for accFrom is handled by the getAccount function
        accFrom = getAccountNumber(consoleIn);
        if(accFrom == exitCode) return;
        
        //input for amount is handled by the getAmount function
        amount = getAmount(consoleIn, accFrom, "WDR");
        if(amount == exitCode) return;
        
        System.out.println("Successfully withdrew " + amount + " cents from account "
                + accFrom + ".");
        transactionSummary = "WDR " + blankAccount + " " + amount + " " + accFrom + " " + blankName + EOL;
        
    }
}
