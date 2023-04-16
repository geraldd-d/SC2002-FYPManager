package controllers;

import entities.Project;
import entities.Student;

/**
 * This interface is used to define the methods that a StudentRequestManager.
 */
public interface IStudentRequestManager {
	/**
	 * This method is used to save the changes made to the request.
	 */
	public void saveChanges();
	/**
	 * This method is used to view the history of the student.
	 * @param user The student whose history is to be viewed.
	 * @param page The page number of the history to be viewed.
	 */
	public void viewHistory(Student user, int page);
	/**
	 * This method is used to add an allocation request.
	 * @param s The student who is making the request.
	 * @param p The project that the student is requesting to be allocated to.
	 */
	void addAllocationRequest(Student s, Project p);
	/**
	 * This method is used to add a deregistration request.
	 * @param s The student who is making the request.
	 * @param p The project that the student is requesting to be deregistered from.
	 */
	void addDeregistrationRequest(Student s, Project p);
	/**
	 * This method is used to add a title request.
	 * @param s The student who is making the request.
	 * @param p The project that the student is requesting to change the title of.
	 * @param title The new title of the project.
	 */
	void addTitleRequest(Student s, Project p, String title);
}
