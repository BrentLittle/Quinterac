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
	private String expectedOutputFolder = "src/test/resources/R2/output/";
	private String inputFolder = "src/test/resources/R2/input/";
	
	@Test
    public void R1T1() throws Exception {
        runTest(Arrays.asList("logout", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Cannot logout before login.", "Please enter a transaction: "));
    }
	
	@Test
    public void R1T2() throws Exception {
        runTest(Arrays.asList("createacc", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Cannot create account while logged out.", "Please enter a transaction: "));
    }
	
	@Test
    public void R1T3() throws Exception {
        runTest(Arrays.asList("deleteacc", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Cannot delete account while logged out.", "Please enter a transaction: "));
    }
	
	@Test
    public void R1T4() throws Exception {
        runTest(Arrays.asList("deposit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Cannot deposit while logged out.", "Please enter a transaction: "));
    }
	
	@Test
    public void R1T5() throws Exception {
        runTest(Arrays.asList("withdraw", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Cannot withdraw while logged out.", "Please enter a transaction: "));
    }
	
	@Test
    public void R1T6() throws Exception {
        runTest(Arrays.asList("transfer", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Cannot transfer while logged out.", "Please enter a transaction: "));
    }
	
	@Test
    public void R1T7() throws Exception {
        runTest(Arrays.asList("dog", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Unrecognized transaction: dog", "Please enter a transaction: "));
    }
	
	@Test
    public void R2T1() throws Exception {
        runTest(Arrays.asList("login", "agent", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "));
    }
	
	@Test
    public void R3T1() throws Exception {
        runTest(Arrays.asList("login", "machine", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "));
    }
	
	@Test
    public void R4T1() throws Exception {
        runTest(Arrays.asList("login", "machine", "login", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: ", "Already logged in.", "Please enter a transaction: "));
    }
	
	@Test
    public void R4T2() throws Exception {
        runTest(Arrays.asList("login", "agent", "login", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: ", "Already logged in.", "Please enter a transaction: "));
    }
	
	@Test
    public void R5T1() throws Exception {
        runTest(Arrays.asList("login", "machine", "logout", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Session completed, output printed to file.", "Please enter a transaction: ")
        		, expectedOutputFolder + "genericLogout.txt");
    }
	
	@Test
    public void R5T2() throws Exception {
        runTest(Arrays.asList("login", "agent", "logout", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Session completed, output printed to file.", "Please enter a transaction: ")
        		, expectedOutputFolder + "genericLogout.txt");
    }
	
	@Test
    public void R6T1() throws Exception {
        runTest(Arrays.asList("login", "machine", "logout", "logout", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Session completed, output printed to file.", "Please enter a transaction: "
        		, "Cannot logout before login.", "Please enter a transaction: "), 
        		
        		expectedOutputFolder + "genericLogout.txt");
    }
	
	@Test
    public void R6T2() throws Exception {
        runTest(Arrays.asList("login", "machine", "logout", "createacc", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Session completed, output printed to file.", "Please enter a transaction: "
        		, "Cannot create account while logged out.", "Please enter a transaction: "), 
        		
        		expectedOutputFolder + "genericLogout.txt");
    }
	
	@Test
    public void R6T3() throws Exception {
        runTest(Arrays.asList("login", "machine", "logout", "deleteacc", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Session completed, output printed to file.", "Please enter a transaction: "
        		, "Cannot delete account while logged out.", "Please enter a transaction: "), 
        		
        		expectedOutputFolder + "genericLogout.txt");
    }
	
	@Test
    public void R6T4() throws Exception {
        runTest(Arrays.asList("login", "machine", "logout", "deposit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Session completed, output printed to file.", "Please enter a transaction: "
        		, "Cannot deposit while logged out.", "Please enter a transaction: "), 
        		
        		expectedOutputFolder + "genericLogout.txt");
    }
	
	@Test
    public void R6T5() throws Exception {
        runTest(Arrays.asList("login", "machine", "logout", "withdraw", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Session completed, output printed to file.", "Please enter a transaction: "
        		, "Cannot withdraw while logged out.", "Please enter a transaction: "), 
        		
        		expectedOutputFolder + "genericLogout.txt");
    }
	
	@Test
    public void R6T6() throws Exception {
        runTest(Arrays.asList("login", "machine", "logout", "transfer", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Session completed, output printed to file.", "Please enter a transaction: "
        		, "Cannot transfer while logged out.", "Please enter a transaction: "), 
        		
        		expectedOutputFolder + "genericLogout.txt");
    }
	
	@Test
    public void R6T7() throws Exception {
        runTest(Arrays.asList("login", "machine", "logout", "dog", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Session completed, output printed to file.", "Please enter a transaction: "
        		, "Unrecognized transaction: dog", "Please enter a transaction: "), 
        		
        		expectedOutputFolder + "genericLogout.txt");
    }
	
	@Test
    public void R6T8() throws Exception {
        runTest(Arrays.asList("login", "machine", "logout", "login", "machine", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Session completed, output printed to file.", "Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "), 
        		
        		expectedOutputFolder + "empty.txt");
    }
	
	@Test
    public void R7T1() throws Exception {
        runTest(Arrays.asList("login", "machine", "createacc", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Cannot create account while in ATM mode.", "Please enter a transaction: "));
    }
	
	@Test
    public void R7T2() throws Exception {
        runTest(Arrays.asList("login", "agent", "createacc", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Create account selected...", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R8T1() throws Exception {
        runTest(Arrays.asList("login", "agent", "createacc", "9876543", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Create account selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter account name:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R8T2() throws Exception {
        runTest(Arrays.asList("login", "agent", "createacc", "1111111", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Create account selected...", "Please enter account number:"
        		, "Error: Account number must not already exist.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R8T3() throws Exception {
        runTest(Arrays.asList("login", "agent", "createacc", "123456", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Create account selected...", "Please enter account number:"
        		, "Error: Account numbers must contain 7 digits.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R8T4() throws Exception {
        runTest(Arrays.asList("login", "agent", "createacc", "12345678", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Create account selected...", "Please enter account number:"
        		, "Error: Account numbers must contain 7 digits.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R8T5() throws Exception {
        runTest(Arrays.asList("login", "agent", "createacc", "0123456", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Create account selected...", "Please enter account number:"
        		, "Error: Account numbers cannot begin with a 0.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R8T6() throws Exception {
        runTest(Arrays.asList("login", "agent", "createacc", "dog", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Create account selected...", "Please enter account number:"
        		, "Error: Entered account number is in fact, not a number.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R9T1() throws Exception {
        runTest(Arrays.asList("login", "agent", "createacc", "9876543", "Joe Smith", "logout", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Create account selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter account name:"
        		, "Account name confirmed...", "Successfully created account 9876543 belonging to Joe Smith."
        		, "Please enter a transaction: ", "Session completed, output printed to file.", "Please enter a transaction: "),
        		
        		expectedOutputFolder + "R9T1_expected.txt");
    }
	
	@Test
    public void R9T2() throws Exception {
        runTest(Arrays.asList("login", "agent", "createacc", "9876543"
        		, "This is a long name with more than 30 characters", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Create account selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter account name:"
        		, "Error: Account name must be between 3 and 30 characters (inclusive)."
        		, "Please enter account name:", "Please enter a transaction: "));
    }
	
	@Test
    public void R9T3() throws Exception {
        runTest(Arrays.asList("login", "agent", "createacc", "9876543", "ah", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Create account selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter account name:"
        		, "Error: Account name must be between 3 and 30 characters (inclusive)."
        		, "Please enter account name:", "Please enter a transaction: "));
    }
	
	@Test
    public void R9T4() throws Exception {
        runTest(Arrays.asList("login", "agent", "createacc", "9876543", " Joe Smith", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Create account selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter account name:"
        		, "Error: Account name cannot begin or end with white space."
        		, "Please enter account name:", "Please enter a transaction: "));
    }
	
	@Test
    public void R9T5() throws Exception {
        runTest(Arrays.asList("login", "agent", "createacc", "9876543", "Joe Smith ", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Create account selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter account name:"
        		, "Error: Account name cannot begin or end with white space."
        		, "Please enter account name:", "Please enter a transaction: "));
    }
	
	@Test
    public void R10T1() throws Exception {
        runTest(Arrays.asList("login", "machine", "deleteacc", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Cannot delete account while in ATM mode.", "Please enter a transaction: "));
    }
	
	@Test
    public void R10T2() throws Exception {
        runTest(Arrays.asList("login", "agent", "deleteacc", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Delete account selected...", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R11T1() throws Exception {
        runTest(Arrays.asList("login", "agent", "deleteacc", "1111111", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Delete account selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter account name:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R11T2() throws Exception {
        runTest(Arrays.asList("login", "agent", "deleteacc", "9876543", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Delete account selected...", "Please enter account number:"
        		, "Error: Entered account number does not exist.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R11T3() throws Exception {
        runTest(Arrays.asList("login", "agent", "deleteacc", "123456", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Delete account selected...", "Please enter account number:"
        		, "Error: Account numbers must contain 7 digits.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R11T4() throws Exception {
        runTest(Arrays.asList("login", "agent", "deleteacc", "12345678", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Delete account selected...", "Please enter account number:"
        		, "Error: Account numbers must contain 7 digits.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R11T5() throws Exception {
        runTest(Arrays.asList("login", "agent", "deleteacc", "0123456", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Delete account selected...", "Please enter account number:"
        		, "Error: Account numbers cannot begin with a 0.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R11T6() throws Exception {
        runTest(Arrays.asList("login", "agent", "deleteacc", "dog", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Delete account selected...", "Please enter account number:"
        		, "Error: Entered account number is in fact, not a number.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R12T1() throws Exception {
        runTest(Arrays.asList("login", "agent", "deleteacc", "1111111", "Joe Smith", "logout", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Delete account selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter account name:"
        		, "Account name confirmed...", "Successfully deleted account 1111111 belonging to Joe Smith."
        		, "Please enter a transaction: ", "Session completed, output printed to file.", "Please enter a transaction: "),
        		
        		expectedOutputFolder + "R12T1_expected.txt");
    }
	
	@Test
    public void R12T2() throws Exception {
        runTest(Arrays.asList("login", "agent", "deleteacc", "1111111"
        		, "This is a long name with more than 30 characters", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Delete account selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter account name:"
        		, "Error: Account name must be between 3 and 30 characters (inclusive)."
        		, "Please enter account name:", "Please enter a transaction: "));
    }
	
	@Test
    public void R12T3() throws Exception {
        runTest(Arrays.asList("login", "agent", "deleteacc", "1111111", "ah", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Delete account selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter account name:"
        		, "Error: Account name must be between 3 and 30 characters (inclusive)."
        		, "Please enter account name:", "Please enter a transaction: "));
    }
	
	@Test
    public void R12T4() throws Exception {
        runTest(Arrays.asList("login", "agent", "deleteacc", "1111111", " Joe Smith", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Delete account selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter account name:"
        		, "Error: Account name cannot begin or end with white space."
        		, "Please enter account name:", "Please enter a transaction: "));
    }
	
	@Test
    public void R12T5() throws Exception {
        runTest(Arrays.asList("login", "agent", "deleteacc", "1111111", "Joe Smith ", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Delete account selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter account name:"
        		, "Error: Account name cannot begin or end with white space."
        		, "Please enter account name:", "Please enter a transaction: "));
    }
	
	@Test
    public void R13T1() throws Exception {
        runTest(Arrays.asList("login", "machine", "deposit", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Deposit selected...", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R13T2() throws Exception {
        runTest(Arrays.asList("login", "agent", "deposit", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Deposit selected...", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R14T1() throws Exception {
        runTest(Arrays.asList("login", "agent", "deposit", "1111111", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Deposit selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter amount:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R14T2() throws Exception {
        runTest(Arrays.asList("login", "agent", "deposit", "9876543", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Deposit selected...", "Please enter account number:"
        		, "Error: Entered account number does not exist.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R14T3() throws Exception {
        runTest(Arrays.asList("login", "agent", "deposit", "123456", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Deposit selected...", "Please enter account number:"
        		, "Error: Account numbers must contain 7 digits.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R14T4() throws Exception {
        runTest(Arrays.asList("login", "agent", "deposit", "12345678", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Deposit selected...", "Please enter account number:"
        		, "Error: Account numbers must contain 7 digits.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R14T5() throws Exception {
        runTest(Arrays.asList("login", "agent", "deposit", "0123456", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Deposit selected...", "Please enter account number:"
        		, "Error: Account numbers cannot begin with a 0.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R14T6() throws Exception {
        runTest(Arrays.asList("login", "agent", "deposit", "dog", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Deposit selected...", "Please enter account number:"
        		, "Error: Entered account number is in fact, not a number.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R15T1() throws Exception {
        runTest(Arrays.asList("login", "machine", "deposit", "1111111", "2000", "logout", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Deposit selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter amount:"
        		, "Amount confirmed..." , "Successfully deposited 2000 cents into account 1111111."
        		,"Please enter a transaction: ", "Session completed, output printed to file.", "Please enter a transaction: "),
        		
        		expectedOutputFolder + "R15T1_expected.txt");
    }
	
	@Test
    public void R15T2() throws Exception {
        runTest(Arrays.asList("login", "machine", "deposit", "1111111", "300000", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Deposit selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter amount:"
        		, "Error: Entered amount cannot exceed the maximum allowable amount." 
        		, "Please enter amount:", "Please enter a transaction: "));
    }
	
	@Test
    public void R15T3() throws Exception {
        runTest(Arrays.asList("login", "machine", "deposit", "1111111", "-1", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Deposit selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter amount:"
        		, "Error: Entered amount cannot be less than nothing." 
        		, "Please enter amount:", "Please enter a transaction: "));
    }
	
	@Test
    public void R15T4() throws Exception {
        runTest(Arrays.asList("login", "machine", "deposit", "1111111", "dog", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Deposit selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter amount:"
        		, "Error: Entered amount must be an integer." 
        		, "Please enter amount:", "Please enter a transaction: "));
    }
	
	@Test
    public void R15T5() throws Exception {
        runTest(Arrays.asList("login", "machine", "deposit", "1111111", "200000", "deposit"
        		, "1111111", "200000", "deposit", "1111111", "200000", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Deposit selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter amount:"
        		, "Amount confirmed..." , "Successfully deposited 200000 cents into account 1111111.", "Please enter a transaction: "
        		, "Deposit selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter amount:"
        		, "Amount confirmed..." , "Successfully deposited 200000 cents into account 1111111.", "Please enter a transaction: "
        		, "Deposit selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter amount:"
        		, "Error: Entered amount cannot exceed the maximum allowable session amount for this type of transaction."
        		, "Please enter amount:","Please enter a transaction: "),
        		
        		expectedOutputFolder + "R15T5_expected.txt");
    }
	
	@Test
    public void R16T1() throws Exception {
        runTest(Arrays.asList("login", "agent", "deposit", "1111111", "1000000", "logout", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Deposit selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter amount:"
        		, "Amount confirmed..." , "Successfully deposited 1000000 cents into account 1111111."
        		,"Please enter a transaction: ", "Session completed, output printed to file.", "Please enter a transaction: "),
        		
        		expectedOutputFolder + "R16T1_expected.txt");
    }
	
	@Test
    public void R16T2() throws Exception {
        runTest(Arrays.asList("login", "agent", "deposit", "1111111", "100000000", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Deposit selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter amount:"
        		, "Error: Entered amount cannot exceed the maximum allowable amount." 
        		, "Please enter amount:", "Please enter a transaction: "));
    }
	
	@Test
    public void R16T3() throws Exception {
        runTest(Arrays.asList("login", "agent", "deposit", "1111111", "-1", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Deposit selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter amount:"
        		, "Error: Entered amount cannot be less than nothing." 
        		, "Please enter amount:", "Please enter a transaction: "));
    }
	
	@Test
    public void R16T4() throws Exception {
        runTest(Arrays.asList("login", "agent", "deposit", "1111111", "dog", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Deposit selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter amount:"
        		, "Error: Entered amount must be an integer." 
        		, "Please enter amount:", "Please enter a transaction: "));
    }
	
	@Test
    public void R17T1() throws Exception {
        runTest(Arrays.asList("login", "machine", "withdraw", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Withdraw selected...", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R17T2() throws Exception {
        runTest(Arrays.asList("login", "agent", "withdraw", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Withdraw selected...", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R18T1() throws Exception {
        runTest(Arrays.asList("login", "agent", "withdraw", "1111111", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Withdraw selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter amount:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R18T2() throws Exception {
        runTest(Arrays.asList("login", "agent", "withdraw", "9876543", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Withdraw selected...", "Please enter account number:"
        		, "Error: Entered account number does not exist.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R18T3() throws Exception {
        runTest(Arrays.asList("login", "agent", "withdraw", "123456", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Withdraw selected...", "Please enter account number:"
        		, "Error: Account numbers must contain 7 digits.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R18T4() throws Exception {
        runTest(Arrays.asList("login", "agent", "withdraw", "12345678", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Withdraw selected...", "Please enter account number:"
        		, "Error: Account numbers must contain 7 digits.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R18T5() throws Exception {
        runTest(Arrays.asList("login", "agent", "withdraw", "0123456", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Withdraw selected...", "Please enter account number:"
        		, "Error: Account numbers cannot begin with a 0.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R18T6() throws Exception {
        runTest(Arrays.asList("login", "agent", "withdraw", "dog", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Withdraw selected...", "Please enter account number:"
        		, "Error: Entered account number is in fact, not a number.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R19T1() throws Exception {
        runTest(Arrays.asList("login", "machine", "withdraw", "1111111", "2000", "logout", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Withdraw selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter amount:"
        		, "Amount confirmed..." , "Successfully withdrew 2000 cents from account 1111111."
        		,"Please enter a transaction: ", "Session completed, output printed to file.", "Please enter a transaction: "),
        		
        		expectedOutputFolder + "R19T1_expected.txt");
    }
	
	@Test
    public void R19T2() throws Exception {
        runTest(Arrays.asList("login", "machine", "withdraw", "1111111", "300000", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Withdraw selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter amount:"
        		, "Error: Entered amount cannot exceed the maximum allowable amount." 
        		, "Please enter amount:", "Please enter a transaction: "));
    }
	
	@Test
    public void R19T3() throws Exception {
        runTest(Arrays.asList("login", "machine", "withdraw", "1111111", "-1", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Withdraw selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter amount:"
        		, "Error: Entered amount cannot be less than nothing." 
        		, "Please enter amount:", "Please enter a transaction: "));
    }
	
	@Test
    public void R19T4() throws Exception {
        runTest(Arrays.asList("login", "machine", "withdraw", "1111111", "dog", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Withdraw selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter amount:"
        		, "Error: Entered amount must be an integer." 
        		, "Please enter amount:", "Please enter a transaction: "));
    }
	
	@Test
    public void R19T5() throws Exception {
        runTest(Arrays.asList("login", "machine", "withdraw", "1111111", "100000", "withdraw", "1111111", "100000"
        		, "withdraw", "1111111", "100000", "withdraw", "1111111", "100000", "withdraw"
        		, "1111111", "100000", "withdraw", "1111111", "100000", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: "
        		, "Please enter a transaction: "
        		, "Withdraw selected..."
        		, "Please enter account number:"
        		, "Account number confirmed..."
        		, "Please enter amount:"
        		, "Amount confirmed..." 
        		, "Successfully withdrew 100000 cents from account 1111111."
        		, "Please enter a transaction: "
        		, "Withdraw selected..."
        		, "Please enter account number:"
        		, "Account number confirmed..."
        		, "Please enter amount:"
        		, "Amount confirmed..." 
        		, "Successfully withdrew 100000 cents from account 1111111."
        		, "Please enter a transaction: "
        		, "Withdraw selected..."
        		, "Please enter account number:"
        		, "Account number confirmed..."
        		, "Please enter amount:"
        		, "Amount confirmed..." 
        		, "Successfully withdrew 100000 cents from account 1111111."
        		, "Please enter a transaction: "
        		, "Withdraw selected..."
        		, "Please enter account number:"
        		, "Account number confirmed..."
        		, "Please enter amount:"
        		, "Amount confirmed..." 
        		, "Successfully withdrew 100000 cents from account 1111111."
        		, "Please enter a transaction: "
        		, "Withdraw selected..."
        		, "Please enter account number:"
        		, "Account number confirmed..."
        		, "Please enter amount:"
        		, "Amount confirmed..." 
        		, "Successfully withdrew 100000 cents from account 1111111."
        		, "Please enter a transaction: "
        		, "Withdraw selected..."
        		, "Please enter account number:"
        		, "Account number confirmed..."
        		, "Please enter amount:"
        		, "Error: Entered amount cannot exceed the maximum allowable session amount for this type of transaction."
        		, "Please enter amount:"
        		, "Please enter a transaction: "),
        		
        		expectedOutputFolder + "R19T5_expected.txt");
    }
	
	@Test
    public void R20T1() throws Exception {
        runTest(Arrays.asList("login", "agent", "withdraw", "1111111", "500000", "logout", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Withdraw selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter amount:"
        		, "Amount confirmed..." , "Successfully withdrew 500000 cents from account 1111111."
        		,"Please enter a transaction: ", "Session completed, output printed to file.", "Please enter a transaction: "),
        		
        		expectedOutputFolder + "R20T1_expected.txt");
    }
	
	@Test
    public void R20T2() throws Exception {
        runTest(Arrays.asList("login", "agent", "withdraw", "1111111", "100000000", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Withdraw selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter amount:"
        		, "Error: Entered amount cannot exceed the maximum allowable amount." 
        		, "Please enter amount:", "Please enter a transaction: "));
    }
	
	@Test
    public void R20T3() throws Exception {
        runTest(Arrays.asList("login", "agent", "withdraw", "1111111", "-1", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Withdraw selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter amount:"
        		, "Error: Entered amount cannot be less than nothing." 
        		, "Please enter amount:", "Please enter a transaction: "));
    }
	
	@Test
    public void R20T4() throws Exception {
        runTest(Arrays.asList("login", "agent", "withdraw", "1111111", "dog", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Withdraw selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter amount:"
        		, "Error: Entered amount must be an integer." 
        		, "Please enter amount:", "Please enter a transaction: "));
    }
	
	@Test
    public void R21T1() throws Exception {
        runTest(Arrays.asList("login", "machine", "transfer", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Transfer selected...", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R21T2() throws Exception {
        runTest(Arrays.asList("login", "agent", "transfer", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Transfer selected...", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R22T1() throws Exception {
        runTest(Arrays.asList("login", "agent", "transfer", "1111111", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Transfer selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R22T2() throws Exception {
        runTest(Arrays.asList("login", "agent", "transfer", "9876543", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Transfer selected...", "Please enter account number:"
        		, "Error: Entered account number does not exist.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R22T3() throws Exception {
        runTest(Arrays.asList("login", "agent", "transfer", "123456", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Transfer selected...", "Please enter account number:"
        		, "Error: Account numbers must contain 7 digits.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R22T4() throws Exception {
        runTest(Arrays.asList("login", "agent", "transfer", "12345678", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Transfer selected...", "Please enter account number:"
        		, "Error: Account numbers must contain 7 digits.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R22T5() throws Exception {
        runTest(Arrays.asList("login", "agent", "transfer", "0123456", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Transfer selected...", "Please enter account number:"
        		, "Error: Account numbers cannot begin with a 0.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R22T6() throws Exception {
        runTest(Arrays.asList("login", "agent", "transfer", "dog", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Transfer selected...", "Please enter account number:"
        		, "Error: Entered account number is in fact, not a number.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R23T1() throws Exception {
        runTest(Arrays.asList("login", "agent", "transfer", "1111111", "2222222", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Transfer selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter amount:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R23T2() throws Exception {
        runTest(Arrays.asList("login", "agent", "transfer", "1111111", "9876543", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Transfer selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter account number:"
        		, "Error: Entered account number does not exist.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R23T3() throws Exception {
        runTest(Arrays.asList("login", "agent", "transfer", "1111111", "123456", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Transfer selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter account number:"
        		, "Error: Account numbers must contain 7 digits.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R23T4() throws Exception {
        runTest(Arrays.asList("login", "agent", "transfer", "1111111", "12345678", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Transfer selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter account number:"
        		, "Error: Account numbers must contain 7 digits.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R23T5() throws Exception {
        runTest(Arrays.asList("login", "agent", "transfer", "1111111", "0123456", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Transfer selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter account number:"
        		, "Error: Account numbers cannot begin with a 0.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R23T6() throws Exception {
        runTest(Arrays.asList("login", "agent", "transfer", "1111111", "dog", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Transfer selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter account number:"
        		, "Error: Entered account number is in fact, not a number.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R23T7() throws Exception {
        runTest(Arrays.asList("login", "agent", "transfer", "1111111", "1111111", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Transfer selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter account number:"
        		, "Error: From account cannot be the same as to account.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	@Test
    public void R24T1() throws Exception {
        runTest(Arrays.asList("login", "machine", "transfer", "1111111", "2222222", "750000", "logout", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Transfer selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter amount:"
        		, "Amount confirmed..." , "Successfully transferred 750000 cents from account 2222222 to 1111111."
        		,"Please enter a transaction: ", "Session completed, output printed to file.", "Please enter a transaction: "),
        		
        		expectedOutputFolder + "R24T1_expected.txt");
    }
	
	@Test
    public void R24T2() throws Exception {
        runTest(Arrays.asList("login", "machine", "transfer", "1111111", "2222222", "2000000", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Transfer selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter amount:"
        		, "Error: Entered amount cannot exceed the maximum allowable amount." 
        		, "Please enter amount:", "Please enter a transaction: "));
    }
	
	@Test
    public void R24T3() throws Exception {
        runTest(Arrays.asList("login", "machine", "transfer", "1111111", "2222222", "-1", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Transfer selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter amount:"
        		, "Error: Entered amount cannot be less than nothing." 
        		, "Please enter amount:", "Please enter a transaction: "));
    }
	
	@Test
    public void R24T4() throws Exception {
        runTest(Arrays.asList("login", "machine", "transfer", "1111111", "2222222", "dog", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Transfer selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter amount:"
        		, "Error: Entered amount must be an integer." 
        		, "Please enter amount:", "Please enter a transaction: "));
    }
	
	@Test
    public void R24T5() throws Exception {
        runTest(Arrays.asList("login", "machine", "transfer", "1111111", "2222222", "750000"
        		, "transfer", "1111111", "2222222", "750000", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Transfer selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter amount:"
        		, "Amount confirmed..." , "Successfully transferred 750000 cents from account 2222222 to 1111111."
        		, "Please enter a transaction: " , "Transfer selected..."
        		, "Please enter account number:", "Account number confirmed..."
        		, "Please enter account number:", "Account number confirmed..."
        		, "Please enter amount:", "Error: Entered amount cannot exceed the maximum allowable session amount for this type of transaction."
        		, "Please enter amount:", "Please enter a transaction: "),
        		
        		expectedOutputFolder + "R24T5_expected.txt");
    }
	
	@Test
    public void R25T1() throws Exception {
        runTest(Arrays.asList("login", "agent", "transfer", "1111111", "2222222", "7500000", "logout", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Transfer selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter amount:"
        		, "Amount confirmed..." , "Successfully transferred 7500000 cents from account 2222222 to 1111111."
        		,"Please enter a transaction: ", "Session completed, output printed to file.", "Please enter a transaction: "),
        		
        		expectedOutputFolder + "R25T1_expected.txt");
    }
	
	@Test
    public void R25T2() throws Exception {
        runTest(Arrays.asList("login", "machine", "transfer", "1111111", "2222222", "100000000", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Transfer selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter amount:"
        		, "Error: Entered amount cannot exceed the maximum allowable amount." 
        		, "Please enter amount:", "Please enter a transaction: "));
    }
	
	@Test
    public void R25T3() throws Exception {
        runTest(Arrays.asList("login", "machine", "transfer", "1111111", "2222222", "-1", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Transfer selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter amount:"
        		, "Error: Entered amount cannot be less than nothing." 
        		, "Please enter amount:", "Please enter a transaction: "));
    }
	
	@Test
    public void R25T4() throws Exception {
        runTest(Arrays.asList("login", "machine", "transfer", "1111111", "2222222", "dog", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Transfer selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter amount:"
        		, "Error: Entered amount must be an integer." 
        		, "Please enter amount:", "Please enter a transaction: "));
    }
	
	@Test
    public void R26T1() throws Exception {
        fileTest(inputFolder + "InvalidAccountFile1.txt", Arrays.asList("Invalid Account File Recieved"));
    }
	
	@Test
    public void R26T2() throws Exception {
        fileTest(inputFolder + "InvalidAccountFile2.txt", Arrays.asList("Invalid Account File Recieved"));
    }
	
	@Test
    public void R26T3() throws Exception {
        fileTest(inputFolder + "InvalidAccountFile3.txt", Arrays.asList("Invalid Account File Recieved"));
    }
	
	@Test
    public void R26T4() throws Exception {
        fileTest(inputFolder + "InvalidAccountFile4.txt", Arrays.asList("Invalid Account File Recieved"));
    }
	
	public void fileTest(String inputFile, List<String> expected_terminal_tails) throws Exception{
		
		// setup parameters for the program to run
        String[] args = { inputFile, outFilePath };
        
        //setup user input
        String userInput = String.join(System.lineSeparator(), "--quit");
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
    
    public void runTest(List<String> terminal_input, List<String> expected_terminal_tails
    		,String expected_output_file) throws Exception{
    	
    	// setup parameters for the program to run
    	File tmpFile = File.createTempFile("temp-test", ".tmp");
        String[] args = { inFilePath, tmpFile.getAbsolutePath() };
        
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
