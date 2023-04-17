package boundaries;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import controllers.CoordProjectManager;
import controllers.CoordRequestManager;
import controllers.CoordinatorController;
import controllers.FacultyController;
import entities.Coordinator;
import entities.Faculty;
import entities.Project;
import entities.Request;
import entities.RequestStatus;

/**
 * This class is the boundary for the CoordinatorMenu class.
 */
public class CoordinatorMenu {
	private CoordinatorMenu() {
	};
	private static CoordinatorMenu cm = null;
	
    /** This method is used to get the instance of the CoordinatorMenu class. It is a singleton class.
     * @return CoordinatorMenu
     */
    public static CoordinatorMenu getInstance() {
		if (cm == null) {
			cm = new CoordinatorMenu();
		}
		return cm;
	}
	
    /** 
     * displays coordinator menu
     * @param coordinator
     */
    public void display(Coordinator coordinator){
		Scanner sc = new Scanner(System.in);
		FacultyController fc = FacultyController.getInstance();
		CoordProjectManager cprm = CoordProjectManager.getInstance();
		CoordinatorController cc = CoordinatorController.getInstance();
        int choice = 0;
        do {
        	boolean alert = false;
            for (Request request: coordinator.getInbox()) {
            	if (request.getStatus().equals(RequestStatus.Pending)) {
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
        	System.out.print("   Coordinator Menu");
        	for (int i = 0; i < width - "Coordinator Menu".length() - 5; i++) {
        	    System.out.print(" ");
        	}
        	System.out.println("\u2551");

        	System.out.print("\u2551"); 
        	for (int i = 0; i < width - 2; i++) {
        	    System.out.print("\u2500"); 
        	}
        	System.out.println("\u2551");

        	System.out.println("\u2551 1. Create / Update / View Projects     \u2551"); // menu options
        	System.out.println("\u2551 2. Transfer student to replacement     \u2551");
        	System.out.println("\u2551    supervisor                          \u2551");
        	if (alert) {
        	    System.out.println("\u2551 3. View your request inbox \u001b[33mNEW\u001b[0m         \u2551");
        	} else {
        	    System.out.println("\u2551 3. View your request inbox             \u2551");
        	}
        	System.out.println("\u2551 4. Address requests                    \u2551");
        	System.out.println("\u2551 5. View all requests                   \u2551");
        	System.out.println("\u2551 6. Change your password                \u2551");
        	System.out.println("\u2551 7. Exit                                \u2551");

        
        	System.out.print("\u255A");
        	for (int i = 0; i < width - 2; i++) {
        	    System.out.print("\u2550");
        	}
        	System.out.println("\u255D");
            System.out.print("Enter your choice: ");
            try {
            	choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice. Please enter an integer from 1-8.");
                sc.nextLine();
                continue;
            }
            ArrayList<Project> projects = coordinator.getProjects();
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
                		System.out.println("\u2551 2. View projects           \u2551");
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
                                cprm.addProject(coordinator, projectTitle);
                                System.out.println("New project " + projectTitle + " created.");
                                break;
                			case 2:
                				// view projects by filter
                				CoordinatorProjectsMenu cpm = CoordinatorProjectsMenu.getInstance();
                				cpm.display(coordinator);
                				break;
                			case 3:
                				// modify title
                				String title;
                            	do {
                            		sc.nextLine();
                                    cc.viewOwnProjects(coordinator);
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
                                    if (coordinator.getProjects().contains(cprm.getProjectByID(id))) {
                                    	Project p = cprm.getProjectByID(id);
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
                	do {
                    	String replacement;
                    	sc.nextLine();
                        System.out.println("Enter Project ID to change title or enter 0 to return:");
                        try {
                        	id = sc.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Enter valid integer.");
                            sc.nextLine();
                            continue;
                        }
                        if (id == 0) {
                        	break;
                        }
                        if (projects.contains(cprm.getProjectByID(id))) {
                        	Project project = cprm.getProjectByID(id);
                        	valid = true;
                        	boolean matched = false;
                        	while (!matched) {
                        		sc.nextLine();
                        		System.out.println("Enter replacement supervisor ID:");
                            	replacement = sc.nextLine();
                            	Faculty f = fc.getFacultybyID(replacement);
                            	if (f != null && f.getActiveProjects() < 2 && !f.getUserID().equals(coordinator.getUserID())) {
                            		matched = true;
                                	cc.requestTransfer(coordinator, project, replacement);
                            	}
                            	else {
                            		if (f == null) {
                            			System.out.println("Supervisor not found.");
                            		} else if (f.getActiveProjects() >= 2) {
                            			System.out.println("Supervisor has too many active projects.");
                            		} else {
                            			System.out.println("You may not transfer a project to yourself.");
                            		}
                            	}
                        	}
                        }
                        else {
                        	System.out.println("Project not found.");
                        }
                	} while(!valid);
                    break;
                case 3: 
                    // view inbox
                	CoordinatorRequestInboxMenu crim = CoordinatorRequestInboxMenu.getInstance();
                    crim.display(coordinator);
                    break;
                case 4: 
                    // address requests
                	CoordinatorApprovalMenu cam = CoordinatorApprovalMenu.getInstance();
                    cam.display(coordinator);
                	break;
                case 5:
                	// call view inbox method
                	CoordinatorRequestMenu crm = CoordinatorRequestMenu.getInstance();
                    crm.display(coordinator);
                	break;
                case 6: 
                	//change password method
                	PasswordMenu pwm = PasswordMenu.getInstance();
					pwm.display(coordinator);
                    break;
                case 7:
                	CoordRequestManager crrm = CoordRequestManager.getInstance();
                	cprm.saveChanges();
                	crrm.saveChanges();
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