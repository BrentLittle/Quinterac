package createacct;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;



public class CreateTObject extends AbsTransactionObject {
	
	
	String accName;
	int accNum;
	int state;
	ArrayList <Integer> numList;
	String outString;
	
	public CreateTObject(int state , ArrayList <Integer> numList) throws OutOfOrderException{
		if(state == 0) {
			throw new OutOfOrderException("cant create account before login");
		}
		if(state != 2) {
			throw new OutOfOrderException("cant create account outside of agent login");
		}
		
		this.state = state;
		this.numList = numList;
	}
	
		// at this point, all permissions are assumed.
	public void process(){
		
		
		while(true) {
			int state = 2;
			if (state == 2) {
				
				BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
				
				System.out.println("Enter Account Number");
				try {
				accNum = Integer.parseInt(consoleIn.readLine());
				}
				catch(IOException e) {
					//shouldn't get here
				}
				
				System.out.println("Enter Account Name");
				try {
				accName =consoleIn.readLine();
				}
				catch(IOException e) {
					//shouldn't get here
				}
				
				int numlength = String.valueOf(accNum).length();
				int firstDigit= Integer.parseInt(Integer.toString(accNum).substring(0, 1));
				
				int namelength = accName.length();
				char fchar = accName.charAt(0);
				char lchar = accName.charAt(namelength);
				
				//check num length, if good, proceed
				
				if(numlength != 7 || firstDigit != 0) {
					continue;
				}
				
				//check name length
				else if(namelength < 3 || namelength > 30){
					if(fchar == ' ' || lchar == ' ') {
						continue;
					}
				}
				else {
				//check number unique
					for (int acnum: numList) {
						if (acnum == accNum) {
							continue;  // num not unique, retry
						}
					}
				}
				
				outString = "NEW "+Integer.toString(accNum) +" "+"000 " + "0000000 "+accName+"/n";
				}
			}
		}
			
		public int getState() {
			return 0;
		}
		
		public String outString() {
			return outString;
			
		}
}


			
			
		
	

