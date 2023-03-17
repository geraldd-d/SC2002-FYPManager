package controllers;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import entities.User;
import java.util.regex.*;
import entities.*;


class AccountsController {
	private HashMap<String, User> studentsList;
	private HashMap<String, User> facultyList;
	private static AccountsController acc = null;
	
	private AccountsController() throws NoSuchAlgorithmException {
		HashMap<String,User> studentData = getStudentAccounts();
		HashMap<String,User> facultyData = getFacultyAccounts();

		this.studentsList = studentData;
		this.facultyList = facultyData;
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
	
	public HashMap<String, User> getFacultyAccounts() throws NoSuchAlgorithmException{
		File file = new File(facultyPath);
		HashMap<String,User> facultyData = new HashMap<>();
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
				String role = fields[3];
				Matcher m = pattern.matcher(email);
				if (m.find()) {
					userID = m.group(1).toLowerCase();
					if (role == "Faculty") {
						Faculty temp = new Faculty(userID,password,name,email);
						facultyData.put(userID, temp);
					}
					else {
						Coordinator temp = new Coordinator(userID,password,name,email);
						facultyData.put(userID, temp);
					}
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
		return facultyData;
	}
	
	public void printStudentList() {
		this.studentsList.entrySet().forEach(user -> {
			System.out.println(user.getKey() + "  " + user.getValue());
		});
	}
	
	public void printFacultyList() {
		this.facultyList.entrySet().forEach(user -> {
			System.out.println(user.getKey() + "  " + user.getValue());
		});
	}
	
	public boolean checkStudentID(String input) {
		return this.studentsList.containsKey(input);
	}
	public boolean checkFacultyID(String input) {
		return this.facultyList.containsKey(input);
	}
	/*
	 * checking password has to be a User class method because salt is specific to each account
	 * upon receiving valid user ID, get Object that user is trying to login to
	 */
}
