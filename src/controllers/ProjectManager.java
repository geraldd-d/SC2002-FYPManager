package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entities.Faculty;
import entities.Project;
import entities.ProjectStatus;
import entities.Student;
import entities.User;

/**
 * This class is the manager for the Project class.
 */
public class ProjectManager {
	private ArrayList<Project> projects;
	private static ProjectManager pm = null;
	private ProjectManager(ArrayList<Project> p) {
		this.projects = p;
		CoordProjectManager cpm = CoordProjectManager.getInstance(projects);
		FacultyProjectManager fpm = FacultyProjectManager.getInstance(projects);
		StudentProjectManager spm = StudentProjectManager.getInstance(projects);
		capProjects();
	};
	
	/**
	 * This method is used to get the instance of the ProjectManager class. It is a singleton class.
	 * @param p The list of projects in the system.
	 * @return The instance of the ProjectManager class.
	 */
	public static ProjectManager getInstance(ArrayList<Project> p) {
		if (pm == null) {
			pm = new ProjectManager(p);
		}
		return pm;
	}

	/**
	 * This method is used to get the instance of the ProjectManager class. It is a singleton class.
	 * @return The instance of the ProjectManager class.
	 */
	public static ProjectManager getInstance() {
		return pm;
	}

	/**
	 * This method is used to set a cap on the number of projects a faculty can supervise.
	 */
	public void capProjects() {
		FacultyController fc = FacultyController.getInstance();
		projects.forEach((p)->{
			Faculty f = fc.getFacultybyID(p.getSupervisorID());
			if (f.getActiveProjects() >= 2 && p.getStatus().equals(ProjectStatus.Available)) {
				p.setStatus(ProjectStatus.Unavailable);
			}
		});
	}
	
	/**
	 * This method is used to save the changes made to the projects.
	 */
	public void saveChanges() {
	    ProjectsController pc = ProjectsController.getInstance();
	    pc.updateProjects(projects);
	}

	/**
	 * This method is used to get the projects by their ID.
	 * @param projectID The ID of the project to be retrieved.
	 * @return The project with the given ID.
	 */
	public Project getProjectByID(Integer projectID) {
		for (Project project : projects) {
			if (project.getID().equals(projectID)) {
				return project;
			}
		}
		return null;
	}
}
