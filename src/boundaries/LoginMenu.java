package boundaries;
import entities.*;

import java.util.InputMismatchException;
import java.util.Scanner;
import controllers.*;

/**
 * This class is the boundary for the LoginMenu class.
 */
public class LoginMenu implements BaseMenu{
	private LoginMenu() {};
	private static LoginMenu lm = null;
	
    /** 
     * @return LoginMenu
     */
    public static LoginMenu getInstance() {
		if (lm == null) {
			lm = new LoginMenu();
		}
		return lm;
	}
	public void display(){
		Scanner sc = new Scanner(System.in);
        int choice = 0;
        LoginController lc = LoginController.getInstance();
        do {
        	int width = 42;
            int height = 6;

            // Create the top border of the box
            System.out.print("\u2554"); // top-left corner
            for (int i = 0; i < width - 2; i++) {
                System.out.print("\u2550"); // horizontal line
            }
            System.out.println("\u2557"); // top-right corner

            // Create the sides of the box
            System.out.print("\u2551"); // left vertical line
            System.out.print("   FYP Management System");
            for (int i = 0; i < width - "  FYP Management System".length() - 3; i++) {
                System.out.print(" ");
            }
            System.out.println("\u2551"); // right vertical line

            System.out.print("\u2551"); // left vertical line
            for (int i = 0; i < width - 2; i++) {
                System.out.print("\u2500"); // horizontal line
            }
            System.out.println("\u2551"); // right vertical line

            System.out.println("\u2551 1. Student Login                       \u2551"); // menu options
            System.out.println("\u2551 2. Faculty Login                       \u2551");
            System.out.println("\u2551 3. Exit                                \u2551");

            // Create the bottom border of the box
            System.out.print("\u255A"); // bottom-left corner
            for (int i = 0; i < width - 2; i++) {
                System.out.print("\u2550"); // horizontal line
            }
            System.out.println("\u255D"); // bottom-right corner

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
                    StudentLoginMenu slm = StudentLoginMenu.getInstance();
                    slm.display();
                    break;
                case 2:
                	FacultyLoginMenu flm = FacultyLoginMenu.getInstance();
                    flm.display();                    
                    break;
                case 3:
                    System.out.println("Thank you for using FYP Management System.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter an integer from 1-3.");
            	}
        	} while (choice != 3);
        sc.close();
      }
	
	
    /** 
     * @param args
     */
    public static void main(String[] args) {
		AccountsController acc = AccountsController.getInstance();
		RequestController rc = RequestController.getInstance();
		LoginMenu lm = LoginMenu.getInstance();
		lm.display();
	}
        
}
