package controllers;
import java.io.*;
import java.util.HashMap;
import entities.User;
import java.util.regex.*;
import entities.*;

/**
 * This class is the controller for the User class.
 */
public class AccountsController {
	private HashMap<String, User> studentList;
	private HashMap<String, User> facultyList;
	private static AccountsController acc = null;
	
	private AccountsController(){
		HashMap<String,User> studentData = getStudentAccounts();
		HashMap<String,User> facultyData = getFacultyAccounts();
		this.studentList = studentData;
		this.facultyList = facultyData;
		StudentController.getInstance(studentData);
		FacultyController.getInstance(facultyData);
		ProjectsController.getInstance();
	}
	
	/**
	 * This method is used to get the instance of the AccountsController class. It is a singleton class.
	 * @return
	 */
	public static AccountsController getInstance(){
		if (acc == null) {
			acc = new AccountsController();
		}
		return acc;
	}
	
	private static String studentsPath = System.getProperty("user.dir") + "//data//studentsList.csv";
	private static String facultyPath = System.getProperty("user.dir") + "//data//facultyList.csv";

	public static final String delimiter = ";";
	
	private static final Pattern pattern = Pattern.compile("^(.+)@.*$");
	
	/**
	 * This method is used to get the student accounts from the file.
	 * @return The list of student accounts in the system.
	 */
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
				if (name.equals("Name") || name.equals("sep=")) {
					continue;
				}
				String email = fields[1];
				Matcher m = pattern.matcher(email);
				if (m.find()) {
					String password;
					userID = m.group(1).toLowerCase();
					if (fields[3].equals(" ")) {
						password = HashService.hashPassword(fields[2], userID);
					}
					else {
						password = fields[2];
					}
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
		updateStudents(studentsPath, studentData);
		return studentData;
	}
	
	/**
	 * This method is used to get the faculty accounts from the file.
	 * @return The list of faculty accounts in the system.
	 */
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
				if (name.equals("Name") || name.equals("sep=")) {
					continue;
				}
				String email = fields[1];
				String role = fields[3];
				//String supervisorID = fields[4];
				Matcher m = pattern.matcher(email);
				if (m.find()) {
					String password;
					String salt;
					userID = m.group(1).toLowerCase();
					if (!fields[4].equals(userID)) {
						salt = userID;
						password = HashService.hashPassword(fields[2], salt);
					}
					else {
						password = fields[2];
					}
					if (role.equals("Faculty")) {
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
		updateFaculty(facultyPath, facultyData);
		return facultyData;
	}
	/**
	 * This method is used to update the student accounts in the file.
	 * @param filepath The path of the file.
	 * @param accounts The hashmap of student accounts.
	 */
	private void updateStudents(String filepath, HashMap<String, User> accounts) {
	    try {
	    	String tempFilePath = filepath + ".tmp";
	    	File tempFile = new File(tempFilePath);
	        FileWriter writer = new FileWriter(tempFilePath);
	        writer.append("sep=;");
	        writer.append("\n");
	        writer.append("Name");
	        writer.append(delimiter);
	        writer.append("Email");
	        writer.append(delimiter);
	        writer.append("Password");
	        writer.append(delimiter);
	        writer.append("UserID");
	        writer.append("\n");
	        for (User account : accounts.values()) {
	            writer.append(account.getName());
	            writer.append(delimiter);
	            writer.append(account.getEmail());
	            writer.append(delimiter);
	            writer.append(account.getPassword());
	            writer.append(delimiter);
	            writer.append(account.getUserID());
	            writer.append("\n");
	        }
	        writer.flush();
	        writer.close();
	        File origFile = new File(filepath);
	        origFile.delete();
	        tempFile.renameTo(origFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	/**
	 * This method is used to update the faculty accounts in the file.
	 * @param filepath The path of the file.
	 * @param accounts The hashmap of faculty accounts.
	 */
	private void updateFaculty(String filepath, HashMap<String, User> accounts) {
	    try {
	    	String tempFilePath = filepath + ".tmp";
	    	File tempFile = new File(tempFilePath);
	        FileWriter writer = new FileWriter(tempFilePath);
	        writer.append("sep=;");
	        writer.append("\n");
	        writer.append("Name");
	        writer.append(delimiter);
	        writer.append("Email");
	        writer.append(delimiter);
	        writer.append("Password");
	        writer.append(delimiter);
	        writer.append("Role");
	        writer.append(delimiter);
	        writer.append("UserID");
	        writer.append("\n");
	        for (User account : accounts.values()) {
	        	String role = (account instanceof Coordinator)? "Coordinator":"Faculty";
	            writer.append(account.getName());
	            writer.append(delimiter);
	            writer.append(account.getEmail());
	            writer.append(delimiter);
	            writer.append(account.getPassword());
	            writer.append(delimiter);
	            writer.append(role);
	            writer.append(delimiter);
	            writer.append(account.getUserID());
	            writer.append("\n");
	        }
	        writer.flush();
	        writer.close();
	        File origFile = new File(filepath);
	        origFile.delete();
	        tempFile.renameTo(origFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	/**
	 * This method is used to update the student account in the file.
	 * @param user The student account.
	 * @param pw The new password.
	 */
	protected void updateSAccount(User user, String pw) {
		try {
            File inputFile = new File(studentsPath);
            File tempFile = new File("temp.csv");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] fields = currentLine.split(delimiter);
                if (fields[0].equals("Name") || fields[0].equals("sep=")) {
					continue;
				}
                if (fields[3].equals(user.getUserID())) {
                	fields[2] = HashService.hashPassword(pw, fields[3]);
                    currentLine = String.join(delimiter, fields);
                }
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
            
            inputFile.delete();
            tempFile.renameTo(inputFile);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
		studentList = getStudentAccounts();
		StudentController.getInstance(studentList);
	}
	/**
	 * This method is used to update the faculty account in the file.
	 * @param user The faculty account.
	 * @param pw The new password.
	 */
	protected void updateFAccount(User user, String pw) {
		try {
            File inputFile = new File(facultyPath);
            File tempFile = new File("temp.csv");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] fields = currentLine.split(delimiter);
                if (fields[0].equals("Name") || fields[0].equals("sep=")) {
					continue;
				}
                if (fields[4].equals(user.getUserID())) {
                	fields[2] = HashService.hashPassword(pw, fields[4]);
                    currentLine = String.join(delimiter, fields);
                }
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
            
            inputFile.delete();
            tempFile.renameTo(inputFile);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
		facultyList = getFacultyAccounts();
		FacultyController.getInstance(facultyList);
	}
}
