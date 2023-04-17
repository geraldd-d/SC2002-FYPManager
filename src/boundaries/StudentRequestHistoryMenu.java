package boundaries;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


import controllers.StudentRequestManager;
import entities.Request;
import entities.Student;

/**
 * This class is the boundary for the StudentRequestHistoryMenu class.
 */
public class StudentRequestHistoryMenu{
	private StudentRequestHistoryMenu() {};
	private static StudentRequestHistoryMenu rhm = null;
	
	/** 
	 * @return RequestHistoryMenu
	 */
	public static StudentRequestHistoryMenu getInstance() {
		if (rhm == null) {
			rhm = new StudentRequestHistoryMenu();
		}
		return rhm;
	}
	
	/** 
	 * @param user
	 * @param reqHist
	 */
	public void display(Student user, ArrayList<Request> reqHist){
		Scanner sc = new Scanner(System.in);
		StudentRequestManager srm = StudentRequestManager.getInstance();

        int page = 1;
        int numRequests = reqHist.size();
        if (numRequests == 0) {
        	System.out.println("You have no prior requests!");
        	return;
        }
    	int numPages = (int) Math.ceil((float)numRequests/(float)5);
        do {
        	if(page <= numPages) {
            	System.out.println("Page " + page + " of " + numPages);
            	System.out.println();
        		srm.viewHistory((Student) user, page);
        	}
            try {
            	System.out.println("Enter 0 to return or a valid integer from 1 -" + numPages);
            	page = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice. Enter 0 to return or a valid integer from 1 - " + numPages);
                sc.nextLine();
                continue;
            }
        } while(page != 0);
    }
}
