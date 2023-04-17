package boundaries;

import java.util.InputMismatchException;
import java.util.Scanner;

import controllers.CoordinatorController;
import controllers.FacultyController;
import controllers.FacultyRequestManager;
import entities.ApprovalType;
import entities.Coordinator;
import entities.Faculty;
import entities.Project;
import entities.Request;
import entities.TitleRequest;

/**
 * This class is the boundary for the FacultyApprovalMenu class.
 */
public class FacultyApprovalMenu {
	private FacultyApprovalMenu() {};
	private static FacultyApprovalMenu fam = null;
	
	/** 
	 * 
	 * 
	 * @return FacultyApprovalMenu
	 */
	public static FacultyApprovalMenu getInstance() {
		if (fam == null) {
			fam = new FacultyApprovalMenu();
		}
		return fam;
	}
	
	/** 
	 * @param faculty
	 */
	public void display(Faculty user){
		Scanner sc = new Scanner(System.in);
		FacultyRequestManager frm = FacultyRequestManager.getInstance();
		int requestID = -1;
        do {
        	if (frm.getPendingReqs(user).size() == 0) {
        		System.out.println("No requests to address.");
        		return;
        	}
            frm.viewPending(user);
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
        	Request request = frm.getPendingRequestbyID(user,requestID);
        	if (request == null) {
                System.out.println("Pending request not found in the database.");
        		continue;
        	} else {
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
        				frm.approveTitleChange(request);
        				System.out.println("Request " + request.getRequestID() + " approved.");
        			} else {
        				frm.rejectRequest((TitleRequest)request);
        				System.out.println("Request " + request.getRequestID() + " rejected.");
        			}
        		} while (!valid);
        	}
          
        } while (requestID != 0);
	}
}
