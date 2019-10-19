import java.io.*;

public class LoginTObject extends TransactionObject {
	
	BufferedReader consoleIn;
	
	public LoginTObject(BufferedReader in, FrontendObject fe) throws OutOfOrderException{
		super(in, fe);
		if (state == 1 || state == 2) throw new OutOfOrderException("Already logged in.");
	}
	
	//example of how this interacts with session and the main loop body

	@Override
	public void process() {
		
		while (state < 0) {
			System.out.printf("Please enter login mode: ");
			String mode = null;
			try {
				mode = consoleIn.readLine().toLowerCase();
			} catch (IOException e) {
				// shouldnt get here....
			}	
			switch (mode) {
			case "machine":
				state = 1;
				break;
			case "agent":
				state = 2;
				break;
			default:
				System.out.printf("Please enter 'machine' or 'agent'. ");
				break;
			}
		}
	}

	@Override
	public int getState() {
		return state;
	}

	@Override
	public String outString() {
		// TODO Auto-generated method stub
		return null;
	}

}
