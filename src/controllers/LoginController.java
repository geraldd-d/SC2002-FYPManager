package controllers;

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
	private User checkStudentID(String id) {
		AccountsController ac = AccountsController.getInstance();
		User currentUser = null;
		currentUser = ac.authStudent(id);
		return currentUser;
	}
	private User checkFacultyID(String id) {
		AccountsController ac = AccountsController.getInstance();
		User currentUser = null;
		currentUser = ac.authFaculty(id);
		return currentUser;
	}
	private boolean isLoggedIn(User user, String password) {
		return user.checkPassword(password);
	}
	public User handleStudentLogin() {
		Scanner sc = new Scanner(System.in);
		String input;
		User currentUser = null;
		boolean auth = false;
		do {
			System.out.println("Enter UserID (lowercase) or leave empty to return: ");
			input = sc.nextLine();
			if(input == "") {
				return null;
			}
			currentUser = checkStudentID(input);
			if (currentUser == null) {
				System.out.println("Invalid User.");
			}
		} while (currentUser == null);
		do {
			System.out.println("Enter Password: ");
			input = sc.nextLine();
			auth = isLoggedIn(currentUser, input);
			if (!auth) {
				System.out.println("Invalid Password.");
			}
		} while (!auth);
		return currentUser;
	}
	public User handleFacultyLogin() {
		Scanner sc = new Scanner(System.in);
		String input;
		User currentUser = null;
		boolean auth = false;
		do {
			System.out.println("Enter UserID (lowercase) or leave empty to return: ");
			input = sc.nextLine();
			if(input == "") {
				return null;
			}
			currentUser = checkFacultyID(input);
			if (currentUser == null) {
				System.out.println("Invalid User.");
			}
		} while (currentUser == null);
		do {
			System.out.println("Enter Password: ");
			input = sc.nextLine();
			auth = isLoggedIn(currentUser, input);
			if (!auth) {
				System.out.println("Invalid Password.");
			}
		} while (!auth);
		return currentUser;
	}
}