package controllers;

import entities.Faculty;
import entities.Project;

/**
 * This interface is used to manage the projects of a faculty.
 */
public interface IFacultyProjectManager {
	/**
	 * This method is used to add a project to the system.
	 * @param user The faculty who is adding the project.
	 * @param title The title of the project.
	 */
	public void addProject(Faculty user,String title);
	/**
	 * This method is used to view the projects that a faculty member owns.
	 * @param user The faculty member whose projects are to be viewed.
	 */
	public void viewOwnProjects(Faculty user);
	/**
	 * This method is used to view the active projects in the system.
	 * @param user The faculty member who is viewing the active projects.
	 */
	public void viewActiveProjects(Faculty user);
	/**
	 * This method is to change the title of a project.
	 * @param p The project whose title is to be changed.
	 * @param t The new title of the project.
	 */
	public void changeTitle(Project p, String t);
	/**
	 * This method is to get the project by its ID.
	 * @param projectID The ID of the project to be retrieved.
	 * @return The project with the given ID.
	 */
	public Project getProjectByID(Integer projectID);
	/**
	 * This method is used to save the changes made to the projects.
	 */
	public void saveChanges();
}
