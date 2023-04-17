package boundaries;
import java.util.*;

import controllers.FacultyController;
import controllers.FacultyProjectManager;
import controllers.FacultyRequestManager;
import controllers.ProjectManager;
import entities.*;

/**
 * This class is the boundary for the FacultyMenu class.
 */
public class FacultyMenu{
	private static FacultyMenu fm = null;
	private FacultyMenu() {};
	
    /** 
     * @return FacultyMenu
     */
    public static FacultyMenu getInstance() {
		if (fm == null) {
			fm = new FacultyMenu();
		}
		return fm;
	}
	
    /** 
     * @param user
     */
    public void display(Faculty user) {

        Scanner input = new Scanner(System.in);
    	FacultyRequestManager frm = FacultyRequestManager.getInstance();
        FacultyProjectManager fpm = FacultyProjectManager.getInstance();
        FacultyController fc = FacultyController.getInstance();
        int choice = 0;
        boolean alert = false;
        for (Request r: user.getInbox()) {
        	if (r.getStatus().equals(RequestStatus.Pending)) {
        		alert = true;
        	}
        }
        do {
        	boolean valid = false;
        	int id = -1;
        	int width = 42;
        	// Create the top border of the box
            System.out.print("\u2554"); // top-left corner
            for (int i = 0; i < width - 2; i++) {
                System.out.print("\u2550"); // horizontal line
            }
            System.out.println("\u2557"); // top-right corner

            // Create the sides of the box
            System.out.print("\u2551"); // left vertical line
            System.out.print("     FYP Matters     ");
            for (int i = 0; i < width - "     FYP Matters     ".length() - 2; i++) {
                System.out.print(" ");
            }
            System.out.println("\u2551"); // right vertical line

            System.out.print("\u2551"); // left vertical line
            for (int i = 0; i < width - 2; i++) {
                System.out.print("\u2500"); // horizontal line
            }
            System.out.println("\u2551"); // right vertical line

            System.out.println("\u2551 1. View your projects                  \u2551"); // menu options
            System.out.println("\u2551 2. Modify the title of FYP             \u2551");
            System.out.println("\u2551 3. Request to transfer student         \u2551");
            if (alert) {
                System.out.println("\u2551 4. View pending requests \u001b[33mNEW\u001b[0m      \u2551");
            } else {
            	System.out.println("\u2551 4. View pending requests               \u2551");
            }
            System.out.println("\u2551 4. View pending requests               \u2551");
            System.out.println("\u2551 5. View request history                \u2551");
            System.out.println("\u2551 6. View request inbox                  \u2551");
            System.out.println("\u2551 7. Change your password                \u2551");
            System.out.println("\u2551 8. Exit                                \u2551");

            // Create the bottom border of the box
            System.out.print("\u255A"); // bottom-left corner
            for (int i = 0; i < width - 2; i++) {
                System.out.print("\u2550"); // horizontal line
            }
            System.out.println("\u255D"); // bottom-right corner

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
                    fc.viewActiveProjects(user);
                    break;
                case 2:
                    // change the title
                	String title;
                	do {
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
                        if (user.getProjects().contains(fpm.getProjectByID(id))) {
                        	Project p = fpm.getProjectByID(id);
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
                case 3:
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
                        Project p = fpm.getProjectByID(id);
                        if (p != null) {
                        	if (p.getStatus() != ProjectStatus.Allocated) {
                        		System.out.println("You may only transfer allocated projects.");
                        		continue;
                        	}
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
                case 4: 
                    // view pending requests
                	RequestPendingMenu rpm = RequestPendingMenu.getInstance();
                	rpm.display(user,frm.getPendingReqs(user));
                    break;
                case 5: 
                    // call view history method
                	FacultyRequestHistoryMenu rhm = FacultyRequestHistoryMenu.getInstance();
                	rhm.display(user, user.getHistory());
                	break;
                case 6:
                	// call view inbox method
                	RequestInboxMenu rim = RequestInboxMenu.getInstance();
                	rim.display(user, user.getInbox());
                	break;
                case 7: 
                	//change password method
                	PasswordMenu pwm = PasswordMenu.getInstance();
					pwm.display(user);
                    break;
                case 8:
                	fpm.saveChanges();
                	frm.saveChanges();
                	System.out.println("Logging out...");
                    LoginMenu lm = LoginMenu.getInstance();
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        } while (choice != 8);
	}

}
    

