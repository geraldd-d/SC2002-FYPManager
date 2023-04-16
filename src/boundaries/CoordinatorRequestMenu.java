package boundaries;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import controllers.CoordController;
import controllers.ProjectService;
import controllers.StudentController;
import entities.Coordinator;
import entities.Project;
import entities.Request;
import entities.Student;

public class CoordinatorRequestMenu {
	private CoordinatorRequestMenu() {};
	private static CoordinatorRequestMenu crm = null;
	public static CoordinatorRequestMenu getInstance() {
		if (crm == null) {
			crm = new CoordinatorRequestMenu();
		}
		return crm;
	}
	public void display(Coordinator coordinator){
		Scanner sc = new Scanner(System.in);
		CoordController cc = CoordController.getInstance();
		ArrayList<Request> requests = cc.getrequests();
        int page = 1;
        int numRequests = requests.size();
    	int numPages = (int) Math.ceil((float)numRequests/(float)5);
        do {
        	if(page <= numPages) {
        		cc.viewAllRequests(coordinator, page);
        	}
            try {
            	System.out.println("Enter 0 to return or a valid integer from 1 - " + numPages);
            	page = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice.");
                sc.nextLine();
                continue;
            }
        } while(page != 0);
    }
}
