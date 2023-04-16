package boundaries;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import controllers.FacultyRequestService;
import controllers.ProjectDataController;
import controllers.RequestManager;
import controllers.StudentController;
import entities.Faculty;
import entities.Request;
import entities.User;

public class RequestInboxMenu{
	private RequestInboxMenu() {};
	private static RequestInboxMenu rim = null;
	public static RequestInboxMenu getInstance() {
		if (rim == null) {
			rim = new RequestInboxMenu();
		}
		return rim;
	}
	public void display(Faculty user, ArrayList<Request> reqInbox){
		Scanner sc = new Scanner(System.in);
		FacultyRequestService frsc = FacultyRequestService.getInstance();
        int page = 1;
        int numRequests = reqInbox.size();
        if (numRequests == 0) {
        	System.out.println("You have no pending requests!");
        	return;
        }
    	int numPages = (int) Math.ceil((float)numRequests/(float)5);
        do {
        	if(page <= numPages) {
        		frsc.viewInbox(user, page);
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

