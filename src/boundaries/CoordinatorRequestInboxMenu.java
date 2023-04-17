package boundaries;

import java.util.InputMismatchException;
import java.util.Scanner;

import controllers.CoordinatorController;
import entities.Coordinator;

/**
 * This class is the boundary for the CoordinatorRequestInboxMenu class.
 */
public class CoordinatorRequestInboxMenu {
	private CoordinatorRequestInboxMenu() {
		CoordinatorController cc = CoordinatorController.getInstance();
	};
	private static CoordinatorRequestInboxMenu crim = null;
	
	/** 
	 * @return CoordinatorRequestInboxMenu
	 */
	public static CoordinatorRequestInboxMenu getInstance() {
		if (crim == null) {
			crim = new CoordinatorRequestInboxMenu();
		}
		return crim;
	}
	
	/** 
	 * @param coordinator
	 */
	public void display(Coordinator coordinator){
		Scanner sc = new Scanner(System.in);
		CoordinatorController cc = CoordinatorController.getInstance();
        int choice = 0;
        do {
            System.out.println("View Request Inbox:");
            System.out.println("---------------------");
            System.out.println("1. All Incoming Requests");
            System.out.println("2. Pending Requests");
            System.out.println("3. Outgoing Requests");
            System.out.println("4. Back");
            System.out.print("Enter your choice: ");
            try {
            	choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice. Please enter an integer from 1-4.");
                sc.nextLine();
                continue;
            }
            switch (choice) { 
            	case 1:
            		CoordinatorInboxMenu cim = CoordinatorInboxMenu.getInstance();
            		cim.display(coordinator);
            		break;
            	case 2:
            		cc.viewPending(coordinator);
            		break;
            	case 3:
            		cc.viewHist(coordinator);
            		break;
                case 4:
                	return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        } while (choice != 4);
	}
}
