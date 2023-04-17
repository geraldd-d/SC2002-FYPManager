package controllers;

import java.util.ArrayList;

import entities.Faculty;
import entities.Project;
import entities.ProjectStatus;

/**
 * This class is the manager for the faculty projects. It implements the IFacultyProjectManager interface.
 */
public class FacultyProjectManager implements IFacultyProjectManager{
	private static FacultyProjectManager fpm = null;
	private ArrayList<Project> projects;
	private FacultyProjectManager(ArrayList<Project> projects) {
		this.projects = projects;
	}
	
	/**
	 * This method is used to get the instance of the FacultyProjectManager class. It is a singleton class. 
	 * @param projects
	 * @return The instance of the FacultyProjectManager class.
	 */
	public static FacultyProjectManager getInstance(ArrayList<Project> projects) {
		if (fpm == null) {
			fpm = new FacultyProjectManager(projects);
		}
		return fpm;
	}
	
	/**
	 * This method is used to get the instance of the FacultyProjectManager class. It is a singleton class. 
	 * @return FacultyProjectManager The instance of the FacultyProjectManager class.
	 */
	public static FacultyProjectManager getInstance() {
		return fpm;
	}
	
	/** 
	 * This method is used to add projects by the user. 
	 * @param user The user who will add projects.
	 * @param title The title of the project added.
	 */
	@Override
	public void addProject(Faculty user, String title) {
		ProjectsController pc = ProjectsController.getInstance();
		ProjectStatus status;
		if (user.getActiveProjects() < 2) {
			status = ProjectStatus.Available;
		} else {
			status = ProjectStatus.Unavailable;
		}
		Project p = new Project(title, user.getUserID(), user.getName(), "", status, pc.getNewID());
		user.addProject(p);
		projects.add(p);
	}

	
	/** 
	 * This method is used to view the user's own project'
	 * @param user The user whose projects will be viewed.
	 */
	@Override
	public void viewOwnProjects(Faculty user) {
		ArrayList<Project>projects = user.getProjects();
		projects.forEach((p)-> p.printProject());
	}

	
	/** 
	 * This method is used to view the all the user's active projects.
	 * @param user The user whose active projects are viewed.
	 */
	@Override
	public void viewActiveProjects(Faculty user) {
		ArrayList<Project>projects = user.getProjects();
		for (Project p : projects) {
			if (p.getStatus().equals(ProjectStatus.Allocated)) {
				p.printProject();
			}
		}
	}

	
	/** 
	 * This method is used to change the title of projects
	 * @param p The project whose title will be changed
	 * @param t The new title of the project
	 */
	@Override
	public void changeTitle(Project p, String t) {
		p.setTitle(t);
	}

	
	/** 
	 * This method is used to get project by its projectID.
	 * @param projectID The projectID od the project
	 * @return The project according to the projectID.
	 */
	@Override
	public Project getProjectByID(Integer projectID) {
		for (Project project : projects) {
			if (project.getID().equals(projectID)) {
				return project;
			}
		}
		return null;
	}
	@Override
	public void saveChanges() {
		ProjectsController pc = ProjectsController.getInstance();
	    pc.updateProjects(projects);
	}



}
