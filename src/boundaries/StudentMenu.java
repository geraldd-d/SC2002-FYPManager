package boundaries;

import java.util.*;

//import javax.lang.model.util.ElementScanner14;
import java.util.*;

import entities.*;
import controllers.*;

/**
 * This class is the boundary for the student menu.
 */
public class StudentMenu{
	private static StudentMenu sm = null;
	private StudentMenu() {};
	
	/** 
	 * @return StudentMenu
	 */
	public static StudentMenu getInstance() {
		if (sm == null) {
			sm = new StudentMenu();
		}
		return sm;
	}
	
	/** 
	 * @param user
	 */
	public void display(Student user) {
		{
        	StudentProjectManager spm = StudentProjectManager.getInstance();
	        Scanner input = new Scanner(System.in);
	        StudentController stc = StudentController.getInstance();
			Project p = new Project();
			Project applied = new Project();
	        int choice = 0;
	        do {
				ArrayList <Request> hist = user.getHistory();
				boolean eligible = true;
				for (Request r : hist) {
					if (r.getStatus().equals(RequestStatus.Pending) && (r instanceof AllocRequest)) {
						eligible = false;
						applied = r.getProject();
					}
				}
	        	// Define the dimensions of the box
	            int width = 42;
	            // Create the top border of the box
	            System.out.print("\u2554"); // top-left corner
	            for (int i = 0; i < width - 2; i++) {
	                System.out.print("\u2550"); // horizontal line
	            }
	            System.out.println("\u2557"); // top-right corner

	            // Create the sides of the box
	            System.out.print("\u2551"); // left vertical line
	            System.out.print("   FYP Matters   ");
	            for (int i = 0; i < width - "   FYP Matters   ".length() - 2; i++) {
	                System.out.print(" ");
	            }
	            System.out.println("\u2551"); // right vertical line

	            System.out.print("\u2551"); // left vertical line
	            for (int i = 0; i < width - 2; i++) {
	                System.out.print("\u2500"); // horizontal line
	            }
	            System.out.println("\u2551"); // right vertical line

	            System.out.println("\u2551 1. View available projects             \u2551"); // menu options
	            System.out.println("\u2551 2. Request for allocation of a project \u2551");
	            System.out.println("\u2551 3. View the registered project         \u2551");
	            System.out.println("\u2551 4. Request to change the title of FYP  \u2551");
	            System.out.println("\u2551 5. Deregister from the registered FYP  \u2551");
	            System.out.println("\u2551 6. View request history                \u2551");
	        	System.out.println("\u2551 7. Change your Password                \u2551");
	            System.out.println("\u2551 8. Exit                                \u2551");

	            // Create the bottom border of the box
	            System.out.print("\u255A"); // bottom-left corner
	            for (int i = 0; i < width - 2; i++) {
	                System.out.print("\u2550"); // horizontal line
	            }
	            System.out.println("\u255D"); // bottom-right corner
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
	                	if (!user.getisAllocated() && eligible) {
	                		ProjectMenu pm = ProjectMenu.getInstance();
	                		pm.display();
	                	} else if (user.getisAllocated()){
	                		System.out.println("You are already registered for " + user.getRegisteredProject().getTitle());
	                	} else {
	                		System.out.println("You already have a pending allocation request for " + applied.getTitle());
	                	}
					break; 
	                case 2:
	                	if (user.getisAllocated()) {
	                		System.out.println("You are already registered for " + user.getRegisteredProject().getTitle());
	                		break;
	                	}
	                	if (!eligible) {
	                		System.out.println("You already have a pending allocation request for " + applied.getTitle());
	                		break;
	                	}
	                	boolean validRequest = false;
	                	while (!validRequest) {
	                		spm.viewAvailable();
	                		input.nextLine();
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
	                	System.out.println("Allocation Request Sent.");
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
						p.printProject();
						while(!validRequest){
							String proj;
							input.nextLine();
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
								validRequest = stc.requestNewTitle(user);
							}
						}
						System.out.println("Title Change Request sent.");
	                    break;
	                case 5: 
						if(!user.getisAllocated()){
							System.out.println("You are not registered for any project yet.");
							break;
						}
						else{
							input.nextLine();
							System.out.println("Please confirm your decision (y/n): ");
							String d = "";
							while (!d.equals("y")) {
								d = input.nextLine();
								if (d.equals("n")) {
									break;
								}
							}
							if (d.equals("y")) {
								stc.DeregisterProject(user);
								System.out.println("Deregister Request sent.");
							}
						}
	                    break;
	                case 6: 
	                	StudentRequestHistoryMenu rhm = StudentRequestHistoryMenu.getInstance();
	                	rhm.display(user, user.getHistory());
	                	break;
					case 7:
						PasswordMenu pm = PasswordMenu.getInstance();
						pm.display(user);
						break;
	                case 8:
	                	StudentRequestManager srm = StudentRequestManager.getInstance();
	                	spm.saveChanges();
	                	srm.saveChanges();
	                    System.out.println("Thank you for using FYP Management System.");
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
    

