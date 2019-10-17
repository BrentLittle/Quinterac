
public class quinterac {

	public static void main(String[] args) {
		FileVerification fileVal = new FileVerification("InvalidAccountFile5.txt");
		boolean fval = fileVal.getFileValid();
		System.out.println(""+fval);
	}

}
