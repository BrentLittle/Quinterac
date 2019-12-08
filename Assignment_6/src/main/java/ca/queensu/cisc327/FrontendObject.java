package main.java.ca.queensu.cisc327;

import java.io.*;
import java.util.*;

public class FrontendObject {
	
	private FileVerification accNums;
	private TSummaryObject summary;
	private int sessionCount = 0;
	
	// 0 = not logged in,
	// 1 = logged in ATM,
	// 2 = logged in agent,
	// 3 = logged out,
	// -1 = error
	
	private int state;
	
	public FrontendObject (String accountFile, String outFile) {
		accNums = null;
		summary = null;
		state = 0;
		try {
			accNums = new FileVerification(accountFile);
		} catch (InvalidAccountException e) {
			System.out.println(e.getMessage());
			state = -1; //exits the loop under main
		}
		
		summary = new TSummaryObject(outFile);
	}
	
	public int getState ( ) {
		return state;
	}
	
	public void setState (int n) {
		state = n;
	}
	
	public Stack<String> getTransactions(){
        return summary.getTransactions();
    }
	
	public boolean checkDuplicate(int n) {
		return accNums.checkDuplicate(n);
	}
	
	public void stackPush(String transLine) {
		summary.stackPush(transLine);
	}
	
	public void createSummary() throws IOException {
		summary.createFile(sessionCount);
		sessionCount++;
	}
	
	public ArrayList <Integer> getAccountList() {
		return accNums.getAccNums();
	}

}
