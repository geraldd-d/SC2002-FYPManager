package controllers;

import entities.Student;
import entities.User;

/**
 * This class is the controller for the Login class.
 */
public class LoginController {
	private LoginController() {};
	private static LoginController lc = null;

	/**
	 * This method is used to get the instance of the LoginController class. It is a singleton class.
	 * @return The instance of the LoginController class.
	 */
	public static LoginController getInstance() {
		if (lc == null) {
			lc = new LoginController();
		}
		return lc;
	}

	/**
	 * This method is used to check the ID of the student.
	 * @param id The ID of the student to be checked.
	 * @return The student with the ID.
	 */
	public User checkStudentID(String id) {
		StudentController sc = StudentController.getInstance();
		User currentUser = null;
		currentUser = sc.getStudentbyID(id);
		return currentUser;
	}
	/**
	 * This method is used to check the ID of the faculty.
	 * @param id The ID of the faculty to be checked.
	 * @return The faculty with the ID.
	 */
	public User checkFacultyID(String id) {
		FacultyController fc = FacultyController.getInstance();
		User currentUser = null;
		currentUser = fc.getFacultybyID(id);
		return currentUser;
	}
	/**
	 * This method is used to check if the user is logged in. 
	 * @param user The user to be checked.
	 * @param password The password of the user.
	 * @return True if the user is logged in, false otherwise.
	 */
	public boolean isLoggedIn(User user, String password) {
		return user.checkPassword(password);
	}
	/**
	 * This method is used to update the password of the user.
	 * @param user The user whose password is to be updated.
	 * @param oldPass The old password of the user.
	 * @param newPass The new password of the user.
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


