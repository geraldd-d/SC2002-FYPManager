package boundaries;
import java.util.*;

import controllers.FacultyController;
import controllers.FacultyProjectService;
import controllers.FacultyService;
import controllers.ProjectManager;
import controllers.ProjectService;
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
		FacultyController fc = FacultyController.getInstance();
		ProjectService psc = ProjectService.getInstance();
        Scanner input = new Scanner(System.in);
        int choice = 0;
        do {
        	boolean valid = false;
        	int id = -1;
            System.out.println("FYP Matters");
            System.out.println("---------------------");
            System.out.println("1. View your projects");
            System.out.println("2. Create a new project");
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
                	String title;
                	boolean created = false;
                	do {
                    	input.nextLine();
                        System.out.println("Enter new title:");
                        title = input.nextLine();
                        while (title.length() < 5) {
                            System.out.println("New title is too short.");
                        	System.out.println("Enter new title:");
                            title = input.nextLine();
                        	}
                        fc.createProject(title, user);
                        created = true;
                        } while (!created);
                	break;
                case 3:
                    // change the title
                	String newtitle;
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
                        if (user.getProjects().contains(psc.getProjectbyID(id))) {
                        	Project p = psc.getProjectbyID(id);
                        	valid = true;
                        	input.nextLine();
                        	System.out.println("Enter new title:");
                        	newtitle = input.nextLine();
                        	while (newtitle.length() < 5) {
                            	System.out.println("New title is too short.");
                        		System.out.println("Enter new title:");
                        		newtitle = input.nextLine();
                        	}
                        	fc.changeTitle(p, newtitle);
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
                    	input.nextLine();
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
                        if (user.getProjects().contains(psc.getProjectbyID(id))) {
                        	Project p = psc.getProjectbyID(id);
                        	valid = true;
                        	boolean matched = false;
                        	while (!matched) {
                        		System.out.println("Enter replacement supervisor ID:");
                            	replacement = input.nextLine();
                            	Faculty f = fc.getFacultybyID(replacement);
                            	if (f != null && f.getActiveProjects() < 2) {
                            		matched = true;
                                	fc.requestTransfer(user, p, replacement);
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
                	rpm.display(user,fc.getPendingReqs(user));
                    break;
                case 6: 
                    // call view history method
                	RequestHistoryMenu rhm = RequestHistoryMenu.getInstance();
                	rhm.display(user, fc.getFacultyRequests(user));
                	break;
                case 7:
                	// call view inbox method
                	RequestInboxMenu rim = RequestInboxMenu.getInstance();
                	rim.display(user, fc.getFacultyInbox(user));
                	break;
                case 8: 
                	//change password method
                	PasswordMenu pwm = PasswordMenu.getInstance();
					pwm.display(user);
                    break;
                case 9:
                	fc.saveChanges();
                    System.out.println("Logging out...");
                    LoginMenu lm = LoginMenu.getInstance();
                    lm.display();
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        } while (choice != 9);
	}

}
    

