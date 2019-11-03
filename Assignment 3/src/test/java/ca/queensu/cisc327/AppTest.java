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
	
	//Relative paths to test files based on directory structure
	private String outFilePath = "src/test/java/ca/queensu/cisc327/TransactionSummaryFile.txt";
	private String inFilePath = "src/test/java/ca/queensu/cisc327/ValidAccountsFile.txt";
	private String expectedOutputFolder = "src/test/resources/R2/output/";
	private String inputFolder = "src/test/resources/R2/input/";
	
	/*
	 * R1T1: User cannot logout before logging in
	 */
	@Test
    public void R1T1() throws Exception {
        runTest(Arrays.asList("logout", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Cannot logout before login.", "Please enter a transaction: "));
    }
	
	/*
	 * R1T2: User cannot create account before logging in
	 */
	@Test
    public void R1T2() throws Exception {
        runTest(Arrays.asList("createacc", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Cannot create account while logged out.", "Please enter a transaction: "));
    }
	
	/*
	 * R1T3: User cannot delete account before logging in
	 */
	@Test
    public void R1T3() throws Exception {
        runTest(Arrays.asList("deleteacc", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Cannot delete account while logged out.", "Please enter a transaction: "));
    }
	
	/*
	 * R1T4: User cannot deposit before logging in
	 */
	@Test
    public void R1T4() throws Exception {
        runTest(Arrays.asList("deposit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Cannot deposit while logged out.", "Please enter a transaction: "));
    }
	
	/*
	 * R1T5: User cannot withdraw before logging in 
	 */
	@Test
    public void R1T5() throws Exception {
        runTest(Arrays.asList("withdraw", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Cannot withdraw while logged out.", "Please enter a transaction: "));
    }
	
	/*
	 * R1T6: User cannot transfer before logging in
	 */
	@Test
    public void R1T6() throws Exception {
        runTest(Arrays.asList("transfer", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Cannot transfer while logged out.", "Please enter a transaction: "));
    }
	
	/*
	 * R1T7: Deal with an unknown input
	 */
	@Test
    public void R1T7() throws Exception {
        runTest(Arrays.asList("dog", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Unrecognized transaction: dog", "Please enter a transaction: "));
    }
	
	/*
	 * R2T1: Allows User to log in as Agent
	 */
	@Test
    public void R2T1() throws Exception {
        runTest(Arrays.asList("login", "agent", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "));
    }
	
	/*
	 * R3T1: Allows User to log in as Machine
	 */
	@Test
    public void R3T1() throws Exception {
        runTest(Arrays.asList("login", "machine", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "));
    }
	
	/*
	 * R4T1: Do not allow the user to login once they have already logged in
	 */
	@Test
    public void R4T1() throws Exception {
        runTest(Arrays.asList("login", "machine", "login", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: ", "Already logged in.", "Please enter a transaction: "));
    }
	
	/*
	 * R4T2: Do not allows the user to login once they have already logged in
	 */
	@Test
    public void R4T2() throws Exception {
        runTest(Arrays.asList("login", "agent", "login", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: ", "Already logged in.", "Please enter a transaction: "));
    }
	
	/*
	 * R5T1: Allow the user to logout if they are in an active session
	 */
	@Test
    public void R5T1() throws Exception {
        runTest(Arrays.asList("login", "machine", "logout", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Session completed, output printed to file.", "Please enter a transaction: ")
        		, expectedOutputFolder + "genericLogout.txt");
    }
	
	/*
	 * R5T2: Allow the user to logout if they are in an active session
	 */
	@Test
    public void R5T2() throws Exception {
        runTest(Arrays.asList("login", "agent", "logout", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Session completed, output printed to file.", "Please enter a transaction: ")
        		, expectedOutputFolder + "genericLogout.txt");
    }
	
	/*
	 * R6T1: User cannot logout after logging out
	 */	
	@Test
    public void R6T1() throws Exception {
        runTest(Arrays.asList("login", "machine", "logout", "logout", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Session completed, output printed to file.", "Please enter a transaction: "
        		, "Cannot logout before login.", "Please enter a transaction: "), 
        		
        		expectedOutputFolder + "genericLogout.txt");
    }
	
	/*
	 * R6T2: User cannot create account after logging out
	 */
	@Test
    public void R6T2() throws Exception {
        runTest(Arrays.asList("login", "machine", "logout", "createacc", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Session completed, output printed to file.", "Please enter a transaction: "
        		, "Cannot create account while logged out.", "Please enter a transaction: "), 
        		
        		expectedOutputFolder + "genericLogout.txt");
    }
	
	/*
	 * R6T3: User cannot delete account after logging out 
	 */
	@Test
    public void R6T3() throws Exception {
        runTest(Arrays.asList("login", "machine", "logout", "deleteacc", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Session completed, output printed to file.", "Please enter a transaction: "
        		, "Cannot delete account while logged out.", "Please enter a transaction: "), 
        		
        		expectedOutputFolder + "genericLogout.txt");
    }
	
	/*
	 * R6T4: User cannot deposit after logging out 
	 */
	@Test
    public void R6T4() throws Exception {
        runTest(Arrays.asList("login", "machine", "logout", "deposit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Session completed, output printed to file.", "Please enter a transaction: "
        		, "Cannot deposit while logged out.", "Please enter a transaction: "), 
        		
        		expectedOutputFolder + "genericLogout.txt");
    }
	
	/*
	 * R6T5: User cannot withdraw after logging out
	 */
	@Test
    public void R6T5() throws Exception {
        runTest(Arrays.asList("login", "machine", "logout", "withdraw", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Session completed, output printed to file.", "Please enter a transaction: "
        		, "Cannot withdraw while logged out.", "Please enter a transaction: "), 
        		
        		expectedOutputFolder + "genericLogout.txt");
    }
	
	/*
	 * R6T6: User cannot transfer after logging out
	 */
	@Test
    public void R6T6() throws Exception {
        runTest(Arrays.asList("login", "machine", "logout", "transfer", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Session completed, output printed to file.", "Please enter a transaction: "
        		, "Cannot transfer while logged out.", "Please enter a transaction: "), 
        		
        		expectedOutputFolder + "genericLogout.txt");
    }
	
	/*
	 * R6T7: Deal with an unknown input
	 */
	@Test
    public void R6T7() throws Exception {
        runTest(Arrays.asList("login", "machine", "logout", "dog", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Session completed, output printed to file.", "Please enter a transaction: "
        		, "Unrecognized transaction: dog", "Please enter a transaction: "), 
        		
        		expectedOutputFolder + "genericLogout.txt");
    }
	
	/*
	 * R6T8: User can login after logging out
	 */
	@Test
    public void R6T8() throws Exception {
        runTest(Arrays.asList("login", "machine", "logout", "login", "machine", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Session completed, output printed to file.", "Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "), 
        		
        		expectedOutputFolder + "empty.txt");
    }
	
	/*
	 * R7T1: Cannot create an account if in ATM mode
	 */
	@Test
    public void R7T1() throws Exception {
        runTest(Arrays.asList("login", "machine", "createacc", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Cannot create account while in ATM mode.", "Please enter a transaction: "));
    }
	
	/*
	 * R7T2: Allow the user to create a new account
	 */
	@Test
    public void R7T2() throws Exception {
        runTest(Arrays.asList("login", "agent", "createacc", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Create account selected...", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	/*
	 * R8T1: Ensure that the account number is valid and not already taken 
	 */
	@Test
    public void R8T1() throws Exception {
        runTest(Arrays.asList("login", "agent", "createacc", "9876543", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Create account selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter account name:"
        		, "Please enter a transaction: "));
    }
	
	/*
	 * R8T2: Ensure there is not duplicate account numbers
	 */
	@Test
    public void R8T2() throws Exception {
        runTest(Arrays.asList("login", "agent", "createacc", "1111111", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Create account selected...", "Please enter account number:"
        		, "Error: Account number must not already exist.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	/*
	 * R8T3: Ensure the account number is exactly 7 digits
	 */
	@Test
    public void R8T3() throws Exception {
        runTest(Arrays.asList("login", "agent", "createacc", "123456", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Create account selected...", "Please enter account number:"
        		, "Error: Account numbers must contain 7 digits.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	/*
	 * R8T4: Ensure the account number has exactly 7 digits
	 */
	@Test
    public void R8T4() throws Exception {
        runTest(Arrays.asList("login", "agent", "createacc", "12345678", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Create account selected...", "Please enter account number:"
        		, "Error: Account numbers must contain 7 digits.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	/* 
	 * R8T5: Ensure that the account number does not start with a 0 
	 */
	@Test
    public void R8T5() throws Exception {
        runTest(Arrays.asList("login", "agent", "createacc", "0123456", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Create account selected...", "Please enter account number:"
        		, "Error: Account numbers cannot begin with a 0.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	/* 
	 * R8T6: Ensure that the account number is actually a number
	 */
	@Test
    public void R8T6() throws Exception {
        runTest(Arrays.asList("login", "agent", "createacc", "dog", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Create account selected...", "Please enter account number:"
        		, "Error: Entered account number is in fact, not a number.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	/* 
	 * R9T1: Allow the user to create the account with a name that is valid
	 */
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
	
	/*
	 * R9T2: Ensure the entered account name is not more than 30 characters
	 */
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
	
	/* 
	 * R9T3: Ensure the entered account name has at least 3 characters
	 */
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
	
	/*
	 * R9T4: Ensure the entered account name does not begin with a space
	 */
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
	
	/*
	 * R9T5: Ensure the entered account name does not end with a space
	 */
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
	
	/*
	 * R10T1: Cannot delete an account if in ATM mode
	 */
	@Test
    public void R10T1() throws Exception {
        runTest(Arrays.asList("login", "machine", "deleteacc", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Cannot delete account while in ATM mode.", "Please enter a transaction: "));
    }
	
	/*
	 * R10T2: Allow the user to delete an account
	 */
	@Test
    public void R10T2() throws Exception {
        runTest(Arrays.asList("login", "agent", "deleteacc", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Delete account selected...", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	/*
	 * R11T1: Allow the user to proceed to entering the account name 
	 */
	@Test
    public void R11T1() throws Exception {
        runTest(Arrays.asList("login", "agent", "deleteacc", "1111111", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Delete account selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter account name:"
        		, "Please enter a transaction: "));
    }
	
	/*
	 * R11T2: Ensure we are deleting an account that is currently active
	 */
	@Test
    public void R11T2() throws Exception {
        runTest(Arrays.asList("login", "agent", "deleteacc", "9876543", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Delete account selected...", "Please enter account number:"
        		, "Error: Entered account number does not exist.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	/*
	 * R11T3: Ensure account number cannot be less than 7 digits
	 */
	@Test
    public void R11T3() throws Exception {
        runTest(Arrays.asList("login", "agent", "deleteacc", "123456", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Delete account selected...", "Please enter account number:"
        		, "Error: Account numbers must contain 7 digits.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	/*
	 * R11T4: Ensure account number cannot be more than 7 digits
	 */
	@Test
    public void R11T4() throws Exception {
        runTest(Arrays.asList("login", "agent", "deleteacc", "12345678", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Delete account selected...", "Please enter account number:"
        		, "Error: Account numbers must contain 7 digits.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	/*
	 * R11T5: Ensure account number does not start with 0
	 */
	@Test
    public void R11T5() throws Exception {
        runTest(Arrays.asList("login", "agent", "deleteacc", "0123456", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Delete account selected...", "Please enter account number:"
        		, "Error: Account numbers cannot begin with a 0.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	/*
	 * R11T6: Ensure that the account number is actually a number
	 */
	@Test
    public void R11T6() throws Exception {
        runTest(Arrays.asList("login", "agent", "deleteacc", "dog", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Delete account selected...", "Please enter account number:"
        		, "Error: Entered account number is in fact, not a number.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	/*
	 * R12T1: Allow the account to be deleted given all info was entered correctly
	 */
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
	
	/* 
	 * R12T2: Ensure the name does not contain more than 30 characters
	 */
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
	
	/*
	 * R12T3: Ensure the names contains more than 3 characters
	 */
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
	
	/*
	 * R12T4: Ensure the entered name does not begin with a space
	 */
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
	
	/*
	 * R12T5: Ensure the entered name does not end with a space
	 */
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
	
	/*
	 * R13T1: Any user should be able to use the deposit command
	 */
	@Test
    public void R13T1() throws Exception {
        runTest(Arrays.asList("login", "machine", "deposit", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Deposit selected...", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	/*
	 * R13T2: Any user should be able to use the deposit command
	 */
	@Test
    public void R13T2() throws Exception {
        runTest(Arrays.asList("login", "agent", "deposit", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Deposit selected...", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	/*
	 * R14T1: Verify that the account number is actually valid and exists
	 */
	@Test
    public void R14T1() throws Exception {
        runTest(Arrays.asList("login", "agent", "deposit", "1111111", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Deposit selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter amount:"
        		, "Please enter a transaction: "));
    }
	
	/*
	 * R14T2: Ensure the account number is exactly 7 digits
	 */
	@Test
    public void R14T2() throws Exception {
        runTest(Arrays.asList("login", "agent", "deposit", "9876543", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Deposit selected...", "Please enter account number:"
        		, "Error: Entered account number does not exist.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	/*
	 * R14T3: Ensure the account number is exactly 7 digits
	 */
	@Test
    public void R14T3() throws Exception {
        runTest(Arrays.asList("login", "agent", "deposit", "123456", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Deposit selected...", "Please enter account number:"
        		, "Error: Account numbers must contain 7 digits.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	/*
	 * R14T4: Ensure the account number does not start with a 0
	 */
	@Test
    public void R14T4() throws Exception {
        runTest(Arrays.asList("login", "agent", "deposit", "12345678", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Deposit selected...", "Please enter account number:"
        		, "Error: Account numbers must contain 7 digits.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	/*
	 * R14T5: Ensure the account number is actually a number
	 */
	@Test
    public void R14T5() throws Exception {
        runTest(Arrays.asList("login", "agent", "deposit", "0123456", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Deposit selected...", "Please enter account number:"
        		, "Error: Account numbers cannot begin with a 0.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	/*
	 * R14T6: Ensure that the user cannot deposit into a non-existent account
	 */
	@Test
    public void R14T6() throws Exception {
        runTest(Arrays.asList("login", "agent", "deposit", "dog", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Deposit selected...", "Please enter account number:"
        		, "Error: Entered account number is in fact, not a number.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	/*
	 * R15T1: Ensure that the amount number is actually valid and within deposit constraints for ATM
	 */
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
	
	/*
	 * R15T2: Ensure the user cannot deposit more then $2000 while at an ATM
	 */
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
	
	/*
	 * R15T3: User cannot enter an amount less then 0
	 */
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
	
	/*
	 * R15T4: Ensure that the user cannot deposit an invalid amount
	 */
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
	
	/*
	 * R15T5: Ensure that the daily limit is not exceeded
	 */
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
	
	/*
	 * R16T1: Ensure that the user is entering a valid amount 
	 */
	
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
	
	/*
	 * R16T2: User cannot enter a value greater than 99,999,999
	 */
	
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
	
	/*
	 * R16T3: User cannot enter a value less than 0
	 */
	
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
	
	/*
	 * R16T4: User cannot deposit an invalid amount
	 */
	
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
	
	/*
	 * R17T2: Any user should be able to withdraw
	 */
	
	@Test
    public void R17T1() throws Exception {
        runTest(Arrays.asList("login", "machine", "withdraw", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Withdraw selected...", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	/*
	 * R17T2: Any user should be able to withdraw
	 */
	
	@Test
    public void R17T2() throws Exception {
        runTest(Arrays.asList("login", "agent", "withdraw", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Withdraw selected...", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	/*
	 * R18T1: Verify that the account number is actually valid and exists
	 */
	
	@Test
    public void R18T1() throws Exception {
        runTest(Arrays.asList("login", "agent", "withdraw", "1111111", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Withdraw selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter amount:"
        		, "Please enter a transaction: "));
    }
	
	/*
	* R18T2: Ensure that the user cannot withdraw from a non-existent account
	*/
	
	@Test
    public void R18T2() throws Exception {
        runTest(Arrays.asList("login", "agent", "withdraw", "9876543", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Withdraw selected...", "Please enter account number:"
        		, "Error: Entered account number does not exist.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	/*
	 * R18T3: account number must contain 7 digits
	 */
	@Test
    public void R18T3() throws Exception {
        runTest(Arrays.asList("login", "agent", "withdraw", "123456", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Withdraw selected...", "Please enter account number:"
        		, "Error: Account numbers must contain 7 digits.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	/*
	 * R18T4: account number must contain 7 digits
	 */
	
	@Test
    public void R18T4() throws Exception {
        runTest(Arrays.asList("login", "agent", "withdraw", "12345678", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Withdraw selected...", "Please enter account number:"
        		, "Error: Account numbers must contain 7 digits.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	/*
	 * R18T5:  account number cannot start with 0 
	 */
	@Test
    public void R18T5() throws Exception {
        runTest(Arrays.asList("login", "agent", "withdraw", "0123456", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Withdraw selected...", "Please enter account number:"
        		, "Error: Account numbers cannot begin with a 0.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	/*
	 * R18T6: Ensure the account number is actually a number
	 */
	
	@Test
    public void R18T6() throws Exception {
        runTest(Arrays.asList("login", "agent", "withdraw", "dog", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Withdraw selected...", "Please enter account number:"
        		, "Error: Entered account number is in fact, not a number.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	/*
	 * R19T1: Ensure that the amount number is actually valid and within deposit constraints for ATM
	 */
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
	
	/*
	 * R19T2: Ensure the user cannot withdraw more then $1000 while at an ATM
	 */
	
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
	
	/*
	 * R19T3: withdrawn amount cannot be less than 0
	 */
	
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
	
	/*
	 * R19T4: withdrawn amount must be an integer value
	 */
	
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
	
	/*
	 * R19T5: user cannot withdraw more than $5000 in a single day
	 */
	
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
	
	/*
	 * R20T1: User must enter a valid amount
	 */
	
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
	
	/*
	 * R20T2: User cannot enter a value greater than 99,999,999  
	 */
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
	
	/*
	 * R20T3: User cannot enter a value less than 0 
	 */
	
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
	
	/*
	 * R20T4: User cannot deposit an invalid amount, amount must be integer type
	 */
	
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
	
	/*
	 * R21T1: session transfer, all users must be allowed to transfer
	 */
	
	@Test
    public void R21T1() throws Exception {
        runTest(Arrays.asList("login", "machine", "transfer", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Transfer selected...", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	/*
	 * R21T2: session transfer, agent user should be allowed to transfer
	 */
	
	@Test
    public void R21T2() throws Exception {
        runTest(Arrays.asList("login", "agent", "transfer", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Transfer selected...", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	/*
	 * R22T1: transfer to valid account number 
			 account number must be valid and exist
	 */
	
	@Test
    public void R22T1() throws Exception {
        runTest(Arrays.asList("login", "agent", "transfer", "1111111", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Transfer selected...", "Please enter account number:"
        		, "Account number confirmed...", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	/*
	 * R22T2: user cannot transfer to a non-existent account
	 */
	@Test
    public void R22T2() throws Exception {
        runTest(Arrays.asList("login", "agent", "transfer", "9876543", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Transfer selected...", "Please enter account number:"
        		, "Error: Entered account number does not exist.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	/*
	 * R22T3: transfer to invalid account number more than 7 digits 
			 account number must be exactly 7 digits long
	 */
	@Test
    public void R22T3() throws Exception {
        runTest(Arrays.asList("login", "agent", "transfer", "123456", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Transfer selected...", "Please enter account number:"
        		, "Error: Account numbers must contain 7 digits.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	/*
	 * R22T4: Account number must contain 7 digits
	 */
	@Test
    public void R22T4() throws Exception {
        runTest(Arrays.asList("login", "agent", "transfer", "12345678", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Transfer selected...", "Please enter account number:"
        		, "Error: Account numbers must contain 7 digits.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	/*
	 * R22T5: account number cannot start with 0
	 */
	
	
	@Test
    public void R22T5() throws Exception {
        runTest(Arrays.asList("login", "agent", "transfer", "0123456", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Transfer selected...", "Please enter account number:"
        		, "Error: Account numbers cannot begin with a 0.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	/*
	 * R22T6: account number must be a number 
	 */
	
	@Test
    public void R22T6() throws Exception {
        runTest(Arrays.asList("login", "agent", "transfer", "dog", "--exit", "--quit"), 
        		
        		Arrays.asList("Please enter a transaction: "
        		, "Please enter login mode: ", "Please enter a transaction: "
        		, "Transfer selected...", "Please enter account number:"
        		, "Error: Entered account number is in fact, not a number.", "Please enter account number:"
        		, "Please enter a transaction: "));
    }
	
	/*
	 * R23T1: Verify that the account number is actually valid and exists
	 */

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
	
	/*
	 *R23T2: transfer from invalid account number less than 7 digits
			account number must be exactly 7 digits
	 */
	
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
	
	/*
	 * R23T3: transfer from invalid account number more than 7 digits 
			 account number must be exactly 7 digits long
	 */
	
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
	
	/*
	 * R23T4: account number must contain 7 digits
	 */ 
	
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
	
	/*
	 * R23T5: account number cannot start with a 0
	 */	
	
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
	
	/*
	 * R23T6: entered account number must be a number
	 */
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
	
	/*
	 * R23T7: user cannot transfer to the their own account.
	 */
	
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
	
	/*
	 * R24T1: Ensure that the amount number is actually valid and within transfer constraint for ATM
	 */
	
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
	
	/*
	 * R24T2: user cannot transfer more then $10,000 while at an ATM
	 */
	
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
	
	/*
	 * R24T3: User cannot enter a transfer amount less then 0 
	 */
	
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
	
	/*
	 * R24T4: Ensure that the user cannot transfer an invalid amount, must be integer type
	 */
	
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
	
	/*
	 * R24T5: Ensure user cannot transfer more than $10,000 in a single day
	 */
	
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
	
	/*
	 * R25T1: Ensure that the amount number is actually valid and within transfer constraints for Agent  
	 */
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
	
	/*
	 * R25T2: Ensure the user cannot transfer more then $99,999.99 while in agent mode
	 */
	
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
	
	/*
	 * R25T3: User cannot enter a transfer amount less then 0  
	 */
	
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
	
	/*
	 * R25T4: Ensure that the user cannot transfer an invalid amount 
	 */
	
	
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
	
    /**
     * Used for testing the input file, which is supplied as an argument in Main, in the testing framework
     * @param inputFile: the file name of the input file to be supplied as an argument in Main
     *        expected_terminal_tails: a list of String outputs expected from the mock terminal,
     * @return void
     */
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
    
    /**
     * Supplies the list of test inputs and expected outputs to the testing framework.
     * @param terminal_input: a list of String arguments to be passed through the mock terminal,
     *        expected_terminal_tails: a list of String outputs expected from the mock terminal,
     * @return void
     */
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
     * Overloaded with extra output file parameter
     * @param terminal_input: a list of String arguments to be passed through the mock terminal,
     *        expected_terminal_tails: a list of String outputs expected from the mock terminal,
     *        expected_out_file: the file name of the expected output file
     * @return void
     */
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
