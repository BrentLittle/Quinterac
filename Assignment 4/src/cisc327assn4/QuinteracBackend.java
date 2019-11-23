/*
 * Quinterac Backend Prototype
 * CISC327 Fall 2019 Assignment 4
 * Queen's University at Kingston ON
 * Contributors: Matt Denny, Kai Laxdal, Brent Littlefield, Tong Liu
 */

package cisc327assn4;

public class QuinteracBackend {

	public static int main(String[] args) {
		
		BackendObj backend = null;
		try {
			backend = new BackendObj(args[0], args[1]);
		} catch (Exception e) {
			//generally will only catch ProcessingExceptions
			//default behaviour is to exit the system
			System.out.printf(e.getMessage());
			e.printStackTrace();
			System.exit(-1);
		}
		
		try {
			backend.processTrans();
		} catch (Exception e) {
			//generally will only catch ProcessingExceptions
			//default behaviour is to exit the system
			System.out.printf(e.getMessage());
			e.printStackTrace();
			System.exit(-1);
		}
		
		try {
			backend.update();
		} catch (Exception e) {
			//generally will only catch ProcessingExceptions
			//default behaviour is to exit the system
			System.out.printf(e.getMessage());
			e.printStackTrace();
			System.exit(-1);
		}
		
		System.out.printf("Done");
		
		return 0;
	}
}
