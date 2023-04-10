package boundaries;

import java.util.*;

import entities.*;
import controllers.*;


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
				System.out.println("7. Change your Password");
	            System.out.println("8. Exit");
	            System.out.print("Enter your choice: ");
	            choice = input.nextInt();

	            switch (choice) {
	                case 1:
					List<Project> availableProjects = StudentController.getInstance().ViewAllAvailableProjects();
					System.out.println("Available projects:");
					int len = availableProjects.size();
					System.out.println("ProjectID. Project Title - Project Supervisor");
					for( int i = 0;i<len;i++){
							Project project = availableProjects.get(i);
							System.out.println(project.getID() + "." +project.getTitle()+ " -  Dr. " + project.getSupervisorName());
					}
					break; 
	                case 2:
						StudentController.getInstance().requestProject(user.getUserID(), user.getHistory());
	                    break;
	                case 3:
						Project registeredProject = StudentController.getInstance().viewRegisteredProject(user);
						if (registeredProject != null) {
							System.out.println("Registered project: " + registeredProject.getTitle() + " - Dr. " + registeredProject.getSupervisorName());
						}
	                    break;
	                case 4:
						StudentController.getInstance().requestNewTitle(user, user.getHistory());
	                    break;
	                case 5: 
						StudentController.getInstance().deregisterProject(user, user.getHistory());
	                    break;
	                case 6: 
	                    StudentController.getInstance().viewRequestHistory(user);
	                    break;
					case 7:
						// change the password 
						break;
	                case 8:
	                    System.out.println("Thank you for using FYP Management System.");
						System.exit(0);
	                    break;
	                default:
	                    System.out.println("Invalid choice. Please enter a valid option.");
	            }

	        } while (choice != 7);
	    }
		
	}
}
    

