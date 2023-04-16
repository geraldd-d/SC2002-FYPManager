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

public class CoordinatorMenu {
	private CoordinatorMenu() {
		CoordinatorController cc = CoordinatorController.getInstance();
	};
	private static CoordinatorMenu cm = null;
	
    /** 
     * @return CoordinatorMenu
     */
    public static CoordinatorMenu getInstance() {
		if (cm == null) {
			cm = new CoordinatorMenu();
		}
		return cm;
	}
	
    /** 
     * @param coordinator
     */
    public void display(Coordinator coordinator){
		Scanner sc = new Scanner(System.in);
		FacultyController fc = FacultyController.getInstance();
		CoordProjectManager cprm = CoordProjectManager.getInstance();
		CoordinatorController cc = CoordinatorController.getInstance();
        int choice = 0;
        boolean alert = false;
        for (Request r: coordinator.getInbox()) {
        	if (r.getStatus().equals(RequestStatus.Pending)) {
        		alert = true;
        	}
        }
        do {
        	boolean valid = false;
        	int id = -1;
            System.out.println("FYP Matters");
            System.out.println("---------------------");
            System.out.println("1. View projects");
            System.out.println("2. Create a new project");
            System.out.println("3. Modify the title of FYP");
            System.out.println("4. Transfer student to replacement supervisor");
            if (alert) {
            	System.out.println("5. View your request inbox (NEW)");
            } else {
            	System.out.println("5. View your request inbox");
            }
            System.out.println("6. Address requests");
            System.out.println("7. View all requests");
            System.out.println("8. Change your password");
            System.out.println("9. Exit");
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
                    // view all the projects 
                    CoordinatorProjectsMenu cpm = CoordinatorProjectsMenu.getInstance();
                    cpm.display(coordinator);
                    break;
                case 2:
                	String title;
                	sc.nextLine();
                    System.out.println("Enter new title:");
                    title = sc.nextLine();
                    while (title.length() < 5) {
                    	sc.nextLine();
                        System.out.println("New title is too short.");
                        System.out.println("Enter new title:");
                        title = sc.nextLine();
                        }
                    cprm.addProject(coordinator, title);
                    break;
                     
                case 3:
                    // change the title
                	
                	do {
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
                        	Project p = cprm.getProjectByID(id);
                        	valid = true;
                        	sc.nextLine();
                        	String newtitle = "";
                        	System.out.println("Enter new title:");
                        	while (newtitle.length() < 5) {
                        		sc.nextLine();
                            	System.out.println("New title is too short.");
                        		System.out.println("Enter new title:");
                            	newtitle = sc.nextLine();
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
                        	Project p = cprm.getProjectByID(id);
                        	valid = true;
                        	boolean matched = false;
                        	while (!matched) {
                        		sc.nextLine();
                        		System.out.println("Enter replacement supervisor ID:");
                            	replacement = sc.nextLine();
                            	Faculty f = fc.getFacultybyID(replacement);
                            	if (f != null && f.getActiveProjects() < 2 && !f.getUserID().equals(coordinator.getUserID())) {
                            		matched = true;
                                	cc.requestTransfer(coordinator, p, replacement);
                            	}
                            	else {
                            		if (f == null) {
                            			System.out.println("Supervisor not found.");
                            		} else if (f.getActiveProjects() >= 2) {
                            			System.out.println("Supervisor has too many active projects.");
                            		} else {
                            			System.out.println("You cannot transfer a project to yourself.");
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
                    // view inbox
                	CoordinatorRequestInboxMenu crim = CoordinatorRequestInboxMenu.getInstance();
                    crim.display(coordinator);
                    break;
                case 6: 
                    // address requests
                	CoordinatorApprovalMenu cam = CoordinatorApprovalMenu.getInstance();
                    cam.display(coordinator);
                	break;
                case 7:
                	// call view inbox method
                	CoordinatorRequestMenu crm = CoordinatorRequestMenu.getInstance();
                    crm.display(coordinator);
                	break;
                case 8: 
                	//change password method
                	PasswordMenu pwm = PasswordMenu.getInstance();
					pwm.display(coordinator);
                    break;
                case 9:
                	CoordRequestManager crrm = CoordRequestManager.getInstance();
                	cprm.saveChanges();
                	crrm.saveChanges();
                    System.out.println("Logging out...");
                    LoginMenu lm = LoginMenu.getInstance();
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        } while (choice != 9);
	}
}