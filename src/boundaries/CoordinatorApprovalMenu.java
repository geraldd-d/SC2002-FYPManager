package boundaries;

import java.util.InputMismatchException;
import java.util.Scanner;

import controllers.CoordinatorController;
import entities.ApprovalType;
import entities.Coordinator;
import entities.Request;

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
    	int requestID = -1;
        do {
            cc.viewPending(coordinator);
        	System.out.println("Which request would you like to address?");
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
        		do {
        			try {
        				sc.nextLine();
        				System.out.println("Enter APPROVE or REJECT: ");
        				approval = sc.nextLine();
            		    ApprovalType apt = ApprovalType.valueOf(approval);
            		} catch ( IllegalArgumentException e ) {
            		    System.err.println( "Invalid option.");
            		    continue;
            		}
        			valid = true;
        			if (ApprovalType.valueOf(approval).equals(ApprovalType.APPROVE)){
        				cc.approveRequest(coordinator, r);
        			} else {
        				cc.rejectRequest(coordinator, r);
        			}
        		} while (!valid);
        	}
          
        } while (requestID != 0);
	}
}
