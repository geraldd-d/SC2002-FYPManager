package boundaries;

import java.util.Scanner;

import controllers.LoginController;
import entities.Student;
import entities.User;

public class StudentLoginMenu {
	private static StudentLoginMenu slm = null;
	private StudentLoginMenu() {};
	public static StudentLoginMenu getInstance() {
		if (slm == null) {
			slm = new StudentLoginMenu();
		}
		return slm;
	}
	public void display() {
		LoginMenu lm = LoginMenu.getInstance();
		LoginController lc = LoginController.getInstance();
		Scanner sc = new Scanner(System.in);
		String input;
		User currentUser = null;
		boolean auth = false;
		do {
			System.out.println("Enter UserID (lowercase) or leave empty to return: ");
			input = sc.nextLine();
			if(input.equals("")) {
				lm.display();
				return;
			}
			currentUser = lc.checkStudentID(input);
			if (currentUser == null) {
				System.out.println("Invalid User.");
			}
		} while (currentUser == null);
		do {
			System.out.println("Enter Password: ");
			input = sc.nextLine();
			auth = lc.isLoggedIn(currentUser, input);
			if (!auth) {
				System.out.println("Invalid Password.");
			}
		} while (!auth);
		if (currentUser instanceof Student) {
            currentUser = (Student) currentUser;
            System.out.println("Welcome, " + currentUser.getName());
            /// display student menu
            StudentMenu sm = StudentMenu.getInstance();
            sm.display((Student)currentUser);
        }
		return;
	}
}
