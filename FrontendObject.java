import java.io.*;

public class FrontendObject {
	
	private AccountFileObject accNums;
	private TSummaryObject summary;
	
	//0 = not logged in, 1 = logged in ATM, 2 = logged in agent, -1 = logged out
	private int state;
	
	public FrontendObject (String accountFile, String outFile) {
		accNums = null;
		summary = null;
		try {
			accNums = new AccountFileObject(accountFile);
		} catch (Exception e) {
			//catch all the throwables from instantation of accNums or summary here
			state = -1; //exits the loop under main
		}
		
		try {
			summary = new TSummaryObject(outFile);
		} catch (Exception e) {
			//catch all the throwables from instantation of accNums or summary here
			state = -1; //exits the loop under main
		}
	}
	
	public int getState ( ) {
		return state;
	}
	
	public void setState (int n) {
		state = n;
	}

}
