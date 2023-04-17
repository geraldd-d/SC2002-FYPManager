package boundaries;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import controllers.FacultyRequestManager;
import entities.Faculty;
import entities.Request;


/**
 * This class is the boundary for the FacultyRequestHistoryMenu class.
 */
public class FacultyRequestHistoryMenu{
	private FacultyRequestHistoryMenu() {};
	private static FacultyRequestHistoryMenu rhm = null;
	
	/** 
	 * This method is used to get the instance of the FacultyRequestHistoryMenu class. It is a singleton class.
	 * @return FacultyRequestHistoryMenu
	 */
	public static FacultyRequestHistoryMenu getInstance() {
		if (rhm == null) {
			rhm = new FacultyRequestHistoryMenu();
		}
		return rhm;
	}
	
	/** 
	 * displays faculty request history menu
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
            	System.out.println("Enter 0 to return or a valid page number");
            	page = sc.nextInt();
            } catch (InputMismatchException e) {
                System.err.println("Invalid choice.");
                sc.nextLine();
                continue;
            }
        } while(page != 0);
    }
}
