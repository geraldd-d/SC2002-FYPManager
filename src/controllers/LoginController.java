package controllers;

import entities.Student;
import entities.User;
import java.util.Scanner;


public class LoginController {
	private LoginController() {};
	private static LoginController lc = null;
	
	/** 
	 * @return LoginController
	 */
	public static LoginController getInstance() {
		if (lc == null) {
			lc = new LoginController();
		}
		return lc;
	}
	
	/** 
	 * @param id
	 * @return User
	 */
	public User checkStudentID(String id) {
		StudentController sc = StudentController.getInstance();
		User currentUser = null;
		currentUser = sc.getStudentbyID(id);
		return currentUser;
	}
	
	/** 
	 * @param id
	 * @return User
	 */
	public User checkFacultyID(String id) {
		FacultyController fc = FacultyController.getInstance();
		User currentUser = null;
		currentUser = fc.getFacultybyID(id);
		return currentUser;
	}
	
	/** 
	 * @param user
	 * @param password
	 * @return boolean
	 */
	public boolean isLoggedIn(User user, String password) {
		return user.checkPassword(password);
	}
	
	
	/** 
	 * @param user
	 * @param oldPass
	 * @param newPass
	 */
	public void updatePassword(User user, String oldPass, String newPass) {
		if (!user.checkPassword(oldPass)) {
			System.out.println("Error");
			return;
		}
		AccountsController ac = AccountsController.getInstance();
		if (user instanceof Student) {
			ac.updateSAccount(user, newPass);
		} else {
			ac.updateFAccount(user, newPass);
		}
	}
}


