package boundaries;

import java.util.*;

//import javax.lang.model.util.ElementScanner14;
import java.util.*;

import entities.*;
import controllers.*;


public class StudentMenu{
	private static StudentMenu sm = null;
	private StudentMenu() {};
	public static StudentMenu getInstance() {
		if (sm == null) {
			sm = new StudentMenu();
		}
		return sm;
	}
	public void display(Student user) {
		{
	        Scanner input = new Scanner(System.in);
	        StudentController stc = StudentController.getInstance();
			Project p = new Project();
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
	                		pm.display(user);
	                	} else {
	                		System.out.println("You are already registered for " + user.getRegisteredProject().getTitle());
	                	}
					break; 
	                case 2:
	                	if (user.getisAllocated()) {
	                		System.out.println("You are already registered for " + user.getRegisteredProject().getTitle());
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
	                		System.out.println("Sending Allocation Request...");
	                		validRequest = stc.requestAlloc(user, proj);
	                	}
	                    break;
	                case 3:
						if(!user.getisAllocated()){
							System.out.println("You are not registered for any project.");
							break;
						}
						else{
							Project regproj = user.getRegisteredProject();
							if(regproj != null){
								regproj.printProject();
								break;
							}
						}
	                    break;
	                case 4:
						if(!user.getisAllocated()){
							System.out.println("You are not registered for any project yet.");
							break;
						}
						validRequest = false;
						System.out.println("The details of your registered project: ");
						Project regproj = user.getRegisteredProject();
						regproj.printProject();
						input.nextLine();
						while(!validRequest){
							String proj;
							try {
								System.out.println("Enter the new title for your project: ");
								proj = input.nextLine();
							} catch (InputMismatchException e){
								System.out.println("Invalid choice");
								input.nextLine();
								continue;
							}
							if(proj.equals(p.getTitle()) || proj.length() < 5){
								System.out.println("Please enter a different/longer title.");
								continue;
							}
							else{
								System.out.println("Sending Title Change Request...");
								validRequest = stc.requestNewTitle(user, proj);
								if (!validRequest) {
									System.out.println("Invalid Request");
								}
							}
						}
	                    break;
	                case 5: 
						if(!user.getisAllocated()){
							System.out.println("You are not registered for any project yet.");
							break;
						}
						else{
							String d = "";
							input.nextLine();
							while (!d.equals("y") && !d.equals("n")) {
								System.out.println("Please confirm your decision (y/n): ");
								d = input.nextLine();
								if (d.equals("n") || d.equals("y")) {
									break;
								}
							}
							if (d.equals("y")) {
								System.out.println("Sending Deregistration Request...");
								stc.requestDeregister(user);
							}
						}
	                    break;
	                case 6: 
	                	RequestHistoryMenu rhm = RequestHistoryMenu.getInstance();
	                	rhm.display(user, user.getHistory());
	                	break;
					case 7:
						PasswordMenu pm = PasswordMenu.getInstance();
						pm.display(user);
						break;
	                case 8:
	                	//save changes code
	                    System.out.println("Logging out...");
						LoginMenu lm = LoginMenu.getInstance();
						lm.display();
						break;
	                default:
	                    System.out.println("Invalid choice. Please enter a valid option.");
	            }

	        } while (choice != 8);
	    }
		
	}
}
    

