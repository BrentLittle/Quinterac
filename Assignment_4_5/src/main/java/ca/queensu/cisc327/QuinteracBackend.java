/*
 * Quinterac Backend Prototype
 * CISC327 Fall 2019 Assignment 4
 * Queen's University at Kingston ON
 * Contributors: Matt Denny, Kai Laxdal, Brent Littlefield, Tong Liu
 */

package main.java.ca.queensu.cisc327;

public class QuinteracBackend {

	public static void main(String[] args) {
		
		BackendObj backend = null;
		try {
			backend = new BackendObj(args[0], args[1]);
		} catch (Exception e) {
			//generally will only catch ProcessingExceptions
			//default behaviour is to exit the system
			System.out.println(e.getMessage());
			//e.printStackTrace();
			return;
		}
		
		try {
			backend.processTrans();
		} catch (Exception e) {
			//generally will only catch ProcessingExceptions
			//default behaviour is to exit the system
			System.out.println(e.getMessage());
			//e.printStackTrace();
			return;
		}
		
		try {
			backend.update();
		} catch (Exception e) {
			//generally will only catch ProcessingExceptions
			//default behaviour is to exit the system
			System.out.println(e.getMessage());
			//e.printStackTrace();
			return;
		}
		
		System.out.println("Done");
	}
}
