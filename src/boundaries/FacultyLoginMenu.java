package boundaries;

import java.util.Scanner;

import controllers.LoginController;
import entities.Coordinator;
import entities.Faculty;
import entities.User;

public class FacultyLoginMenu {
	private static FacultyLoginMenu flm = null;
	private FacultyLoginMenu() {};
	public static FacultyLoginMenu getInstance() {
		if (flm == null) {
			flm = new FacultyLoginMenu();
		}
		return flm;
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
			currentUser = lc.checkFacultyID(input);
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
        System.out.println("Welcome, " + currentUser.getName());
        if (currentUser instanceof Coordinator) {
            currentUser = (Coordinator) currentUser;
            CoordinatorMenu cm = CoordinatorMenu.getInstance();
            cm.display((Coordinator)currentUser);
        }
		if (currentUser instanceof Faculty) {
            currentUser = (Faculty) currentUser;
            FacultyMenu fm = FacultyMenu.getInstance();
            fm.display((Faculty)currentUser);
        }
		
		
		return;
	}
}
