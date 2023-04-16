package controllers;

import entities.Student;
import entities.User;
import java.util.Scanner;


public class LoginController {
	private LoginController() {};
	private static LoginController lc = null;
	public static LoginController getInstance() {
		if (lc == null) {
			lc = new LoginController();
		}
		return lc;
	}
	public User checkStudentID(String id) {
		StudentService sc = StudentService.getInstance();
		User currentUser = null;
		currentUser = sc.getStudentbyID(id);
		return currentUser;
	}
	public User checkFacultyID(String id) {
		FacultyService fc = FacultyService.getInstance();
		User currentUser = null;
		currentUser = fc.getFacultybyID(id);
		return currentUser;
	}
	public boolean isLoggedIn(User user, String password) {
		return user.checkPassword(password);
	}
	
	public void updatePassword(User user, String oldPass, String newPass) {
		if (!user.checkPassword(oldPass)) {
			System.out.println("Error");
			return;
		}
		
		if (user instanceof Student) {
			StudentService sc = StudentService.getInstance();
			sc.updateStudent(user.getUserID(), newPass);
		} else {
			FacultyService fc = FacultyService.getInstance();
			fc.updateFaculty(user.getUserID(), newPass);
		}
	}
}


