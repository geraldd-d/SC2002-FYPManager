package boundaries;

import java.util.InputMismatchException;
import java.util.Scanner;

import controllers.*;
import entities.*;

/**
 * This class is the boundary for the ProjectMenu class.
 */
public class ProjectMenu implements BaseMenu{
	private ProjectMenu() {
	};
	private static ProjectMenu pm = null;
	
	/** 
	 * @return ProjectMenu
	 */
	public static ProjectMenu getInstance() {
		if (pm == null) {
			pm = new ProjectMenu();
		}
		return pm;
	}
	public void display(){
		Scanner sc = new Scanner(System.in);
        int page = 1;
        StudentProjectManager spm = StudentProjectManager.getInstance();
        int numProjects = spm.getAllAvailableProjects().size();
    	int numPages = (int) Math.ceil((float)numProjects/(float)5);
        do {
        	if(page <= numPages) {
        		spm.viewAllAvailableProjects(page);
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
