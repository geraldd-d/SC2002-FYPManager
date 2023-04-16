package boundaries;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import controllers.ProjectsController;
import controllers.RequestManager;
import controllers.StudentController;
import entities.Request;
import entities.User;

/**
 * This class is the boundary for the RequestHistoryMenu class.
 */
public class RequestHistoryMenu{
	private RequestHistoryMenu() {};
	private static RequestHistoryMenu rhm = null;
	
	/** 
	 * @return RequestHistoryMenu
	 */
	public static RequestHistoryMenu getInstance() {
		if (rhm == null) {
			rhm = new RequestHistoryMenu();
		}
		return rhm;
	}
	
	/** 
	 * @param user
	 * @param reqHist
	 */
	public void display(User user, ArrayList<Request> reqHist){
		Scanner sc = new Scanner(System.in);
		RequestManager rm = RequestManager.getInstance();
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
