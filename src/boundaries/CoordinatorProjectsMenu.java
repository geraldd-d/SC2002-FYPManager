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

public class CoordinatorProjectsMenu {
	private CoordinatorProjectsMenu() {};
	private static CoordinatorProjectsMenu cpm = null;
	public static CoordinatorProjectsMenu getInstance() {
		if (cpm == null) {
			cpm = new CoordinatorProjectsMenu();
		}
		return cpm;
	}
	public void display(Coordinator coordinator){
		Scanner sc = new Scanner(System.in);
		CoordController cc = CoordController.getInstance();
        int choice = 0;
        do {
        	boolean valid = false;
        	int id = -1;
            System.out.println("View Projects:");
            System.out.println("---------------------");
            System.out.println("1. Available");
            System.out.println("2. Unavailable");
            System.out.println("3. Reserved");
            System.out.println("4. Allocated");
            System.out.println("5. My Projects");
            System.out.println("6. Back");
            System.out.print("Enter your choice: ");
            try {
            	choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice. Please enter an integer from 1-6.");
                sc.nextLine();
                continue;
            }
            switch (choice) { 
            	case 1:
            		cc.viewAllAvailableProjects(coordinator);
            		break;
            	case 2:
            		cc.viewAllUnavailableProjects(coordinator);
            		break;
            	case 3:
            		cc.viewAllReservedProjects(coordinator);
            		break;
            	case 4:
            		cc.viewAllUnavailableProjects(coordinator);
            		break;
            	case 5:
            		cc.viewOwnProjects(coordinator);
            		break;
                case 6:
                	return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        } while (choice != 5);
	}
}