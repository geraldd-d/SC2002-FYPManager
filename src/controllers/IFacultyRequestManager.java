package controllers;

import java.util.ArrayList;

import entities.Faculty;
import entities.Project;
import entities.Request;
import entities.TitleRequest;

/**
 * This interface is the interface for the FacultyRequestManager class.
 */
public interface IFacultyRequestManager {
	/**
	 * This method is used to view all projects that the faculty is registered to.
	 * @param f The faculty whose projects are to be viewed.
	 * @param p The project that the faculty is requesting to be allocated to.
	 * @param replacement The ID of the replacement faculty member.
	 */
	public void addTransferRequest(Faculty f, Project p, String replacement);
	/**
	 * This method is used to view the past project history.
	 * @param user The faculty whose projects are to be viewed.
	 * @param page The page number of the projects to be viewed.
	 */
	public void viewHistory(Faculty user, int page);
	/**
	 * This method is used to get the inbox of the faculty.
	 * @param user The faculty whose projects are to be viewed.
	 * @param page The page number of the projects to be viewed.
	 */
	public void viewInbox(Faculty user, int page);
	/**
	 * This method is used to print the pending requests of the faculty.
	 * @param user The faculty whose projects are to be viewed.
	 */
	public void viewPending(Faculty user);
	/**
	 * This method is used to get a list of all the pending requests of the faculty.
	 * @param user The faculty whose projects are to be viewed.
	 * @return An ArrayList of all the pending requests of the faculty.
	 */
	public ArrayList<Request> getPendingReqs(Faculty user);
	/**
	 * This method is to save the changes made to the request.
	 */
	public void saveChanges();
	/**
	 * This method is used to approve the title change request.
	 * @param r The request to be approved.
	 */
	public void approveTitleChange(Request r);
	/**
	 * This method is to reject the title change request.
	 * @param r The request to be rejected
	 */
	public void rejectRequest(TitleRequest r);
	/**
	 * This method is used to get the pending request by ID.
	 * @param user The user who is viewing the request by ID.
	 * @param id The ID which is used to call request.
	 * @return Yes or no, if there are pending requests.
	 */
	public Request getPendingRequestbyID(Faculty user, int id);
}
