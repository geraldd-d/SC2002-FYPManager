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
	 * @param projects
	 * @return FacultyProjectManager
	 */
	public static FacultyProjectManager getInstance(ArrayList<Project> projects) {
		if (fpm == null) {
			fpm = new FacultyProjectManager(projects);
		}
		return fpm;
	}
	
	/** 
	 * @return FacultyProjectManager
	 */
	public static FacultyProjectManager getInstance() {
		return fpm;
	}
	
	/** 
	 * @param user
	 * @param title
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
	 * @param user
	 */
	@Override
	public void viewOwnProjects(Faculty user) {
		ArrayList<Project>projects = user.getProjects();
		projects.forEach((p)-> p.printProject());
	}

	
	/** 
	 * @param user
	 */
	@Override
	public void viewActiveProjects(Faculty user) {
		ArrayList<Project>projects = user.getProjects();
		projects.forEach((p)-> p.printActiveProject());
	}

	
	/** 
	 * @param p
	 * @param t
	 */
	@Override
	public void changeTitle(Project p, String t) {
		p.setTitle(t);
	}

	
	/** 
	 * @param projectID
	 * @return Project
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
