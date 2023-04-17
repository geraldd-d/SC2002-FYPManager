package boundaries;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import controllers.CoordRequestManager;
import controllers.CoordinatorController;
import entities.ApprovalType;
import entities.Coordinator;
import entities.Request;

/**
 * This class is the boundary for the CoordinatorApprovalMenu class.
 */
public class CoordinatorApprovalMenu {
	private CoordinatorApprovalMenu() {};
	private static CoordinatorApprovalMenu cam = null;
	
	/** 
	 * 
	 * 
	 * @return CoordinatorApprovalMenu
	 */
	public static CoordinatorApprovalMenu getInstance() {
		if (cam == null) {
			cam = new CoordinatorApprovalMenu();
		}
		return cam;
	}
	
	/** 
	 * @param coordinator
	 */
	public void display(Coordinator coordinator){
		Scanner sc = new Scanner(System.in);
		CoordinatorController cc = CoordinatorController.getInstance();
		CoordRequestManager crm = CoordRequestManager.getInstance();
    	int requestID = -1;
        do {
        	ArrayList<Request> pendingRequests = crm.getPendingReqs(coordinator);
        	if (pendingRequests.size() == 0) {
        		System.out.println("You have no pending requests.");
        		break;
        	}
            cc.viewPending(coordinator);
            try {
        		System.out.println("Enter request ID or 0 to return: ");
            	requestID = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
                sc.nextLine();
                continue;
            }
        	if (requestID == 0) {
        		break;
        	}
        	if (cc.getPendingRequestbyID(coordinator,requestID) == null) {
                System.out.println("Pending request not found in the database.");
        		continue;
        	} else {
        		Request r = cc.getPendingRequestbyID(coordinator, requestID);
        		String approval;
        		boolean valid = false;
        		sc.nextLine();
        		do {
        			try {
        				System.out.println("Enter APPROVE or REJECT: ");
        				approval = sc.nextLine();
            		    ApprovalType apt = ApprovalType.valueOf(approval);
            		} catch ( IllegalArgumentException e ) {
            		    System.err.println("Invalid option.");
            		    continue;
            		}
        			valid = true;
        			if (ApprovalType.valueOf(approval).equals(ApprovalType.APPROVE)){
        				cc.approveRequest(coordinator, r);
        				System.out.println("Request " + r.getRequestID() + " approved.");
        			} else {
        				cc.rejectRequest(coordinator, r);
        				System.out.println("Request " + r.getRequestID() + " rejected.");
        			}
        		} while (!valid);
        	}
          
        } while (requestID != 0);
	}
}
