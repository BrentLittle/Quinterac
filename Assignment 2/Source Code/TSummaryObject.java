import java.io.*;
import java.util.*;

public class TSummaryObject 
{
	
	private String outfile;
	private Stack<String> outStrings;

	public TSummaryObject(String out) throws IOException 
	{
		outfile = out;
		outStrings = new Stack<String>();
	}
	
	public void stackPush(String s) // add to the end of the stack so when we pop it pops in chronological order
	{
		outStrings.add(0, s);
	}
	
	public void createFile() throws IOException 
	{
		FileWriter output = new FileWriter(outfile); // open a filewriter
		while (!outStrings.isEmpty()) 
		{
			String strToWrite = outStrings.pop();	// pop the stack and write it to the output file
			output.write(strToWrite);
		}
		output.close();
	}
	public Stack<String> getTransactions()
	{
        return outStrings;
    }
}
