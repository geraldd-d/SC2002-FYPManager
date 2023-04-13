package boundaries;

import java.util.*;

import entities.*;
import controllers.*;


public class StudentMenu{
	public void display(Student user) {
		{
	        Scanner input = new Scanner(System.in);
	        StudentController stc = StudentController.getInstance();
	        int choice = 0;
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
	            try {
	            	choice = input.nextInt();
	            } catch (InputMismatchException e) {
	                System.out.println("Invalid choice. Please enter an integer from 1 - 8.");
	                input.nextLine();
	                continue;
	            }
	            switch (choice) {
	                case 1:
	                	if (!user.getisAllocated()) {
	                		ProjectMenu pm = ProjectMenu.getInstance();
	                		pm.display();
	                	} else {
	                		System.out.println("You are already registered for" + user.getRegisteredProject().getTitle());
	                	}
					break; 
	                case 2:
	                	if (user.getisAllocated()) {
	                		System.out.println("You are already registered for" + user.getRegisteredProject().getTitle());
	                		break;
	                	}
	                	boolean validRequest = false;
	                	while (!validRequest) {
	                		int proj = 0;
	                		try {
	    	                	System.out.println("Enter project ID that you wish to be allocated for: ");
	                            proj = input.nextInt();
	                        } catch (InputMismatchException e) {
	                            System.out.println("Invalid choice. Enter a valid project ID");
	                            input.nextLine();
	                            continue;
	                        }
	                		validRequest = stc.requestAlloc(user, proj);
	                	}
	                    break;
	                case 3:
	                    break;
	                case 4:
	                    break;
	                case 5: 
	                    break;
	                case 6: 
	                	RequestHistoryMenu rhm = RequestHistoryMenu.getInstance();
	                	rhm.display(user, user.getHistory());
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

	        } while (choice != 8);
	    }
		
	}
}
    

