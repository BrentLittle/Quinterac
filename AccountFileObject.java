import java.io.*;
import java.util.*;

public class AccountFileObject {
	
	private ArrayList<Integer> accNums;
	
	public AccountFileObject(String filename) throws IOException, InvalidAccountException {
		FileInputStream input = null;
		try {
			input = new FileInputStream(filename);
		} catch (IOException e) {
			System.out.printf("File error.");
		}
		BufferedReader buffer = new BufferedReader(new InputStreamReader(input));
		String inLine;
		while ((inLine = buffer.readLine()) != null) {
			int accNum = 0;
			if (inLine.charAt(0) == '0') {
				throw new InvalidAccountException("Account file has line begins with zero");
			}
			if (inLine.length() != 7) {
				throw new InvalidAccountException("Account file has non 7-digit account number");
			}
			try {
				accNum = Integer.parseInt(inLine);
			} catch (NumberFormatException e) {
				throw new InvalidAccountException("Account file has line with non-numeric");
			}
			
			// needs more cases
			
			accNums.add(accNum);
		}
	}
	
	public boolean checkDuplicate(int n) {
		for (int i = 0; i < accNums.size(); i++) {
			if (accNums.get(i) == n) {
				return true;
			}
		}
		return false;
	}
		
	public int getAccNum(int index) {
		int n = accNums.get(index);
		return n;
	}
}
