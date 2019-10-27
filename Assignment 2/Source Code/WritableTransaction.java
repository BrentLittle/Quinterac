
public abstract class WritableTransaction extends Transaction {
	
	protected final String blankName = "***";
	protected final String blankAccount = "0000000";
	protected final String blankAmount = "000";
	
	protected String transactionSummary = "EOS " + blankAccount + " " + blankAmount + " " + blankAccount + " " + blankName + "\n";
	
	public String getTransactionSummary() {
		return transactionSummary;
	}
	
}
