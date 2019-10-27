package main.java.ca.queensu.cisc327;

import java.util.*;

public class TransactionLogin extends Transaction {
	
	public TransactionLogin(Scanner in, int state) throws OutOfOrderException{
		if (state == 1 || state == 2) throw new OutOfOrderException("Already logged in.");
		this.state = -1;
		consoleIn = in;
	}
	
	//example of how this interacts with session and the main loop body

	@Override
	public void process() {
		while (state < 0) {
			System.out.printf("Please enter login mode: ");
			String mode = null;
			
			mode = consoleIn.nextLine().toLowerCase();
			
			switch (mode) {
			case "machine":
				state = 1;
				break;
			case "agent":
				state = 2;
				break;
			default:
				System.out.println("Error: Please enter 'machine' or 'agent'. ");
				break;
			}
		}
	}
}
