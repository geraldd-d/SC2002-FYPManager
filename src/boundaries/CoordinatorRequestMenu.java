package boundaries;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import controllers.CoordRequestManager;
import controllers.CoordinatorController;
import controllers.StudentController;
import entities.Coordinator;
import entities.Project;
import entities.Request;
import entities.Student;

/**
 * This class is the boundary for the CoordinatorRequestMenu class.
 */
public class CoordinatorRequestMenu {
	private CoordinatorRequestMenu() {
		CoordinatorController cc = CoordinatorController.getInstance();
	};
	private static CoordinatorRequestMenu crm = null;
	
	/** 
	 * @return CoordinatorRequestMenu
	 */
	public static CoordinatorRequestMenu getInstance() {
		if (crm == null) {
			crm = new CoordinatorRequestMenu();
		}
		return crm;
	}
	
	/** 
	 * @param coordinator
	 */
	public void display(Coordinator coordinator){
		Scanner sc = new Scanner(System.in);
		CoordinatorController cc = CoordinatorController.getInstance();
		CoordRequestManager crm = CoordRequestManager.getInstance();
		ArrayList<Request> requests = crm.getRequests();
        int page = 1;
        int numRequests = requests.size();
    	int numPages = (int) Math.ceil((float)numRequests/(float)5);
        do {
        	if(page <= numPages) {
        		cc.viewAllRequests(coordinator, page);
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

