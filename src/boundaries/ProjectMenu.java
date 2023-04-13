package boundaries;

import java.util.InputMismatchException;
import java.util.Scanner;

import controllers.LoginController;
import controllers.ProjectManager;
import controllers.ProjectsController;
import controllers.StudentController;
import entities.Coordinator;
import entities.Faculty;
import entities.Student;
import entities.User;

public class ProjectMenu implements BaseMenu{
	private ProjectMenu() {};
	private static ProjectMenu pm = null;
	public static ProjectMenu getInstance() {
		if (pm == null) {
			pm = new ProjectMenu();
		}
		return pm;
	}
	public void display(){
		Scanner sc = new Scanner(System.in);
        int page = 1;
        StudentController stc = StudentController.getInstance();
		ProjectManager pm = ProjectManager.getInstance();
        int numProjects = pm.getAllAvailableProjects().size();
    	int numPages = (int) Math.ceil((float)numProjects/(float)5);
        do {
        	if(page <= numPages) {
        		stc.ViewAllAvailableProjects(page);
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
