/*
 * Quinterac Backend Prototype
 * CISC327 Fall 2019 Assignment 4
 * Queen's University at Kingston ON
 * Contributors: Matt Denny, Kai Laxdal, Brent Littlefield, Tong Liu
 */

package main.java.ca.queensu.cisc327;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.PriorityQueue;

public class MasterFileObj {

	private String filename;
	//essentially a min heap, used to write all accounts in increasing order of acc number in update()
	private PriorityQueue<Integer> allAccNums;
	public HashMap<Integer, AccountObj> allAccounts;

	public MasterFileObj(String fn) throws IOException, InvalidAccountException {
		filename = fn;
		allAccounts = new HashMap<Integer, AccountObj>();
		allAccNums = new PriorityQueue<Integer>();
		createAccountObjects();
	}

	private void createAccountObjects() throws IOException, InvalidAccountException {
		FileInputStream input = null;
		try {
			input = new FileInputStream(filename);
		} catch (IOException e) {
			System.out.printf("Error encountered while opening file.");
			//e.printStackTrace();
		}
		BufferedReader buffer = new BufferedReader(new InputStreamReader(input));
		String inLine;
		AccountObj tempAcc;
		Integer tempNum = null;
		Integer tempBal = null;
		String tempName = null;
		int index = 0;
		while ((inLine = buffer.readLine()) != null) {
			index += 1;
			String[] tempArr = inLine.split("\\s+");
			try {
				tempNum = Integer.parseInt(tempArr[0]);
				tempBal = Integer.parseInt(tempArr[1]);
				tempName = tempArr[2];
			} catch (NumberFormatException e) {
				System.out.printf("Error encountered while parsing master file in line %d", index);
				//e.printStackTrace();
			}
			if (tempNum == null || tempBal == null || tempName == null) {
				throw new InvalidAccountException("Error in master file line formatting.");
			}
			tempAcc = new AccountObj(tempNum, tempBal, tempName);
			try {
				addToHash(tempNum, tempAcc);
			} catch (InvalidAccountException e) {
				System.out.printf(e.getMessage());
				//e.printStackTrace();
			}
		} //end while
	} //end createAccountObjects()

	public void addToHash(Integer key, AccountObj acc) throws InvalidAccountException {
		if (allAccounts.get(key) != null) {
			throw new InvalidAccountException("Account already exists");
		}
		allAccounts.put(key, acc);
		allAccNums.add(key);
	}
	
	public void updateMaster() throws IOException {
        Integer currNum;
        String newMaster = "";
        String newValid = "";
        File master = new File(filename);
        File valid = new File("NewValidAccount.txt");
        FileWriter outputM = new FileWriter(master, false);
        FileWriter outputV = new FileWriter(valid, false);
        
        //gets the smallest account number in the heap
        //fetches the account object from the hash table
        //calls accountObj.toString() and overwrites the current master account file
        currNum = allAccNums.poll();
        while (currNum != null) {
            if (!allAccounts.get(currNum).getDeleted()) { //needs to add this check for deleted
                newValid += currNum;
                newMaster += allAccounts.get(currNum);
            }
            currNum = allAccNums.poll();
            
            if(currNum != null) {
            	newMaster += "\n";
            	newValid += "\n";
            }
        }
        outputM.write(newMaster);
        outputV.write(newValid);
        outputM.close();
        outputV.close();
    }
}
