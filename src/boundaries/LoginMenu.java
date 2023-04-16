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
      }
	
	public static void main(String[] args) {
		StudentService.getInstance();
		FacultyService.getInstance();
		ProjectDataController.getInstance();
		RequestDataController.getInstance();
		LoginMenu lm = LoginMenu.getInstance();
		lm.display();
	}
        
}
