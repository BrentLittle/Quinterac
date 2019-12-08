/*
 * Quinterac Backend Prototype
 * CISC327 Fall 2019 Assignment 4
 * Queen's University at Kingston ON
 * Contributors: Matt Denny, Kai Laxdal, Brent Littlefield, Tong Liu
 */

package main.java.ca.queensu.cisc327;

public class AccountObj {

	private int accNumber, accBalance;
	private String accName;
	private boolean deleted;

	public AccountObj(int accNumber, int accBalance, String accName) {
		this.accNumber = accNumber;
		this.accBalance = accBalance;
		this.accName = accName;
		deleted = false; //accounts are not deleted by default
	}

	public int getAccNumber() {
		return accNumber;
	}

	public void setAccNumber(int newNum) {
		this.accNumber = newNum;
	}

	public int getAccBalance() {
		return this.accBalance;
	}

	public void setAccBalance(int newBal) {
		this.accBalance = newBal;
	}
	
	public void addBalance(int amount) {
		this.accBalance += amount;
	}
	
	public void subBalance(int amount) {
		this.accBalance -= amount;
	}

	public String getAccName() {
		return this.accName;
	}

	public void setAccName(String newName) {
		this.accName = newName;
	}

	public boolean getDeleted() {
		return this.deleted;
	}

	public void setDeleted(boolean newDel) {
		this.deleted = newDel;
	}
	
	public String toString() {
		return Integer.toString(accNumber) + " " + Integer.toString(accBalance) + " " + accName;
	}

	public void validate() throws InvalidAccountException {

		if (this.getAccBalance() < 0) {
			throw new InvalidAccountException("Balance cannot be negative.");
		}
		if (this.getDeleted() == true && this.getAccBalance() != 0) {
			throw new InvalidAccountException("Deleted accounts must have a balance of zero.");
		}
	}
}