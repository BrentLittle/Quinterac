import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;



public class CreateTObject extends AbsTransactionObject {
	
	
	String accName;
	String accNum;
	int state;
	ArrayList <Integer> accList;
	String outString;
	
	public CreateTObject(int state , ArrayList <Integer> accList) throws OutOfOrderException{
		if(state == 0) {
			throw new OutOfOrderException("Can't create account before login\n");
		}
		if(state != 2) {
			throw new OutOfOrderException("Can't create account outside of agent login\n");
		}
		
		this.state = state;
		this.accList = accList;
	}
	
		// at this point, all permissions are assumed.
	public void process()
	{
		//while(true) 
		//{
			//int state = 2;
			//if (state == 2) 
			//{
				BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
				while(true)
				{
					System.out.println("Enter Account Number");
					try 
					{
						accNum = consoleIn.readLine();
					}
					catch(IOException e) {
						//shouldn't get here
					}
					
					if (!checkValidAccountNumber(accNum))
					{
						System.out.println("Account Number is Not Valid");
						continue;
					}
					
					if(accList.contains(Integer.parseInt(accNum)))
					{
						System.out.println("Account Number Already in Use");
						continue;
					}
					
					else
					{
						break;
					}
				}
				
				
				while(true)
				{
					System.out.println("Enter Account Name");
					try 
					{
						accName = consoleIn.readLine();
					}
					catch(IOException e) 
					{
						//shouldn't get here
					}
					if(!checkValidAccountName(accName))
					{
						System.out.println("Account Name is  Not Valid");
						continue;
					}
					else
					{
						break;
					}
				}
				
				System.out.println("Account Number "+accNum+" for "+accName+" has been created");
	}
				
//				System.out.println("Enter Account Name");
//				try 
//				{
//					accName = consoleIn.readLine();
//				}
//				catch(IOException e) {
//					//shouldn't get here
//				}
//				
//				int numlength = String.valueOf(accNum).length();
//				int firstDigit= Integer.parseInt(accNum.substring(0, 1));
//				
//				int namelength = accName.length();
//				char fchar = accName.charAt(0);
//				char lchar = accName.charAt(namelength);
//				
//				//check num length, if good, proceed
//				
//				if(numlength != 7 || firstDigit != 0) {
//					continue;
//				}
//				
//				//check name length
//				else if(namelength < 3 || namelength > 30){
//					if(fchar == ' ' || lchar == ' ') {
//						continue;
//					}
//				}
//				else {
//					boolean uniqueNum = false;
//				//check number unique
//					for (int acnum: accList) {
//						if (acnum == accNum) {
//							continue;  // num not unique, retry
//						}
//					}
//				}
				
				//outString = "NEW "+Integer.toString(accNum) +" "+"000 " + "0000000 "+accName+"/n";
				
			//}
		//}
			
		public int getState() {
			return 0;
		}
		
		public String outString() {
			return "NEW "+accNum+" 000 0000000 "+accName+"\n";
			
		}
		
		public boolean checkValidAccountNumber(String input)
		{
			if(input.length() != 7) // less than 7 digits in account number
	    	{
	    		return false;
	    	}
	    	else if ( !(input.matches("[0-9]+")) ) // account number contains non-numeric characters
	    	{
	    		return false;
	    	}
	    	else if (input.startsWith("0") && !(input.equals("0000000"))) // starts with a 0 but is not the ending line of 0000000
	    	{
	    		return false;
	    	}
			return true;
		}
		
		public boolean checkValidAccountName(String input)
		{
			if(input.length() < 3 || input.length() > 30) // account name bust be between 3 and 30 characters inclusive
			{
				return false;
			}
			else if(input.startsWith(" ") || input.endsWith(" "))
			{
				return false;
			}
			return true;
		}
}


			
			
		
	

