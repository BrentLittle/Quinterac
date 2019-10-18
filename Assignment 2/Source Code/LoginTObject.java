import java.io.*;

public class LoginTObject extends AbsTransactionObject {
	
	private int outState;
	
	public LoginTObject(int state) throws OutOfOrderException{
		if (state == 1 || state == 2) throw new OutOfOrderException("Already logged in.");
		outState = -1;
	}
	
	//example of how this interacts with session and the main loop body

	@Override
	public void process() {
		BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
		while (outState < 0) {
			System.out.printf("Please enter login mode: ");
			String mode = null;
			try {
				mode = consoleIn.readLine().toLowerCase();
			} catch (IOException e) {
				// shouldnt get here....
			}	
			switch (mode) {
			case "machine":
				outState = 1;
				break;
			case "agent":
				outState = 2;
				break;
			default:
				System.out.printf("Please enter 'machine' or 'agent'.");
				break;
			}
		}
	}

	@Override
	public int getState() {
		return outState;
	}

	@Override
	public String outString() {
		// TODO Auto-generated method stub
		return null;
	}

}
