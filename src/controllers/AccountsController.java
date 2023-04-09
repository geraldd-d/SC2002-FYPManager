package controllers;
import java.io.*;
import java.util.HashMap;
import entities.User;
import java.util.regex.*;
import entities.*;


public class AccountsController {
	private HashMap<String, User> studentList;
	private HashMap<String, User> facultyList;
	private static AccountsController acc = null;
	
	private AccountsController(){
		HashMap<String,User> studentData = getStudentAccounts();
		HashMap<String,User> facultyData = getFacultyAccounts();
		this.studentList = studentData;
		this.facultyList = facultyData;
		StudentController sc = StudentController.getInstance(studentData);
		FacultyController fc = FacultyController.getInstance(facultyData);
		ProjectsController pcc = ProjectsController.getInstance(facultyData);
		
		
	}
	
	public static AccountsController getInstance(){
		if (acc == null) {
			acc = new AccountsController();
		}
		return acc;
	}
	
	private static String studentsPath = System.getProperty("user.dir") + "//data//studentsList.csv";
	private static String facultyPath = System.getProperty("user.dir") + "//data//facultyList.csv";

	public static final String delimiter = ",";
	
	private static final Pattern pattern = Pattern.compile("^(.+)@.*$");
	
	private HashMap<String, User> getStudentAccounts(){
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
				String name = fields[0];
				if (name.equals("Name")) {
					continue;
				}
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
	
	private HashMap<String, User> getFacultyAccounts(){
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
				String name = fields[0];
				if (name.equals("Name")) {
					continue;
				}
				String email = fields[1];
				String password = fields[2];
				String role = fields[3];
				//String supervisorID = fields[4];
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
	
	
	/*
	 * checking password has to be a User class method because salt is specific to each account
	 * upon receiving valid user ID, get Object that user is trying to login to
	 */
}
