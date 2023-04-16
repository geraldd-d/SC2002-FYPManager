package boundaries;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import controllers.CoordController;
import controllers.FacultyController;
import controllers.FacultyService;
import controllers.ProjectService;
import entities.Coordinator;
import entities.Faculty;
import entities.Project;

public class CoordinatorMenu {
	private CoordinatorMenu() {};
	private static CoordinatorMenu cm = null;
	public static CoordinatorMenu getInstance() {
		if (cm == null) {
			cm = new CoordinatorMenu();
		}
		return cm;
	}
	public void display(Coordinator coordinator){
		Scanner sc = new Scanner(System.in);
		ProjectService psc = ProjectService.getInstance();
		CoordController cc = CoordController.getInstance();
		FacultyService fs = FacultyService.getInstance();
		FacultyController fc = FacultyController.getInstance();
        int choice = 0;
        do {
        	boolean valid = false;
        	int id = -1;
            System.out.println("FYP Matters");
            System.out.println("---------------------");
            System.out.println("1. View projects");
            System.out.println("2. Create a new project");
            System.out.println("3. Modify the title of FYP");
            System.out.println("4. Transfer student to replacement supervisor");
            System.out.println("5. View your request inbox");
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
                	boolean created = false;
                	do {
                        System.out.println("Enter new title:");
                        title = sc.nextLine();
                        while (title.length() < 5) {
                            System.out.println("New title is too short.");
                        	System.out.println("Enter new title:");
                            title = sc.nextLine();
                        	}
                        fc.createProject(title, coordinator);
                        created = true;
                        } while (!created);
                case 3:
                    // change the title
                	String newtitle;
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
                        if (projects.contains(psc.getProjectbyID(id))) {
                        	Project p = psc.getProjectbyID(id);
                        	valid = true;
                        	System.out.println("Enter new title:");
                        	title = sc.nextLine();
                        	while (title.length() < 5) {
                            	System.out.println("New title is too short.");
                        		System.out.println("Enter new title:");
                            	title = sc.nextLine();
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
                        	id = sc.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Enter valid integer.");
                            sc.nextLine();
                            continue;
                        }
                        if (id == 0) {
                        	break;
                        }
                        if (projects.contains(psc.getProjectbyID(id))) {
                        	Project p = psc.getProjectbyID(id);
                        	valid = true;
                        	boolean matched = false;
                        	while (!matched) {
                        		System.out.println("Enter replacement supervisor ID:");
                            	replacement = sc.nextLine();
                            	Faculty f = fs.getFacultybyID(replacement);
                            	if (f != null && f.getActiveProjects() < 2) {
                            		matched = true;
                                	cc.requestTransfer(coordinator, p, replacement);
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
                	fc.saveChanges();
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