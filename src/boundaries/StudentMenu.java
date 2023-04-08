package boundaries;

import java.util.*;

import entities.*;
import controllers.*;


public class StudentMenu{
	public void display(Student user) {
		{
	        Scanner input = new Scanner(System.in);
			//ViewAvailableProject availableprojects = new ViewAvailableProject(ProjectsController.getInstance().getAllAvailableProjects());
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
				System.out.println("7. Change your Password");
	            System.out.println("8. Exit");
	            System.out.print("Enter your choice: ");
	            choice = input.nextInt();

	            switch (choice) {
	                case 1:
						// View all the avaiable project ;
	                    break;
	                case 2:
	                    // requestFYP
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
						// change the password 
						break;
	                case 8:
	                    System.out.println("Thank you for using FYP Management System.");
	                    break;
	                default:
	                    System.out.println("Invalid choice. Please enter a valid option.");
	            }

	        } while (choice != 7);
	    }
		
	}
}
    

