package controllers;

import java.util.ArrayList;

import entities.Project;

/**
 * This interface is the interface for the StudentProjectManager class.
 */
public interface IStudentProjectManager {
	/**
	 * This method is used to view all available projects.
	 * @param page The page number of the projects to be viewed.
	 */
	public void viewAllAvailableProjects(int page);
	/**
	 * This method is used to view all projects that the student is registered to.
	 * @param p The student whose projects are to be viewed.
	 */
	public void reserveProject(Project p);
	/**
	 * This method is used to save the changes made to the request.
	 */
    public void saveChanges();
	/**
	 * This method is used to view all projects that the student is registered to.
	 */
	public ArrayList<Project> getAllAvailableProjects();
}
