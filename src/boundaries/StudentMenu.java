package boundaries;
import java.util.*;

import entities.Student;
import entities.User;

public class StudentMenu{
	public void display(Student user) {
		{
	        Scanner input = new Scanner(System.in);
	        int choice;
	        do {
	            System.out.println("FYP Matters");
	            System.out.println("---------------------");
	            System.out.println("1. View available projects");
	            System.out.println("2. Request for allocation of a project");
	            System.out.println("3. View the registered project");
	            System.out.println("4. Request to change the title of FYP");
	            System.out.println("5. Deregister from the registered FYP");
	            System.out.println("6. View request history");
	            System.out.println("7. Exit");
	            System.out.print("Enter your choice: ");
	            choice = input.nextInt();

	            switch (choice) {
	                case 1:
	                    // call view available projects method 
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
	            }

	        } while (choice != 7);
	    }
		
	}
}
    

