
public class AccountObject {
	
	private int accNumber,accBalance;
	private String accName;
	private boolean deleted;
	
	
	
	public AccountObject(int accNumber,int accBalance, String accName, boolean deleted){
		this.accNumber = accNumber;
		this.accBalance = accNumber;
		this.accName = accName;
		this.deleted = deleted;
	}
	
	public int getAccNumber() {
		return this.accNumber;
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
	
	
	public boolean validate() throws InvalidAccountException {
		
		if(this.getAccBalance() < 0) {
			throw new InvalidAccountException("balance cannot be negative");
		}
		else if(this.getDeleted() == true && this.getAccBalance() != 0) {
			throw new InvalidAccountException("deleted accounts must have 0 balance");
		}
		else {
			return true;
		}
	}
	
	

}
