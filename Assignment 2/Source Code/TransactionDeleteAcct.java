import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
*
* @author Brent Littlefield - 16bml1 - 20060929
*/

public class TransactionDeleteAcct extends AccountsRequiredTransaction
{
	public TransactionDeleteAcct(BufferedReader in, FrontendObject feObject) throws OutOfOrderException{
    	
    	state = feObject.getState();
        if(state == 0 || state == 3) throw new OutOfOrderException("Cannot delete account while logged out.");
        
        if(state == 1) throw new OutOfOrderException("Cannot delete account while in ATM mode.");
        
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
        System.out.printf("Please enter the number of the account you wish to delete: ");
        accNumber = getAccountNumber(consoleIn, "Please re-enter account number: ", "Account number confirmed...");
        
        //input for amount is handled by the getAmount function
        System.out.printf("Please enter account name: ");
        name = getAccountName(consoleIn, "Please re-enter account name: ", "Account name confirmed...");
        
        deleteAccountFromList(accNumber);
        
        System.out.println("Successfully deleted account " + accNumber + " belonging to "
                + name + ".");
        
        transactionSummary = "DEL " + accNumber + " " + blankAmount + " " + blankAccount + " " + name + "\n";
        
    }
    
    private void deleteAccountFromList(int account) // used to remove file from validAccounts list so we do not apply transactions with this account 
	{
		for (int i = 0; i < accNums.size(); i++) 
		{
			if (accNums.get(i) == account) 
			{
				accNums.remove(i);
				break;
			}
		}
	}
}
