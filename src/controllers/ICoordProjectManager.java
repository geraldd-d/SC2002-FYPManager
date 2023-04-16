package controllers;

import java.util.ArrayList;

import entities.Faculty;
import entities.Project;
import entities.Student;

/**
 * This interface is used to manage the projects from a course coordinator's perspective.
 */
public interface ICoordProjectManager {
	/**
	 * This method is used to allocate a project to a student.
	 * @param student The student to whom the project is to be allocated.
	 * @param p The project to be allocated.
	 */
	public void allocateProject(Student student, Project p);
	/**
	 * This method is used to deregister a project from a student.
	 * @param student The student from whom the project is to be deregistered.
	 * @param p The project to be deregistered.
	 */
	public void deregisterProject(Student student, Project p );
	/**
	 * This method is used to transfer a project from a faculty to another faculty.
	 * @param current The faculty from whom the project is to be transferred.
	 * @param replacementID The ID of the faculty to whom the project is to be transferred.
	 * @param replacementName The name of the faculty to whom the project is to be transferred.
	 * @param p The project to be transferred.
	 */
	public void transferProject(Faculty current, String replacementID, String replacementName, Project p);
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
	 * This method is to get the project by its ID. 
	 * @param projectID The ID of the project to be retrieved.
	 * @return The project with the given ID.
	 */
	public Project getProjectByID(Integer projectID);
	/**
	 * This method is used to save all the changes made
	 */
	public void saveChanges();
	/**
	 * This method is used to change the title of a project.
	 * @param p The project whose title is to be changed.
	 * @param t The new title of the project.
	 */
	void changeTitle(Project p, String t);
	/**
	 * This method is used to get all the projects in the system.
	 * @return An ArrayList of all the projects in the system.
	 */
	public ArrayList<Project> getAllAvailableProjects();
	/**
	 * This method is used to get all the projects that are unavailable.
	 * @return An ArrayList of all the projects that are unavailable.
	 */
	public ArrayList<Project> getAllUnavailableProjects();
	/**
	 * This method is used to get all the projects that are reserved.
	 * @return An ArrayList of all the projects that are reserved.
	 */
	public ArrayList<Project> getAllReservedProjects();
	/**
	 * This method is used to get all the projects that are allocated.
	 * @return An ArrayList of all the projects that are allocated.
	 */
	public ArrayList<Project> getAllAllocatedProjects();
}
