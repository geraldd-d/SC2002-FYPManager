package boundaries;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import controllers.FacultyRequestManager;
import entities.Faculty;
import entities.Request;
import entities.User;

/**
 * This class is the boundary for the FacultyRequestHistoryMenu class.
 */
public class FacultyRequestHistoryMenu{
	private FacultyRequestHistoryMenu() {};
	private static FacultyRequestHistoryMenu rhm = null;
	
	/** 
	 * @return RequestHistoryMenu
	 */
	public static FacultyRequestHistoryMenu getInstance() {
		if (rhm == null) {
			rhm = new FacultyRequestHistoryMenu();
		}
		return rhm;
	}
	
	/** 
	 * @param user
	 * @param reqHist
	 */
	public void display(Faculty user, ArrayList<Request> reqHist){
		Scanner sc = new Scanner(System.in);
		FacultyRequestManager rm = FacultyRequestManager.getInstance();
	
        int page = 1;
        int numRequests = reqHist.size();
        if (numRequests == 0) {
        	System.out.println("You have no prior requests!");
        	return;
        }
    	int numPages = (int) Math.ceil((float)numRequests/(float)5);
        do {
        	if(page <= numPages) {
        		rm.viewHistory(user, page);
            	System.out.println("\u001b[7mPage " + page + " of " + numPages + "\u001b[0m");
        	}
            try {
            	System.out.println("Enter 0 to return or a valid integer from 1 -" + numPages);
            	page = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice. Enter 0 to return or a valid integer from 1 -" + numPages);
                sc.nextLine();
                continue;
            }
        } while(page != 0);
    }
}
