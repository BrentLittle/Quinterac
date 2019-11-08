import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Assn5 
{
	public ArrayList<String []> trans = new ArrayList <String[]> ();
	
	public Assn5()
	{
		
	}
	
	public void processFile (String fn)
	{
		splitUpTransactions(fn);
	}
	
	
	public void splitUpTransactions (String file)
	{
		ArrayList<String []> trans = new ArrayList <String[]> ();
		{
			try
			{
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String currTrans = ""; 
	        
				while ( (currTrans = reader.readLine()) != null)
				{
					String[] splitTrans = currTrans.split(" ");
					if(splitTrans[0].equals("EOS"))
					{
						continue;
					}
					else
					{
						trans.add(splitTrans);
					}
				}
				reader.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}	
	}
	
	public ArrayList <String []> getTransactionList()
	{
		return trans;
	}
}
