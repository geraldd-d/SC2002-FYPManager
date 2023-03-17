package controllers;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import entities.User;
import java.util.regex.*;
import entities.*;


class AccountsController {
	private HashMap<String, User> studentsList;
	private static AccountsController acc = null;
	
	private AccountsController() throws NoSuchAlgorithmException {
		HashMap<String,User> studentData = getStudentAccounts();
		this.studentsList = studentData;
	}
	
	public static AccountsController getInstance() throws NoSuchAlgorithmException {
		if (acc == null) {
			acc = new AccountsController();
		}
		return acc;
	}
	
	private static String studentsPath = System.getProperty("user.dir") + "\\data\\studentsList.csv";
	private static String facultyPath = System.getProperty("user.dir") + "\\data\\facultyList.csv";

	public static final String delimiter = ",";
	
	private static final Pattern pattern = Pattern.compile("^(.+)@.*$");
	
	public HashMap<String, User> getStudentAccounts() throws NoSuchAlgorithmException{
		File file = new File(studentsPath);
		HashMap<String,User> studentData = new HashMap<>();
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			String[] fields;
			String userID;
			while ((line = br.readLine()) != null) {
				fields = line.split(delimiter);
				if (fields[0] == "Name") {
					continue;
				}
				String name = fields[0];
				String email = fields[1];
				String password = fields[2];
				Matcher m = pattern.matcher(email);
				if (m.find()) {
					userID = m.group(1).toLowerCase();
					Student temp = new Student(userID,password,name,email);
					studentData.put(userID, temp);
				}
			}
			br.close();
		}
		catch (FileNotFoundException fe) {
			fe.printStackTrace();
			System.exit(0);
		}
		catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		return studentData;
	}
	public void printStudentList() {
		this.studentsList.entrySet().forEach(user -> {
			System.out.println(user.getKey() + "  " + user.getValue());
		});
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
		AccountsController ac = AccountsController.getInstance();
		ac.printStudentList();
	}
	
}
