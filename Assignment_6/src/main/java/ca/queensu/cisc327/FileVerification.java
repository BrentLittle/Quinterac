package main.java.ca.queensu.cisc327;

import java.io.*;
import java.util.*;

// This class when given a valid file name for an accounts List file will determine if all of the lines in the file
// meet the requirements of valid account numbers and an ending entry of 0000000

/**
*
* @author Brent Littlefield - 16bml1 - 20060929
*/

public class FileVerification {
	
	public ArrayList<Integer> accNums = new ArrayList<Integer>();
	boolean fileValid = false;
	String fileName = "";
	
	public FileVerification(String fn) throws InvalidAccountException
	{
		fileName = fn;
		checkValidity();
		if(!fileValid)
		{
			throw new InvalidAccountException("Invalid Account File Recieved");
		}
	}
	
	public void checkValidity ()
	{
		try
		{
		    BufferedReader reader = new BufferedReader(new FileReader(fileName)); // open the file
		    String lastLine = "";
		    String currLine = "";													
		    
		    while ( (currLine = reader.readLine()) != null) // go through all the lines until reached the end
		    {
		    	if(currLine.length() != 7) // accountNum was not 7 digits in length
		    	{
		    		fileValid = false;
		    		reader.close();
		    		return;
		    	}
		    	else if ( !(currLine.matches("[0-9]+")) ) // account number contains non-numeric characters
		    	{
		    		fileValid = false;
		    		reader.close();
		    		return;
		    	}
		    	else if (currLine.startsWith("0") && !(currLine.equals("0000000"))) // starts with a 0 but is not the ending line of 0000000
		    	{
		    		fileValid = false;
		    		reader.close();
		    		return;
		    	}
		    	
		    	if (checkDuplicate(Integer.parseInt(currLine))) // account has already been added to the list so we have a duplicate account in file
		    	{
		    		fileValid = false;
		    		reader.close();
		    		return;
		    	}
		    	
		    	accNums.add(Integer.parseInt(currLine));
		    														
		    	lastLine = currLine; 	// keep track of the current line read from the file in the next 
		    }
		    
		    if ( !(lastLine.equals("0000000")) ) {  // make sure that if the last Line in the file is not 0000000 then it is false
	    		fileValid = false;
	    		reader.close();
	    		return;
	    	}		    
		    
		    fileValid = true;  // if we reach here then we have a valid account 
		    reader.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean getFileValid()
	{
		return fileValid;
	}
	
	public boolean checkDuplicate(int n) // check for possible duplicate to n in the valid accounts arraylist
	{
		for (int i = 0; i < accNums.size(); i++) 
		{
			if (accNums.get(i) == n) 
			{
				return true;
			}
		}
		return false;
	}
		
	public int getAccNum(int index) 
	{
		int n = accNums.get(index);
		return n;
	}
	
	public ArrayList<Integer> getAccNums()
	{
		return accNums;
	}
}
