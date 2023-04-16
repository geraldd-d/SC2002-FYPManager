package boundaries;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import controllers.FacultyRequestService;
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
		FacultyRequestService frsc = FacultyRequestService.getInstance();
        int page = 1;
        int numRequests = pending.size();
        if (numRequests == 0) {
        	System.out.println("You have no pending requests!");
        	return;
        }
    	int numPages = (int) Math.ceil((float)numRequests/(float)5);
        do {
        	if(page <= numPages) {
        		frsc.viewPending(user, page);
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
