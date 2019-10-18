import java.util.*;
import java.io.*;

public class DepositTObject extends AbsTransactionObject{
	
	private int outState;
	private String outString;
	
	private int depositTo;
	private int depositAmt;
	
	private int inState;
	private ArrayList<Integer> accNums;
	
	public DepositTObject (int state, ArrayList<Integer> accNums) throws OutOfOrderException {
		if (state < 1 || state > 2) throw new OutOfOrderException("Must login before deposit.");
		this.accNums = accNums;
		inState = state;
		depositTo = 0;
		depositAmt= 0;
		outState = -1;
	}

	@Override
	public void process() {
		BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
		while (outState < 0) {
			System.out.printf("Please enter destination account number: ");
			String tempStr = null;
			try {
				tempStr = consoleIn.readLine().toLowerCase();
			} catch (IOException e) {
				// shouldnt get here....
			}
			if (tempStr.length() != 7) {
				System.out.printf("Invalid account number.");
				continue;
			}
			if (tempStr.charAt(0) == '0') {
				System.out.printf("Invalid account number.");
				continue;
			}
			try {
				depositTo = Integer.parseInt(tempStr);
			} catch (NumberFormatException e) {
				System.out.printf("Invalid account number.");
				continue;
			}
			System.out.printf("Please enter deposit amount: ");
			try {
				tempStr = consoleIn.readLine().toLowerCase();
			} catch (IOException e) {
				// shouldnt get here....
			}
			try {
				depositAmt = Integer.parseInt(tempStr);
			} catch (NumberFormatException e) {
				System.out.printf("Invalid account number.");
				continue;
			}
			if (inState == 1 && depositAmt > 200000) {
				System.out.printf("Invalid deposit amount for ATM mode.");
				continue;
			}
			if (depositAmt > 99999999) {
				System.out.printf("Deposit amount exceeds maximum allowed.");
				continue;
			}
			outState = 2;
		}
	}

	@Override
	public int getState() {
		return outState;
	}

	@Override
	public String outString() {
		return "DEP " + depositTo + " " + depositAmt + " 0000000 ***";
	}

}
