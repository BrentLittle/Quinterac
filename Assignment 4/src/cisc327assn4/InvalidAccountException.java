/*
 * Quinterac Backend Prototype
 * CISC327 Fall 2019 Assignment 4
 * Queen's University at Kingston ON
 * Contributors: Matt Denny, Kai Laxdal, Brent Littlefield, Tong Liu
 */

package cisc327assn4;

public class InvalidAccountException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3076978405897087447L;

	public InvalidAccountException(String m) {
		super(m);
	}

}
