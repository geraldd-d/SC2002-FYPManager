package boundaries;
import entities.*;

import java.util.InputMismatchException;
import java.util.Scanner;
import controllers.*;

public class LoginMenu implements BaseMenu{
	private LoginMenu() {};
	private static LoginMenu lm = null;
	public static LoginMenu getInstance() {
		if (lm == null) {
			lm = new LoginMenu();
		}
		return lm;
	}
	public void display(){
		Scanner sc = new Scanner(System.in);
		User currentUser = null;
        int choice = 0;
        LoginController lc = LoginController.getInstance();
        do {
            System.out.println("FYP Management System");
            System.out.println("---------------------");
            System.out.println("1. Student Login");
            System.out.println("2. Faculty Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            try {
            	choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice. Please enter an integer from 1-3.");
                sc.nextLine();
                continue;
            }
            switch (choice) {
                case 1:
                    currentUser = lc.handleStudentLogin();
                    break;
                case 2:
                    currentUser = lc.handleFacultyLogin();
                    break;
                case 3:
                    System.out.println("Thank you for using FYP Management System.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter an integer from 1-3.");
            	}
        	} while (choice != 3 && currentUser == null);
        System.out.println("Successfully logged in.");
        if (currentUser instanceof Student) {
        	currentUser = (Student) currentUser;
        	// display student menu
        }
        if (currentUser instanceof Faculty) {
        	currentUser = (Faculty) currentUser;
        	// display faculty menu
        }
        if (currentUser instanceof Coordinator) {
        	currentUser = (Coordinator) currentUser;
        	// display coordinator menu
        }
        System.out.println("Welcome, " + currentUser.getName());
        FacultyMenu fc = new FacultyMenu();
        fc.display((Faculty)currentUser);
      }
	
	public static void main(String[] args) {
		ProjectsController pcc = ProjectsController.getInstance();
		LoginMenu lm = LoginMenu.getInstance();
		lm.display();
	}
        
}
