package boundaries;
import java.util.*;

import entities.Faculty;
import entities.User;

public class FacultyMenu{
	public FacultyMenu() {};
	public void display(Faculty user) {
        Scanner input = new Scanner(System.in);
        int choice = 0;
        do {
            System.out.println("FYP Matters");
            System.out.println("---------------------");
            System.out.println("1. View your projects");
            System.out.println("2. View information regarding created FYP");
            System.out.println("3. Modify the title of FYP");
            System.out.println("4. Request to transfer stduent to replacement supervisor");
            System.out.println("5. View pending requests");
            System.out.println("6. View request history");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();

            switch (choice) { 
                case 1:
                    user.getProjects();
                    break;
                case 2:
                    // call allocation of fyp method 
                    break;
                case 3:
                    // call view registered project method
                    break;
                case 4:
                    // call change the title method 
                    break;
                case 5: 
                    // call deregister title 
                    break;
                case 6: 
                    // call view history method 
                    break;
                case 7:
                    System.out.println("Thank you for using FYP Management System.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        } while (choice != 7);
	}

}
    

