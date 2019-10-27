public class TransactionLogout extends WritableTransaction
{
	
	public TransactionLogout(int state) throws OutOfOrderException 
	{
		if(state == 0 || state == 3) 
		{
			throw new OutOfOrderException("cant logout before login");
		}
	}
	
	@Override
	public void process() 
	{
		state = 3;
	}
	
	@Override
	public int getState() 
	{
		return state;
	}

}
	


