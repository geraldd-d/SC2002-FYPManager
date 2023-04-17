package controllers;

import java.util.ArrayList;
import java.util.List;

import entities.Project;
import entities.ProjectStatus;

/**
 * This class is used to manage the projects of a student. It implements the IStudentProjectManager interface.
 */
public class StudentProjectManager implements IStudentProjectManager{
	private static StudentProjectManager spm = null;
	private ArrayList<Project> projects;
	private StudentProjectManager(ArrayList<Project> projects) {
		this.projects = projects;
	}

	/** 
	 * This method is used to get the instance of the StudentProjectManager class. It is a singleton class.
	 * @param projects The list of projects
	 * @return The instance of the StudentProjectManager class.
	 */
	public static StudentProjectManager getInstance(ArrayList<Project> projects) {
		if (spm == null) {
			spm = new StudentProjectManager(projects);
		}
		return spm;
	}
	
	/** 
	 * This method is used to get the instance of the StudentProjectManager class. It is a singleton class.
	 * @return The instance of the StudentProjectManager class.
	 */
	public static StudentProjectManager getInstance() {
		return spm;
	}
	
	/** 
	 * This method is used to call a list of available projects.
	 * @return The list of available projects which is called.
	 */
	@Override
	public ArrayList<Project> getAllAvailableProjects(){
		ArrayList<Project> availableProjects = new ArrayList<Project>();
        for (Project project : projects) {
            if (project.getStatus().equals(ProjectStatus.Available)) {
                availableProjects.add(project);
            }
        }
        return availableProjects;
	}

	
	/** 
	 * This method is used to view all the available projects.
	 * @param page The page which is viewed for the available projects.
	 */
	@Override
	public void viewAllAvailableProjects(int page) {
		ArrayList<Project>projects = getAllAvailableProjects();
		int pageSize = 5;
		int startIndex = (page - 1) * pageSize;
		int endIndex = Math.min(startIndex + pageSize, projects.size());
		List<Project> currentPage = projects.subList(startIndex, endIndex);
		currentPage.forEach((project)->project.printProject());
	}

	/**
	 * This method is used to print available projects.
	 */
	public void viewAvailable() {
		ArrayList<Project>projects = getAllAvailableProjects();
		projects.forEach((project)->project.printProject());
	}
	/** 
	 * This method is used to reserve projects.
	 * @param p The project which is reserved.
	 */
	@Override
	public void reserveProject(Project p) {
		if (p.getStatus().equals(ProjectStatus.Available)) {
			p.setStatus(ProjectStatus.Reserved);
			System.out.println("Project reserved.");
		}
	}

	/**
	 * This method is used to save changes.
	 */
	@Override
	public void saveChanges() {
		ProjectsController pc = ProjectsController.getInstance();
	    pc.updateProjects(projects);
	}
	
	
}
