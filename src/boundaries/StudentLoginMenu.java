package boundaries;

import java.util.Scanner;

import controllers.LoginController;
import entities.Student;
import entities.User;

/**
 * This class is the boundary for the StudentLoginMenu class.
 */
public class StudentLoginMenu {
	private static StudentLoginMenu slm = null;
	private StudentLoginMenu() {};
	
	/** 
	 * @return StudentLoginMenu
	 */
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
		int attempts = 0;
		int capacity = 5;
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
				attempts++;
				System.out.println((capacity - attempts) + " attempts remaining");
			}
		} while (currentUser == null && attempts < 5);
		if (attempts >= 5) {
			System.out.println("Maximum number of attempts reached. Shutting down...");
			System.exit(0);
		}
		attempts = 0;
		do {
			System.out.println("Enter Password or leave empty to return: ");
			input = sc.nextLine();
			if(input.equals("")) {
				lm.display();
				return;
			}
			auth = lc.isLoggedIn(currentUser, input);
			if (!auth) {
				System.out.println("Invalid Password.");
				attempts++;
				System.out.println((capacity - attempts) + " attempts remaining");
			}
		} while (!auth && attempts < 5);
		if (attempts >= 5) {
			System.out.println("Maximum number of attempts reached. Shutting down...");
			System.exit(0);
		}
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
