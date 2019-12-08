package main.java.ca.queensu.cisc327;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class TSummaryObject 
{
	
	private String outFile;
	private Stack<String> outStrings;

	public TSummaryObject(String out)
	{
		outFile = out;
		outStrings = new Stack<String>();
	}
	
	public void stackPush(String s) // add to the end of the stack so when we pop it pops in chronological order
	{
		outStrings.add(0, s);
	}
	
	public void createFile(int sessionCount) throws IOException 
	{
		String strToWrite = "";
		
		while (!outStrings.isEmpty()) 
		{
			strToWrite += outStrings.pop();	// pop the stack and write it to the output file
		}
		Files.write(new File(outFile + "TransactionSummaryFile" + sessionCount + ".txt").toPath(), strToWrite.getBytes());
	}
	public Stack<String> getTransactions()
	{
        return outStrings;
    }
}
