package boundaries;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import controllers.FacultyRequestManager;
import entities.Faculty;
import entities.Request;


/**
 * This class is the boundary for the RequestInboxMenu class.
 */
public class RequestInboxMenu{
	private RequestInboxMenu() {};
	private static RequestInboxMenu rim = null;
	
	/** 
	 * This method is used to get the instance of the ReqestInboxMenu class. It is a singleton class.
	 * @return RequestInboxMenu
	 */
	public static RequestInboxMenu getInstance() {
		if (rim == null) {
			rim = new RequestInboxMenu();
		}
		return rim;
	}
	
	/** 
	 * displays request inbox menu for faculty
	 * @param user
	 * @param reqInbox
	 */
	public void display(Faculty user, ArrayList<Request> reqInbox){
		Scanner sc = new Scanner(System.in);
		FacultyRequestManager frm = FacultyRequestManager.getInstance();
        int page = 1;
        int numRequests = reqInbox.size();
        if (numRequests == 0) {
        	System.out.println("You have no incoming requests!");
        	return;
        }
    	int numPages = (int) Math.ceil((float)numRequests/(float)5);
        do {
        	if(page <= numPages) {
        		System.out.println();
        		frm.viewInbox(user, page);
            	System.out.println("\u001b[7mPage " + page + " of " + numPages + "\u001b[0m");
        	}
            try {
            	System.out.println("Enter 0 to return or a valid page number:");
            	page = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice.");
                sc.nextLine();
                continue;
            }
        } while(page != 0);
    }
}

