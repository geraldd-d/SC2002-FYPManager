package boundaries;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import controllers.LoginController;
import controllers.ProjectManager;
import controllers.ProjectService;
import controllers.ProjectDataController;
import controllers.StudentController;
import entities.Coordinator;
import entities.Faculty;
import entities.Project;
import entities.Student;
import entities.User;

public class ProjectMenu{
	private ProjectMenu() {};
	private static ProjectMenu pm = null;
	public static ProjectMenu getInstance() {
		if (pm == null) {
			pm = new ProjectMenu();
		}
		return pm;
	}
	public void display(Student user){
		Scanner sc = new Scanner(System.in);
		ProjectService psc = ProjectService.getInstance();
		StudentController stc = StudentController.getInstance();
		ArrayList<Project> projects = psc.getAllAvailableProjects();
        int page = 1;
        int numProjects = projects.size();
    	int numPages = (int) Math.ceil((float)numProjects/(float)5);
        do {
        	if(page <= numPages) {
        		stc.viewAllAvailableProjects(user, page);
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
