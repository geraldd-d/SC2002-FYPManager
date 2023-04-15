package controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entities.Coordinator;
import entities.Faculty;
import entities.User;

public class FacultyAccountController {
	private static FacultyAccountController fac = null;
	private final FacultyRepository facultyRepository;
	private FacultyAccountController() {
		readFacultyAccounts();
		FacultyService ffc = FacultyService.getInstance();
		this.facultyRepository = FacultyRepository.getInstance();
	}
	public static FacultyAccountController getInstance(){
		if (ffc == null) {
			ffc = new FacultyAccountController();
		}
		return ffc;
	}
	private static String facultyPath = System.getProperty("user.dir") + "//data//facultyList.csv";
	private static final String delimiter = ";";
	private static final Pattern pattern = Pattern.compile("^(.+)@.*$");
	private void readStudentAccounts() {
		File file = new File(facultyPath);
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

				Matcher m = pattern.matcher(email);
				if (m.find()) {
					String password;
					String salt;
					userID = m.group(1).toLowerCase();
					if (fields[4].equals("")) {
						salt = userID;
						password = HashService.hashPassword(fields[2], userID);
					}
					else {
						password = fields[2];
					}
					userID = m.group(1).toLowerCase();
					facultyRepository.createFaculty(userID, password, name, email, role);
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
		updateFaculty(facultyPath, facultyRepository.getFacultyList());
	}
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
	}
}
