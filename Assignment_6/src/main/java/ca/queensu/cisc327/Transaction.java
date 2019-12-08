package main.java.ca.queensu.cisc327;

import java.util.*;

/*
 * All concrete transaction objects should extend this
 * This concrete objects in this hierarchy should be instantiated with a generic 
 */
public abstract class Transaction {
	
	protected Scanner consoleIn;
	protected int state;
	
	protected final String exitString = "--exit";
	protected final int exitCode = -1;
	
	public abstract void process();
	
	public int getState() {
		return state;
	}

}
