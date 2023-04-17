package boundaries;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import controllers.FacultyRequestManager;
import entities.Faculty;
import entities.Request;
import entities.User;

/**
 * This class is the boundary for the RequestPendingMenu class.
 */
public class RequestPendingMenu{
	private RequestPendingMenu() {};
	private static RequestPendingMenu rpm = null;
	
	/** 
	 * @return RequestPendingMenu
	 */
	public static RequestPendingMenu getInstance() {
		if (rpm == null) {
			rpm = new RequestPendingMenu();
		}
		return rpm;
	}
	
	/** 
	 * @param user
	 * @param pending
	 */
	public void display(Faculty user, ArrayList<Request> pending){
		Scanner sc = new Scanner(System.in);
		FacultyRequestManager frm = FacultyRequestManager.getInstance();
        int numRequests = pending.size();
        if (numRequests == 0) {
        	System.out.println("You have no pending requests!");
        	return;
        } else {
        	frm.viewPending(user);
        }
    }
}
