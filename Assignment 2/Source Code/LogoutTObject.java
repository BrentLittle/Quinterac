
public class LogoutTObject extends AbsTransactionObject
{
	
	private int outState;
	
	public LogoutTObject(int state) throws OutOfOrderException 
	{
		if(state == 0 || state == 3) 
		{
			throw new OutOfOrderException("cant logout before login");
		}
	}
	@Override
	public void process() 
	{
		outState = 3;
	}
	
	@Override
	public int getState() 
	{
		return outState;
	}
	@Override
	public String outString() 
	{
		return "EOS 0000000 000 0000000 ***";
	}

}
	


