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
	 * @return RequestInboxMenu
	 */
	public static RequestInboxMenu getInstance() {
		if (rim == null) {
			rim = new RequestInboxMenu();
		}
		return rim;
	}
	
	/** 
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
        		frm.viewInbox(user, page);
        	}
            try {
            	System.out.println("Enter 0 to return or a valid integer from 1 -" + numPages);
            	page = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice. Enter 0 to return or a valid integer from 1 -" + numPages);
                sc.nextLine();
                continue;
            }
            System.out.println(page);
        } while(page != 0);
    }
}

