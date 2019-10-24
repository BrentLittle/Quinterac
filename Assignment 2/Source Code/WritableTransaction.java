
public abstract class WritableTransaction extends Transaction {

	protected String transactionSummary;
	
	protected final String blankName = "***";
	protected final String blankAccount = "0000000";
	protected final String blankAmount = "000";
	
	public String getTransactionSummary() {
		return transactionSummary;
	}
	
}
