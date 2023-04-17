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
     * This method is used to get the instance of the FacultyMenu class. It is a singleton class.
     * @return FacultyMenu
     */
    public static FacultyMenu getInstance() {
		if (fm == null) {
			fm = new FacultyMenu();
		}
		return fm;
	}
	
    /** 
     * displays the faculty menu
     * @param user
     */
    public void display(Faculty user) {

        Scanner sc = new Scanner(System.in);
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
            System.out.print("\u2554"); 
            for (int i = 0; i < width - 2; i++) {
                System.out.print("\u2550");
            }
            System.out.println("\u2557"); 

        
            System.out.print("\u2551"); 
            System.out.print("     FYP Matters     ");
            for (int i = 0; i < width - "     FYP Matters     ".length() - 2; i++) {
                System.out.print(" ");
            }
            System.out.println("\u2551"); 

            System.out.print("\u2551");
            for (int i = 0; i < width - 2; i++) {
                System.out.print("\u2500");
            }
            System.out.println("\u2551"); 

            System.out.println("\u2551 1. Create / Update / View Projects     \u2551"); // menu options
            System.out.println("\u2551 2. Request to transfer student         \u2551");
            if (alert) {
                System.out.println("\u2551 3. View pending requests \u001b[33mNEW\u001b[0m           \u2551");
            } else {
            	System.out.println("\u2551 3. View pending requests               \u2551");
            }
            System.out.println("\u2551 4. View request inbox                  \u2551");
            System.out.println("\u2551 5. Address requests                    \u2551");
            System.out.println("\u2551 6. Change your password                \u2551");
            System.out.println("\u2551 7. Exit                                \u2551");

            
            System.out.print("\u255A"); 
            for (int i = 0; i < width - 2; i++) {
                System.out.print("\u2550"); 
            }
            System.out.println("\u255D");
            System.out.println("Enter your choice:");
            try {
            	choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice. Please enter an integer from 1-8.");
                sc.nextLine();
                continue;
            }

            switch (choice) { 
                case 1:
                	sc.nextLine();
                	int input = 0;
                	do {
                		int breadth = 30;

                		// Create the top border of the box
                		System.out.print("\u2554"); // top-left corner
                		for (int i = 0; i < breadth - 2; i++) {
                		    System.out.print("\u2550"); // horizontal line
                		}
                		System.out.println("\u2557"); // top-right corner

                		// Create the sides of the box
                		System.out.print("\u2551"); // left vertical line
                		System.out.print("  Project Management Menu");
                		for (int i = 0; i < breadth - "Project Management Menu".length() - 4; i++) {
                		    System.out.print(" ");
                		}
                		System.out.println("\u2551"); // right vertical line

                		System.out.print("\u2551"); // left vertical line
                		for (int i = 0; i < breadth - 2; i++) {
                		    System.out.print("\u2500"); // horizontal line
                		}
                		System.out.println("\u2551"); // right vertical line

                		System.out.println("\u2551 1. Create a project        \u2551"); // menu options
                		System.out.println("\u2551 2. View your own projects  \u2551");
                		System.out.println("\u2551 3. Modify project title    \u2551");
                		System.out.println("\u2551 4. Back                    \u2551");

                		// Create the bottom border of the box
                		System.out.print("\u255A"); // bottom-left corner
                		for (int i = 0; i < breadth - 2; i++) {
                		    System.out.print("\u2550"); // horizontal line
                		}
                		System.out.println("\u255D"); // bottom-right corner
                		System.out.println("Enter your choice: ");
                		try {
                        	input = sc.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid choice. Please enter an integer from 1-8.");
                            sc.nextLine();
                            continue;
                        }
                		switch(input) {
                			case 1: 
                				// create new project
                            	String projectTitle;
                            	sc.nextLine();
                                System.out.println("Enter title of new project or leave empty to return: ");
                                projectTitle = sc.nextLine();
                                if (projectTitle.equals("")) {
                                	break;
                                }
                                while (projectTitle.length() < 8) {
                                    System.out.println("New title is too short.");
                                    System.out.println("Enter Project title:");
                                    projectTitle = sc.nextLine();
                                    }
                                fpm.addProject(user, projectTitle);
                                System.out.println("New project " + projectTitle + " created.");
                                break;
                			case 2:
                				// view own projects
                				fc.viewOwnProjects(user);
                				break;
                			case 3:
                				// modify title
                				String title;
                            	do {
                            		sc.nextLine();
                                    fc.viewOwnProjects(user);
                                    System.out.println("Enter Project ID to change title or enter 0 to return:");
                                    try {
                                    	id = sc.nextInt();
                                    } catch (InputMismatchException e) {
                                        System.err.println("Enter valid integer.");
                                        sc.nextLine();
                                        continue;
                                    }
                                    if (id == 0) {
                                    	break;
                                    }
                                    if (user.getProjects().contains(fpm.getProjectByID(id))) {
                                    	Project p = fpm.getProjectByID(id);
                                    	valid = true;
                                    	sc.nextLine();
                                    	System.out.println("Enter new title:");
                                    	title = sc.nextLine();
                                    	while (title.length() < 5 || title.equals(p.getTitle())) {
                                    		if (title.equals(p.getTitle())) {
                                        		System.out.println("You cannot rename a project with the same name.");
                                        	} else {
                                            	System.out.println("New title is too short.");
                                        	}
                                    		System.out.println("Enter new title:");
                                        	title = sc.nextLine();
                                    	}
                                    	fc.changeTitle(p, title);
                                    	System.out.println("Title changed Successfully");
                                    }
                                    else {
                                    	System.out.println("Project not found.");
                                    }
                            	} while(!valid);
                            	break;
                			case 4:
                				break;
                            default:
                            	System.err.println("Enter a valid choice from 1-4.");
                            	break;
                			}	
                		} while (input != 4);
                	break;
                case 2:
                    // request to transfer student
                	if (user.getActiveProjects() == 0) {
                		System.err.println("You do not have any projects to transfer");
                		break;
                	}
                	do {
                    	String replacement;
                    	sc.nextLine();
                    	fc.viewActiveProjects(user);
                        try {
                            System.out.println("Enter Project ID to transfer student or 0 to return:");
                        	id = sc.nextInt();
                        } catch (InputMismatchException e) {
                            System.err.println("Enter valid integer.");
                            sc.nextLine();
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
                        	sc.nextLine();
                        	while (!matched) {
                        		System.out.println("Enter replacement supervisor ID:");
                            	replacement = sc.nextLine();
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
                case 3: 
                    // view pending requests
                	RequestPendingMenu rpm = RequestPendingMenu.getInstance();
                	rpm.display(user,frm.getPendingReqs(user));
                    break;
                case 4: 
                	int option = 0;
                	do {
                		System.out.println("1. View request history.");
                        System.out.println("2. View incoming requests.");
                        System.out.println("3. Back");
                        System.out.println("Enter your choice: ");
                    	try {
                        	option = sc.nextInt();
                        } catch (InputMismatchException e) {
                            System.err.println("Enter valid integer.");
                            sc.nextLine();
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
                case 5:
                	// address requests
                	FacultyApprovalMenu fam = FacultyApprovalMenu.getInstance();
                	fam.display(user);
                	break;
                case 6: 
                	//change password method
                	PasswordMenu pwm = PasswordMenu.getInstance();
					pwm.display(user);
                    break;
                case 7:
                	// logout and save changes
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
        } while (choice != 7);
	}

}
    

