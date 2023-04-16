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
		int attempts = 0;
		int capacity = 5;
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
			System.out.println("Enter Password: ");
			input = sc.nextLine();
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
        System.out.println("Welcome, " + currentUser.getName());
        if (currentUser instanceof Coordinator) {
            currentUser = (Coordinator) currentUser;
            CoordinatorMenu cm = CoordinatorMenu.getInstance();
            cm.display((Coordinator)currentUser);
        }
        else if (currentUser instanceof Faculty) {
            currentUser = (Faculty) currentUser;
            FacultyMenu fm = FacultyMenu.getInstance();
            fm.display((Faculty)currentUser);
        }
		
		
		return;
	}
}
