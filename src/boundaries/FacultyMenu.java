package boundaries;
import java.util.*;

import controllers.ProjectManager;
import entities.*;


public class FacultyMenu{
	private static FacultyMenu fm = null;
	private FacultyMenu() {};
	public static FacultyMenu getInstance() {
		if (fm == null) {
			fm = new FacultyMenu();
		}
		return fm;
	}
	public void display(Faculty user) {

        Scanner input = new Scanner(System.in);
        int choice;
        do {
            System.out.println("FYP Matters");
            System.out.println("---------------------");
            System.out.println("1. View your projects");
            System.out.println("2. View information regarding created FYP");
            System.out.println("3. Modify the title of FYP");
            System.out.println("4. Request to transfer student to replacement supervisor");
            System.out.println("5. View pending requests");
            System.out.println("6. View request history");
            System.out.println("7. Change your password");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();

            switch (choice) { 
                case 1:
                    // view all the projects 
                    user.getProjects();
                    break;
                case 2:
                    // call info reqgarding the created FYP
                    break;
                case 3:
                    // change the title 
                    break;
                case 4:
                    // request to transfer student
                    break;
                case 5: 
                    // view pending requests 
                    break;
                case 6: 
                    // call view history method 
                    break;
                case 7: 
                    // Change the password 
                    break;
                case 8:
                	ProjectManager pm = ProjectManager.getInstance();
                	pm.saveChanges();
                    System.out.println("Thank you for using FYP Management System.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        } while (choice != 7);
	}

}
    

