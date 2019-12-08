package main.java.ca.queensu.cisc327;

import main.java.ca.queensu.cisc327.DailyScript;

public class WeeklyScript {

	public static void main(String args[]) {
		
		String[] dailyArgs;
		
		for (int i = 0; i < 5; i++) {
			dailyArgs = new String[]{"" + i};
			DailyScript.main(dailyArgs);
		}
		
		System.out.println("Weekly run complete...");
		
	}
	
}
