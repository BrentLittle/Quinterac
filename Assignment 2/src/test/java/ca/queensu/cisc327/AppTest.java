package ca.queensu.cisc327;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.*;

import ca.queensu.cisc327.Quinterac;

public class AppTest {
	
	private String outFilePath = "src/test/java/ca/queensu/cisc327/TransactionSummaryFile.txt";
	private String inFilePath = "src/test/java/ca/queensu/cisc327/ValidAccountsFile.txt";
	
	@Test
    public void R1T1() throws Exception {
        runTest(Arrays.asList("logout", "--quit"), Arrays.asList("Please enter a transaction: "
        		, "Cannot logout before login.", "Please enter a transaction: "));
    }
	
	@Test
    public void R1T2() throws Exception {
        runTest(Arrays.asList("createacc", "--quit"), Arrays.asList("Please enter a transaction: "
        		, "Cannot create account while logged out.", "Please enter a transaction: "));
    }
	
	@Test
    public void R1T3() throws Exception {
        runTest(Arrays.asList("deleteacc", "--quit"), Arrays.asList("Please enter a transaction: "
        		, "Cannot delete account while logged out.", "Please enter a transaction: "));
    }
	
	@Test
    public void R1T4() throws Exception {
        runTest(Arrays.asList("deposit", "--quit"), Arrays.asList("Please enter a transaction: "
        		, "Cannot deposit while logged out.", "Please enter a transaction: "));
    }
	
	@Test
    public void R1T5() throws Exception {
        runTest(Arrays.asList("withdraw", "--quit"), Arrays.asList("Please enter a transaction: "
        		, "Cannot withdraw while logged out.", "Please enter a transaction: "));
    }
	
	@Test
    public void R1T6() throws Exception {
        runTest(Arrays.asList("transfer", "--quit"), Arrays.asList("Please enter a transaction: "
        		, "Cannot transfer while logged out.", "Please enter a transaction: "));
    }
	
	@Test
    public void R1T7() throws Exception {
        runTest(Arrays.asList("dog", "--quit"), Arrays.asList("Please enter a transaction: "
        		, "Unrecognized transaction: dog", "Please enter a transaction: "));
    }
	
	@Test
    public void R2T1() throws Exception {
        runTest(Arrays.asList("login", "agent", "--quit"), Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "));
    }
	
	@Test
    public void R3T1() throws Exception {
        runTest(Arrays.asList("login", "machine", "--quit"), Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "));
    }
	
	@Test
    public void R4T1() throws Exception {
        runTest(Arrays.asList("login", "machine", "login", "--quit"), Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: ", "Already logged in.", "Please enter a transaction: "));
    }
	
	@Test
    public void R4T2() throws Exception {
        runTest(Arrays.asList("login", "agent", "login", "--quit"), Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: ", "Already logged in.", "Please enter a transaction: "));
    }
	
	@Test
    public void R5T1() throws Exception {
        runTest(Arrays.asList("login", "machine", "logout", "--quit"), Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Session completed, output printed to " + outFilePath, "Please enter a transaction: "));
    }
	
	@Test
    public void R5T2() throws Exception {
        runTest(Arrays.asList("login", "agent", "logout", "--quit"), Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Session completed, output printed to " + outFilePath, "Please enter a transaction: "));
    }
	
	@Test
    public void R6T1() throws Exception {
        runTest(Arrays.asList("login", "machine", "logout", "logout", "--quit"), Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Session completed, output printed to " + outFilePath, "Please enter a transaction: "
        		, "Cannot logout before login.", "Please enter a transaction: "));
    }
	
	@Test
    public void R6T2() throws Exception {
        runTest(Arrays.asList("login", "machine", "logout", "createacc", "--quit"), Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Session completed, output printed to " + outFilePath, "Please enter a transaction: "
        		, "Cannot create account while logged out.", "Please enter a transaction: "));
    }
	
	@Test
    public void R6T3() throws Exception {
        runTest(Arrays.asList("login", "machine", "logout", "deleteacc", "--quit"), Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Session completed, output printed to " + outFilePath, "Please enter a transaction: "
        		, "Cannot delete account while logged out.", "Please enter a transaction: "));
    }
	
	@Test
    public void R6T4() throws Exception {
        runTest(Arrays.asList("login", "machine", "logout", "deposit", "--quit"), Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Session completed, output printed to " + outFilePath, "Please enter a transaction: "
        		, "Cannot deposit while logged out.", "Please enter a transaction: "));
    }
	
	@Test
    public void R6T5() throws Exception {
        runTest(Arrays.asList("login", "machine", "logout", "withdraw", "--quit"), Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Session completed, output printed to " + outFilePath, "Please enter a transaction: "
        		, "Cannot withdraw while logged out.", "Please enter a transaction: "));
    }
	
	@Test
    public void R6T6() throws Exception {
        runTest(Arrays.asList("login", "machine", "logout", "transfer", "--quit"), Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Session completed, output printed to " + outFilePath, "Please enter a transaction: "
        		, "Cannot transfer while logged out.", "Please enter a transaction: "));
    }
	
	@Test
    public void R6T7() throws Exception {
        runTest(Arrays.asList("login", "machine", "logout", "dog", "--quit"), Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Session completed, output printed to " + outFilePath, "Please enter a transaction: "
        		, "Unrecognized transaction: dog", "Please enter a transaction: "));
    }
	
	/*
	@Test
    public void R2T1() throws Exception {
        runTest(Arrays.asList("dog", "--quit"), Arrays.asList("Please enter a transaction: "
        		, "Unrecognized transaction: dog", "Please enter a transaction: "));
    }*/
    
    public void runTest(List<String> terminal_input, List<String> expected_terminal_tails) throws Exception{
    	
    	// setup parameters for the program to run
        String[] args = { inFilePath, outFilePath };
        
        //setup user input
        String userInput = String.join(System.lineSeparator(), terminal_input);
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);
        
        // setup stdin & stdout:
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        // run the program
        Quinterac.main(args);

        // capture terminal outputs:
        String[] printed_lines = outContent.toString().split("[\r\n]+");

        // compare the tail of the terminal outputs:
        int diff = printed_lines.length - expected_terminal_tails.size();
        for (int i = 0; i < expected_terminal_tails.size(); ++i) {
            assertEquals(expected_terminal_tails.get(i), printed_lines[i + diff]);
        }
    }

    /**
     * Helper function to run the main function and verify the output
     * @param terminal_input A list of string as the terminal input to run the program
     * @param expected_terminal_tails A list of string expected at the tail of terminal output
     * @param expected_output_file A file that contains the expected content for the output file
     * @throws Exception
     */
    public void runAndTest(List<String> terminal_input, List<String> expected_terminal_tails,
            String expected_output_file) throws Exception {

        // setup parameters for the program to run
        // create a temporary file
        File tmpFile = File.createTempFile("temp-test", ".tmp");
        String[] args = { tmpFile.getAbsolutePath() };

        // setup user input
        String userInput = String.join(System.lineSeparator(), terminal_input);
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);

        // setup stdin & stdout:
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        // run the program
        Quinterac.main(args);

        // capture terminal outputs:
        String[] printed_lines = outContent.toString().split("[\r\n]+");

        // compare the tail of the terminal outputs:
        int diff = printed_lines.length - expected_terminal_tails.size();
        for (int i = 0; i < expected_terminal_tails.size(); ++i) {
            assertEquals(expected_terminal_tails.get(i), printed_lines[i + diff]);
        }

        // compare output file content to the expected content
        if (expected_output_file != null) {
            String expected_output = new String(Files.readAllBytes(Paths.get(expected_output_file)), "UTF-8");
            String actual_output = new String(Files.readAllBytes(tmpFile.toPath()), "UTF-8");
            assertEquals(expected_output, actual_output);
        }

    }

    /**
     * Retrieve the absolute path of the files in the resources folder
     * @param relativePath The file's relative path in the resources folder (/test/resources)
     * @return the absolute path of the file in the resource folder. 
     */
    String getFileFromResource(String relativePath) {
        return new File(this.getClass().getResource(relativePath).getFile()).getAbsolutePath();
    }
}
