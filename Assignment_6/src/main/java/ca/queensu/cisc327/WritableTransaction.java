package main.java.ca.queensu.cisc327;

public abstract class WritableTransaction extends Transaction {
	
	protected final String blankName = "***";
	protected final String blankAccount = "0000000";
	protected final String blankAmount = "000";
	protected final String EOL = "\r\n";
	
	protected String transactionSummary = "EOS " + blankAccount + " " 
						+ blankAmount + " " + blankAccount + " " + blankName + EOL;
	
	public String getTransactionSummary() {
		return transactionSummary;
	}
	
}
