package boundaries;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import controllers.CoordinatorController;
import entities.Coordinator;
import entities.Request;

public class CoordinatorInboxMenu {
	private CoordinatorInboxMenu() {
		CoordinatorController cc = CoordinatorController.getInstance();

	};
	private static CoordinatorInboxMenu crm = null;
	
	// it is the corrdinator indox where she receives all the requests
	public static CoordinatorInboxMenu getInstance() {
		if (crm == null) {
			crm = new CoordinatorInboxMenu();
		}
		return crm;
	}
	
	/** 
	 * @param coordinator
	 */
	public void display(Coordinator coordinator){
		Scanner sc = new Scanner(System.in);
		CoordinatorController cc = CoordinatorController.getInstance();
		ArrayList<Request> requests = coordinator.getInbox();
        int page = 1;
        int numProjects = requests.size();
    	int numPages = (int) Math.ceil((float)numProjects/(float)5);
        do {
        	if(page <= numPages) {
        		cc.viewInbox(coordinator, page);
        	}
            try {
            	System.out.println("Enter 0 to return or a valid integer from 1 - " + numPages);
            	page = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice.");
                sc.nextLine();
                continue;
            }
        } while(page != 0);
    }
}


