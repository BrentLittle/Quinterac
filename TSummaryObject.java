import java.io.*;
import java.util.*;

public class TSummaryObject {
	
	private String outfile;
	
	private Stack<String> outStrings;

	public TSummaryObject(String outfile) throws IOException {
		this.outfile = outfile;
		outStrings.push("EOS");
	}
	
	public void stackPush(String s) {
		outStrings.push(s);
	}
	
	public void createFile() throws IOException {
		FileWriter output = new FileWriter(outfile);
		while (!outStrings.isEmpty()) {
			String strToWrite = outStrings.pop();
			output.write(strToWrite);
			}
		output.close();
	}
}
