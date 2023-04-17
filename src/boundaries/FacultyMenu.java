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
        
        do {
        	boolean alert = false;
            for (Request r: user.getInbox()) {
            	if (r.getStatus().equals(RequestStatus.Pending)) {
            		alert = true;
            	}
            }
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
            System.out.println("\u2551 4. Create new project                  \u2551");
            if (alert) {
                System.out.println("\u2551 5. View pending requests \u001b[33mNEW\u001b[0m           \u2551");
            } else {
            	System.out.println("\u2551 5. View pending requests               \u2551");
            }
            System.out.println("\u2551 6. View request inbox                  \u2551");
            System.out.println("\u2551 7. Address requests                    \u2551");
            System.out.println("\u2551 8. Change your password                \u2551");
            System.out.println("\u2551 9. Exit                                \u2551");

            // Create the bottom border of the box
            System.out.print("\u255A"); // bottom-left corner
            for (int i = 0; i < width - 2; i++) {
                System.out.print("\u2550"); // horizontal line
            }
            System.out.println("\u255D"); // bottom-right corner
            System.out.println("Enter your choice:");
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
                    // change the title
                	String title;
                	do {
                		input.nextLine();
                        fc.viewOwnProjects(user);
                        System.out.println("Enter Project ID to change title or enter 0 to return:");
                        try {
                        	id = input.nextInt();
                        } catch (InputMismatchException e) {
                            System.err.println("Enter valid integer.");
                            input.nextLine();
                            continue;
                        }
                        if (id == 0) {
                        	break;
                        }
                        if (user.getProjects().contains(fpm.getProjectByID(id))) {
                        	Project p = fpm.getProjectByID(id);
                        	valid = true;
                        	input.nextLine();
                        	System.out.println("Enter new title:");
                        	title = input.nextLine();
                        	while (title.length() < 5 || title.equals(p.getTitle())) {
                        		if (title.equals(p.getTitle())) {
                            		System.out.println("You cannot rename a project with the same name.");
                            	} else {
                                	System.out.println("New title is too short.");
                            	}
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
                	if (user.getActiveProjects() == 0) {
                		System.err.println("You do not have any projects to transfer");
                		break;
                	}
                	do {
                    	String replacement;
                    	input.nextLine();
                    	fc.viewActiveProjects(user);
                        try {
                            System.out.println("Enter Project ID to transfer student or 0 to return:");
                        	id = input.nextInt();
                        } catch (InputMismatchException e) {
                            System.err.println("Enter valid integer.");
                            input.nextLine();
                            continue;
                        }
                        if (id == 0) {
                        	break;
                        }
                        Project p = fpm.getProjectByID(id);
                        if (p != null) {
                        	if (p.getStatus() != ProjectStatus.Allocated) {
                        		System.err.println("You may only transfer allocated projects.");
                        		continue;
                        	}
                        	valid = true;
                        	boolean matched = false;
                        	input.nextLine();
                        	while (!matched) {
                        		System.out.println("Enter replacement supervisor ID:");
                            	replacement = input.nextLine();
                            	Faculty f = fc.getFacultybyID(replacement);
                            	if (f != null && f.getActiveProjects() < 2 && !f.getUserID().equals(user.getUserID())) {
                            		matched = true;
                                	fc.transferRequest(user, p, replacement);
                                	System.out.println("Transfer Request sent succesfully");
                            	}
                            	else {
                            		if (f == null) {
                            			System.err.println("Supervisor not found.");
                            		} else if (f.getActiveProjects() > 2){
                            			System.err.println("Supervisor has too many active projects.");
                            		} else {
                            			System.err.println("You may not transfer a project to yourself.");
                            		}
                            	}
                        	}
                        }
                        else {
                        	System.err.println("Project not found.");
                        }
                	} while(!valid);
                    break;
                case 4:
                	// create new project
                	String projecttitle;
                	input.nextLine();
                    System.out.println("Enter title of new project or leave empty to return: ");
                    projecttitle = input.nextLine();
                    if (projecttitle.equals("")) {
                    	break;
                    }
                    while (projecttitle.length() < 5) {
                        System.out.println("New title is too short.");
                        System.out.println("Enter Project title:");
                        title = input.nextLine();
                        }
                    fpm.addProject(user, projecttitle);
                    System.out.println("New project " + projecttitle + " created.");
                    break;
                case 5: 
                    // view pending requests
                	RequestPendingMenu rpm = RequestPendingMenu.getInstance();
                	rpm.display(user,frm.getPendingReqs(user));
                    break;
                case 6: 
                	int option = 0;
                	do {
                		System.out.println("1. View request history.");
                        System.out.println("2. View incoming requests.");
                        System.out.println("3. Back");
                        System.out.println("Enter your choice: ");
                    	try {
                        	option = input.nextInt();
                        } catch (InputMismatchException e) {
                            System.err.println("Enter valid integer.");
                            input.nextLine();
                            continue;
                        }
                    	switch (option) {
                    		case 1:	
                                // call view history method
                    			FacultyRequestHistoryMenu rhm = FacultyRequestHistoryMenu.getInstance();
                    			rhm.display(user, user.getHistory());
                    			break;
                    		case 2:
                            	// call view inbox method
                    			RequestInboxMenu rim = RequestInboxMenu.getInstance();
                            	rim.display(user, user.getInbox());
                            	break;
                    		case 3:
                    			break;
                    		default:
                    			System.err.println("Invalid option.");
                    			break;
                    	}
                	} while (option != 3);
                	
                	break;
                case 7:
                	FacultyApprovalMenu fam = FacultyApprovalMenu.getInstance();
                	fam.display(user);
                	break;
                case 8: 
                	//change password method
                	PasswordMenu pwm = PasswordMenu.getInstance();
					pwm.display(user);
                    break;
                case 9:
                	fpm.saveChanges();
                	frm.saveChanges();
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
    

