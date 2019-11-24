/*
 * Quinterac Backend Prototype
 * CISC327 Fall 2019 Assignment 4
 * Queen's University at Kingston ON
 * Contributors: Matt Denny, Kai Laxdal, Brent Littlefield, Tong Liu
 */

package main.java.ca.queensu.cisc327;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class BackendObj {

	private MasterFileObj masterFile;
	private MergedSummaryObj mergedTrans;
	
	public BackendObj (String dir, String master) throws FileNotFoundException, IOException, 
	InvalidAccountException, TransactionSummaryException, ProcessingException {
		
		//merge transaction summaries into one
		try {
			mergeFiles(dir);
		} catch (FileNotFoundException e) {
			System.out.println("Error while merging summaries.");
			//e.printStackTrace();
			throw new ProcessingException("Error during BackendObj.mergeFiles");
		} catch (IOException e) {
			System.out.println("Error while reading or writing to file.");
			//e.printStackTrace();
			throw new ProcessingException("Error during BackendObj.mergeFiles");
		}
		
		//instantiate merged summary object
		try {
		mergedTrans = new MergedSummaryObj("MergedSummary.txt");
		} catch (Exception e) {
			System.out.println("Error while creating MergedSummaryObj.");
			//e.printStackTrace();
			throw new ProcessingException("Error during instantiating mergedTrans");
		}
		
		//instantiate master file object
		try {
			masterFile = new MasterFileObj(master);
		} catch (IOException e) {
			System.out.println("Error while reading master acc file.");
			//e.printStackTrace();
			throw new ProcessingException("Error during instantiating masterFile");
		} catch (InvalidAccountException e) {
			System.out.println("Error while parsing master acc file.");
			//e.printStackTrace();
			throw new ProcessingException("Error during instantiating masterFile");
		}
		
	} //end constructor
	
	public void processTrans() throws ProcessingException, TransactionSummaryException {
		//process transactions in transaction summary, apply changes to each account object
		String[] currentLine = null;
		for (int i = 0; i < mergedTrans.size; i++) {
			currentLine = mergedTrans.trans.get(i);
			switch (currentLine[0]) {
			case "DEP":
				processDEP(currentLine[1], currentLine[2]);
				break;
			case "WDR":
				processWDR(currentLine[3], currentLine[2]);
				break;
			case "XFR":
				processXFR(currentLine[1], currentLine[2], currentLine[3]);
				break;
			case "NEW":
				processNEW(currentLine[1], currentLine[4]);
				break;
			case "DEL":
				processDEL(currentLine[1], currentLine[4]);
				break;
			case "EOS":
				//skips to next line
				break;
			default:
				//if there is an unreadable line, exits system without writing to master file
				//or creating new valid account file
				throw new TransactionSummaryException("Unreadable line encountered in merged transaction file.");
			}
		}
	}
	
	public void update() throws ProcessingException {
		//update master account file
		try {
			masterFile.updateMaster();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
			throw new ProcessingException("Error during masterFile.updateMaster()");
		}
	}
	
	private void mergeFiles(String dir) throws FileNotFoundException, IOException{
		
		// dir must be absolute path to directory
		File directory = new File (dir);
		PrintWriter output = null;
		output = new PrintWriter("MergedSummary.txt");
		String[] summaries = directory.list();
		BufferedReader buffer = null;
		
		for (String summary : summaries) {
			File input = new File(directory,summary);
			buffer = new BufferedReader(new FileReader(input));
			String inLine;
			while ((inLine = buffer.readLine()) != null) {
				output.print(inLine + "\n");
			} //end while
			output.flush();
		} //end for
		buffer.close();
		output.close();
	} //end mergeFiles()
	
	private void processDEP(String acc, String amt) throws ProcessingException {
		int account = Integer.parseInt(acc);
		int amount = Integer.parseInt(amt);
		AccountObj curr = masterFile.allAccounts.get(account);
		curr.addBalance(amount);
		try {
			curr.validate();
		} catch (InvalidAccountException e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
			throw new ProcessingException("Error during processDEP");
		}
	}
	
	private void processWDR(String acc, String amt) throws ProcessingException {
		int account = Integer.parseInt(acc);
		int amount = Integer.parseInt(amt);
		AccountObj curr = masterFile.allAccounts.get(account);
		curr.subBalance(amount);
		try {
			curr.validate();
		} catch (InvalidAccountException e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
			throw new ProcessingException("Error during processWDR");
		}
	}
	
	private void processXFR(String acc1, String amt, String acc2) throws ProcessingException {
		int accountTo = Integer.parseInt(acc1);
		int accountFrom = Integer.parseInt(acc2);
		int amount = Integer.parseInt(amt);
		AccountObj currTo = masterFile.allAccounts.get(accountTo);
		AccountObj currFrom = masterFile.allAccounts.get(accountFrom);
		currTo.addBalance(amount);
		currTo.subBalance(amount);
		try {
			currTo.validate();
			currFrom.validate();
		} catch (InvalidAccountException e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
			throw new ProcessingException("Error during processXFR");
		}
	}
	
	private void processNEW(String acc, String name) throws ProcessingException {
		int account = Integer.parseInt(acc);
		AccountObj curr = new AccountObj(account, 0, name);
		try {
			masterFile.addToHash(account, curr);
		} catch (InvalidAccountException e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
			throw new ProcessingException("Error during processNEW");
		}
	}
	
	private void processDEL(String acc, String name) throws ProcessingException {
		int account = Integer.parseInt(acc);
		AccountObj curr = masterFile.allAccounts.get(account);
		if (name.equals(curr.getAccName())) {
			curr.setDeleted(true);
			try {
				curr.validate();
			} catch (InvalidAccountException e) {
				System.out.println(e.getMessage());
				throw new ProcessingException("Error during processDEL");
			}
		} else {
			System.out.println("Non-matching account names during processDEL");
			throw new ProcessingException("Error during processDEL");
		}
	}	
} //end class
