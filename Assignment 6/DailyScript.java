/*
 * Quinterac Integration Daily Script
 * CISC327 Fall 2019 Assignment 6
 * Queen's University at Kingston ON
 * Contributors: Matt Denny, Kai Laxdal, Brent Littlefield, Tong Liu
 */

//package main.java.ca.queensu.cisc327;
package ca.queensu.cisc327;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class DailyScript 
{
	
	public static void main (String args[]) 
	{
		String fileSep = System.getProperty("file.separator");
		System.out.println("Starting daily script...");
		Scanner consoleIn = new Scanner(System.in);
		int sessionNum = getSessions(consoleIn);
		String arr[] = new String[2];
		arr[0] = "ValidAccountsFile.txt";
		
		String sumFolder = "summaries"; //change this as needed
		File summaryFolder = new File(sumFolder);
		summaryFolder.mkdir();
		for (int n = 1; n <= sessionNum; n++) 
		{
			runFrontend(arr, n, fileSep, sumFolder);
		}
		
		runBackend(consoleIn, arr, sumFolder);
		System.out.println("Daily script completed. Press \'Enter\' to exit...");
		fileSep = consoleIn.nextLine();
		consoleIn.close();
	}
	
	private static void runBackend(Scanner in, String[] array, String folder) 
	{
		System.out.println("==================================================");
		System.out.printf("Starting backend session...");
		array[0] = folder;
		while (true) {
			System.out.printf("Please enter the name of the master summary file: ");
			array[1] = in.nextLine();
			File f = new File(array[1]);
			try {
				if (! f.createNewFile())
				{
					break; //user must enter a master file name which already exists
				}
			} catch (IOException e) {
				//do nothing
			}
			System.out.println("File not found, please try again.");
		}
		
		QuinteracBackend.main(array);
		System.out.println("Backend session completed.");
	}

	public static void runFrontend(String[] array, int counter, String sep, String folder) 
	{
		System.out.println("==================================================");
		System.out.printf("Starting frontend session number: %d\n", counter);
		array[1] = "TransactionSummaryFile" + counter;
		try
		{
			// TODO
			//Update the file pathing as necessary, default is: projectroot\summaries\TransactionSummaryNumber.txt
			//The summaries should be collected into one folder for the backend
			File f = new File(folder + sep + array[1]);
			f.createNewFile();
			Quinterac.main(array);
		} catch (IOException e)
		{
			System.out.println("Error encountered.");
			e.getStackTrace();
			System.out.println(e.getMessage());
			System.out.println("Exiting...");
			System.exit(-1);
		}
		System.out.printf("Frontend session %d completed.\n", counter);
		System.out.printf("Output printed to %s.\n", array[1]);
	}

	public static int getSessions(Scanner in)
	{
		int ret = 0;
		while (ret < 3 || ret > 10)
		{
			try 
			{
				System.out.printf("Please enter number of frontend sessions (between 3 and 10): ");
				ret = Integer.parseInt(in.nextLine());
			} catch (Exception e) 
			{
				//do nothing
			}
		}
		return ret;
	}
}
