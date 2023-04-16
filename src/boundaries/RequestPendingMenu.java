package boundaries;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import controllers.FacultyRequestManager;
import controllers.RequestManager;
import entities.Faculty;
import entities.Request;
import entities.User;

public class RequestPendingMenu{
	private RequestPendingMenu() {};
	private static RequestPendingMenu rpm = null;
	public static RequestPendingMenu getInstance() {
		if (rpm == null) {
			rpm = new RequestPendingMenu();
		}
		return rpm;
	}
	public void display(Faculty user, ArrayList<Request> pending){
		Scanner sc = new Scanner(System.in);
		FacultyRequestManager frm = FacultyRequestManager.getInstance();
        int page = 1;
        int numRequests = pending.size();
        if (numRequests == 0) {
        	System.out.println("You have no pending requests!");
        	return;
        }
		frm.viewPending(user);
    }
}
