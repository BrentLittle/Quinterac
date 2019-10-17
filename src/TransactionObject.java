/*
 * SHOULD make different transaction object for each type of transaction
 * depending on transaction type will advance the session state
 */

/*
 * Use AbsTransactionObject + concrete transaction classes instead of this one
 */

public class TransactionObject {
	
	private int type;
	private int state;
	//private String[] parsedInput;

	public TransactionObject(String transaction, int state) throws InvalidStringException {
		//this.parsedInput = transaction;
		this.state = state;
		//switch (transaction[0]) {
		switch (transaction) {
		case "login" :
			type = 1;
			break;
		case "logout" :
			type = 2;
			break;
		case "creatacc" :
			type = 3;
			break;
		case "deleteacc" :
			type = 4;
			break;
		case "deposit" :
			type = 5;
			break;
		case "withdraw" :
			type = 6;
			break;
		case "transfer" :
			type = 7;
			break;
		default:
			//throw new InvalidStringException("Unrecognized transaction encountered: " + transaction[0]);
			throw new InvalidStringException("Unrecognized transaction encountered: " + transaction);
		}
	}
	
	public int getType () {
		return type;
	}
	
	public String process() throws OutOfOrderException{
		switch (type) {
		case 1:			
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		default: return "";
				
		}
	}
}
