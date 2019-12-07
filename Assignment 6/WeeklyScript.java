/*
 * Quinterac Integration Weekly Script
 * CISC327 Fall 2019 Assignment 6
 * Queen's University at Kingston ON
 * Contributors: Matt Denny, Kai Laxdal, Brent Littlefield, Tong Liu
 */

package main.java.ca.queensu.cisc327;

public class WeeklyScript {

	/* 
	 * This script calls the weekly script 5 times
	 * @param: none
	 * @return: none 
	 */
	public static void main (String args[])
	{
		String[] arr = new String[1];
		for (int counter = 0; counter < 5; counter++)
		{
			arr[0] = "SummaryDailySession" + Integer.toString(counter + 1);
			DailyScript.main(arr);
		}
	}
}
