/*
 * Quinterac Backend Prototype
 * CISC327 Fall 2019 Assignment 4
 * Queen's University at Kingston ON
 * Contributors: Matt Denny, Kai Laxdal, Brent Littlefield, Tong Liu
 */

package main.java.ca.queensu.cisc327;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class MergedSummaryObj 
{
	private String file;	
	public ArrayList<String []> trans;
	public int size;

	//constructor
	public MergedSummaryObj (String file)
	{
		this.setFile(file);
		trans = new ArrayList <String[]> ();
		splitUpTransactions();
		size = trans.size();
	}	

	//
	public void splitUpTransactions ()
	{
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String currTrans = ""; 

			while ( (currTrans = reader.readLine()) != null)
			{
				String[] splitTrans = currTrans.split(" ");
				if(splitTrans[0].equals("EOS"))
				{
					continue;
				}
				else
				{
					trans.add(splitTrans);
				}
			}
			reader.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
}
