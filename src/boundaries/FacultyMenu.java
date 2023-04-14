package boundaries;
import java.util.*;

import controllers.FacultyController;
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
        ProjectManager pm = ProjectManager.getInstance();
        FacultyController fc = FacultyController.getInstance();
        int choice = 0;
        do {
        	boolean valid = false;
        	int id = -1;
            System.out.println("FYP Matters");
            System.out.println("---------------------");
            System.out.println("1. View your projects");
            System.out.println("2. View information regarding created FYP");
            System.out.println("3. Modify the title of FYP");
            System.out.println("4. Request to transfer student to replacement supervisor");
            System.out.println("5. View pending requests");
            System.out.println("6. View request history");
            System.out.println("7. View request inbox");
            System.out.println("8. Change your password");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            try {
            	choice = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice. Please enter an integer from 1-8.");
                input.nextLine();
                continue;
            }

            switch (choice) { 
                case 1:
                    // view all the projects 
                    fc.viewOwnProjects(user);
                    break;
                case 2:
                    // call info regarding the created FYP WHAT IS INFO HERE?
                	fc.viewOwnProjects(user);
                    break;
                case 3:
                    // change the title
                	String title;
                	do {
                        System.out.println("Enter Project ID to change title or enter 0 to return:");
                        try {
                        	id = input.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Enter valid integer.");
                            input.nextLine();
                            continue;
                        }
                        if (id == 0) {
                        	break;
                        }
                        if (user.getProjects().contains(pm.getProjectByID(id))) {
                        	Project p = pm.getProjectByID(id);
                        	valid = true;
                        	System.out.println("Enter new title:");
                        	title = input.nextLine();
                        	while (title.length() < 5) {
                            	System.out.println("New title is too short.");
                        		System.out.println("Enter new title:");
                            	title = input.nextLine();
                        	}
                        	fc.changeTitle(p, title);
                        }
                        else {
                        	System.out.println("Project not found.");
                        }
                	} while(!valid);
                    
                    break;
                case 4:
                    // request to transfer student
                	do {
                    	String replacement;
                        System.out.println("Enter Project ID to change title or enter 0 to return:");
                        try {
                        	id = input.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Enter valid integer.");
                            input.nextLine();
                            continue;
                        }
                        if (id == 0) {
                        	break;
                        }
                        if (user.getProjects().contains(pm.getProjectByID(id))) {
                        	Project p = pm.getProjectByID(id);
                        	valid = true;
                        	boolean matched = false;
                        	while (!matched) {
                        		System.out.println("Enter replacement supervisor ID:");
                            	replacement = input.nextLine();
                            	Faculty f = fc.getFacultybyID(replacement);
                            	if (f != null && f.getActiveProjects() < 2) {
                            		matched = true;
                                	fc.transferRequest(user, p, replacement);
                            	}
                            	else {
                            		if (f == null) {
                            			System.out.println("Supervisor not found.");
                            		} else {
                            			System.out.println("Supervisor has too many active projects.");
                            		}
                            	}
                        	}
                        }
                        else {
                        	System.out.println("Project not found.");
                        }
                	} while(!valid);
                    break;
                case 5: 
                    // view pending requests
                	RequestPendingMenu rpm = RequestPendingMenu.getInstance();
                	rpm.display(user,fc.getPendingRequests(user));
                    break;
                case 6: 
                    // call view history method
                	RequestHistoryMenu rhm = RequestHistoryMenu.getInstance();
                	rhm.display(user, user.getHistory());
                	break;
                case 7:
                	// call view inbox method
                	RequestInboxMenu rim = RequestInboxMenu.getInstance();
                	rim.display(user, user.getInbox());
                	break;
                case 8: 
                	//change password method
                	PasswordMenu pwm = PasswordMenu.getInstance();
					pwm.display(user);
                    break;
                case 9:
                	pm.saveChanges();
                    System.out.println("Thank you for using FYP Management System.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        } while (choice != 9);
	}

}
    

