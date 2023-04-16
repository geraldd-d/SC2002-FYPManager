package controllers;

import java.util.ArrayList;

import entities.Coordinator;
import entities.Faculty;
import entities.Request;

/**
 * This interface is used to manage the requests made by coordinators.
 */
public interface ICoordRequestManager {
	/**
	 * This method is used to reject a request.
	 * @param r The request to be rejected.
	 */
	public void rejectRequest(Request r);
	/**
	 * This method is to approve a title change request.
	 * @param r The request to be approved.
	 */
	public void approveTitleChange(Request r);
	/**
	 * This method is to approve a project allocation request.
	 * @param r The request to be approved.
	 */
	public void approveAllocation(Request r);
	/**
	 * This method is to approve a project deregistration request.
	 * @param r The request to be approved.
	 */
	public void approveDeregistration(Request r);
	/**
	 * This method is to approve a project transfer request.
	 * @param r The request to be approved.
	 */
	public void approveTransfer(Request r);
	/**
	 * This method is to view pending requests.
	 * @param c The coordinator who is viewing the requests.
	 */
	public void viewPending(Coordinator c);
	/**
	 * This method is to view all requests.
	 * @param page The page number of the requests to be viewed.
	 */
	public void viewAllRequests(int page);
	/**
	 * This method is to view the inbox of a coordinator.
	 * @param coordinator The coordinator whose inbox is to be viewed.
	 * @param page The page number of the requests to be viewed.
	 */
	public void viewInbox(Coordinator coordinator, int page);
	/**
	 * This method is to view a pending request by its ID.
	 * @param user The user who is viewing the request.
	 * @param id The ID of the request to be viewed.
	 * @return The request with the given ID.
	 */
	public Request getPendingRequestbyID(Faculty user, int id);
	/**
	 * This method is to get the pending requests of a faculty.
	 * @param user The faculty whose requests are to be retrieved.
	 * @return The list of pending requests of the faculty.
	 */
	public ArrayList<Request> getPendingReqs(Faculty user);
	/**
	 * This method is to save all changes
	 */
	public void saveChanges();
	/**
	 * This method is to get all the requests.
	 * @return The list of all requests.
	 */
	public ArrayList<Request> getRequests();
}
