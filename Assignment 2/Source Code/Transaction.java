import java.io.BufferedReader;

/*
 * All concrete transaction objects should extend this
 * This concrete objects in this hierarchy should be instantiated with a generic 
 */
public abstract class Transaction {
	
	protected BufferedReader consoleIn;
	protected int state;
	
	public abstract void process();
	
	public int getState() {
		return state;
	}

}
