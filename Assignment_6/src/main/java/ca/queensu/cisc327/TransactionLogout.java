package main.java.ca.queensu.cisc327;

public class TransactionLogout extends WritableTransaction
{
	
	public TransactionLogout(int state) throws OutOfOrderException 
	{
		if(state == 0 || state == 3) 
		{
			throw new OutOfOrderException("Cannot logout before login.");
		}
	}
	
	@Override
	public void process() 
	{
		state = 0;
	}
	
	@Override
	public int getState() 
	{
		return state;
	}

}
	


