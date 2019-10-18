import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

// This class when given a valid file name for an accounts List file will determine if all of the lines in the file
// meet the requirements of valid account numbers and an ending entry of 0000000

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
		    	if(currLine.length() != 7) // less than 7 digits in account number
		    	{
		    		fileValid = false;
		    		return;
		    	}
		    	else if ( !(currLine.matches("[0-9]+")) ) // account number contains non-numeric characters
		    	{
		    		fileValid = false;
		    		return;
		    	}
		    	else if (currLine.startsWith("0") && !(currLine.equals("0000000"))) // starts with a 0 but is not the ending line of 0000000
		    	{
		    		fileValid = false;
		    		return;
		    	}
		    	
		    	accNums.add(Integer.parseInt(currLine));
		    														
		    	lastLine = currLine; 	// keep track of the current line read from the file in the next 
		    }
		    
		    if ( !(lastLine.equals("0000000")) ) {  // make sure that if the last Line in the file is not 0000000 then it is false
	    		fileValid = false;
	    		return;
	    	}		    
		    else {
		    	fileValid = true;
		    }
		    
		    reader.close();
		}
		catch (Exception e) {e.printStackTrace();}
	}
	
	public boolean getFileValid()
	{
		return fileValid;
	}
	
	public boolean checkDuplicate(int n) {
		for (int i = 0; i < accNums.size(); i++) {
			if (accNums.get(i) == n) {
				return true;
			}
		}
		return false;
	}
		
	public int getAccNum(int index) {
		int n = accNums.get(index);
		return n;
	}
	
	public ArrayList<Integer> getAccNums()
	{
		return accNums;
	}
}
