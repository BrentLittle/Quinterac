/*
 * All concrete transaction objects should extend this
 * This concrete objects in this hierarchy should be instantiated with a generic 
 */
public abstract class AbsTransactionObject {

	public AbsTransactionObject() {
		// TODO Auto-generated constructor stub
	}
	
	public abstract void process();
	
	public abstract int getState();
	
	public abstract String outString();

}
