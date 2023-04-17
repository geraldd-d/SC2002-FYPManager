package controllers;

import java.util.ArrayList;
import java.util.List;

import entities.Coordinator;
import entities.Faculty;
import entities.Project;
import entities.Request;
import entities.RequestStatus;
import entities.Student;
import entities.User;

/**
 * This class is the manager for the requests made by the coordinator. It implements the ICoordRequestManager interface.
 */
public class CoordRequestManager implements ICoordRequestManager {
	private static CoordRequestManager crsc = null;
	private ArrayList<Request> requests;
	private CoordRequestManager(ArrayList<Request> requests) {
		this.requests = requests;
	}

	
	/**
	 * This method is used to get the instance of the CoordRequestManager class. It is a singleton class.  
	 * @param requests The list of request in the system.
	 * @return The instance of the CoordRequestManager class.
	 */
	public static CoordRequestManager getInstance(ArrayList<Request> requests) {
		if (crsc == null) {
			crsc = new CoordRequestManager(requests);
		}
		return crsc;
	}
	
	/** 
	 * This method is used to get the instance of the CoordRequestManager class. It is a singleton class. 
	 * @return The instance of the CoordRequestManager class.
	 */
	public static CoordRequestManager getInstance() {
		return crsc;
	}
	
	/** 
	 * This method is used to reject request.
	 * @param r The request which is going to be rejected.
	 */
	@Override
	public void rejectRequest(Request r) {
		r.setStatus(RequestStatus.Rejected);
	}

	
	/** 
	 * This methode is used to approve allocation request.
	 * @param r The allocation request which is going to be approved. 
	 */
	@Override
	public void approveAllocation(Request r) {
		FacultyController fc = FacultyController.getInstance();
		CoordProjectManager cpm = CoordProjectManager.getInstance();
		Project p = r.getProject();
		String id = p.getSupervisorID();
		Faculty supervisor = fc.getFacultybyID(id);
		if (supervisor.getActiveProjects() >= 2) {
			System.out.println("Supervisor already has the maximum number of projects.");
			return;
		}
		r.setStatus(RequestStatus.Approved);
		Student requestor = (Student) r.getRequestor();
		cpm.allocateProject(requestor, p);
	}

	
	/** This method is used to approve deregistration request and reject all other pending requests for that project.
	 * @param r This is the request which will be approved for deregisteration.
	 */
	@Override
	public void approveDeregistration(Request r) {
		CoordProjectManager cpm = CoordProjectManager.getInstance();
		r.setStatus(RequestStatus.Approved);
		Project p = r.getProject();
		Student requestor = (Student) r.getRequestor();
		for (Request request : requests) {
			if (request.getStatus().equals(RequestStatus.Pending) && request.getProject().equals(p)) {
				rejectRequest(request);
			}
		}
		cpm.deregisterProject(requestor, p);
	}

	
	/** 
	 * This method is used to approve transfer request.
	 * @param r The request made for transfer.
	 */
	@Override
	public void approveTransfer(Request r) {
		FacultyController fc = FacultyController.getInstance();
		CoordProjectManager cpm = CoordProjectManager.getInstance();
		Project p = r.getProject();
		Faculty requestor = (Faculty) r.getRequestor();
		String replacementID = r.getChanges();
		Faculty replacement = fc.getFacultybyID(replacementID);
		String replacementName = replacement.getName();
		if (replacement.getActiveProjects() >= 2) {
			System.out.println("Supervisor already has the maximum number of projects.");
			return;
		}
		cpm.transferProject(requestor, replacementID, replacementName, p);
		r.setStatus(RequestStatus.Approved);
	}

	/**
	 * This method is used to save the changes. 
	 */
	@Override
	public void saveChanges() {
		RequestController rc = RequestController.getInstance();
		rc.updateRequests(requests);
	}

	
	/** 
	 * This method is used to approve title change request.
	 * @param r The request made for title change.
	 */
	@Override
	public void approveTitleChange(Request r) {
		r.setStatus(RequestStatus.Approved);
		Project p = r.getProject();
		String title = r.getChanges();
		p.setTitle(title);
	}

	
	/** 
	 * This method is made to view all request.
	 * @param page The page which contains all the requests.
	 */
	public void viewAllRequests(int page) {
		int pageSize = 5;
		int startIndex = (page - 1) * pageSize;
		int endIndex = Math.min(startIndex + pageSize, requests.size());
		List<Request> currentPage = requests.subList(startIndex, endIndex);
		currentPage.forEach((request)->request.printRequest());
	}
	
	/** 
	 * This method is used to view the inbox. 
	 * @param coordinator The coordinator who is going to view the inbox.
	 * @param page The page which is being viewed in the Inbox.
	 */
	public void viewInbox(Coordinator coordinator, int page) {
		ArrayList<Request> requests = coordinator.getInbox();
		int pageSize = 5;
		int startIndex = (page - 1) * pageSize;
		int endIndex = Math.min(startIndex + pageSize, requests.size());
		List<Request> currentPage = requests.subList(startIndex, endIndex);
		currentPage.forEach((request)->request.printRequest());
	}
	
	/** 
	 * The method used to view pending request.
	 * @param c The coordinator who is viewing the pending request.
	 */
	@Override
	public void viewPending(Coordinator c) {
		ArrayList<Request> requests = getPendingReqs(c);
		requests.forEach((request)->request.printRequest());
	}

	
	/** 
	 * This method is used to get a list of pending requests.
	 * @param user The user who is going to view the list of pending requests.
	 * @return The list which contains the pending requests.
	 */
	@Override
	public ArrayList<Request> getPendingReqs(Faculty user) {
		ArrayList<Request> reqs = new ArrayList<Request>();
		ArrayList<Request> inbox = user.getInbox();
		inbox.forEach((request)->{
			if (request.getStatus().equals(RequestStatus.Pending)) {
				reqs.add(request);
			}
		});
		return reqs;
	}
	
	/** 
	 * This method is used to get the pending request by their ID.
	 * @param user The user whose pending requests are called.
	 * @param id The projectID which will bring the project.
	 * @return The pending requests of user.
	 */
	public Request getPendingRequestbyID(Faculty user, int id) {
		ArrayList<Request> pending = getPendingReqs(user);
		for (Request r : pending) {
			if (r.getRequestID() == id) {
				return r;
			}
		}
		return null;
	}
	
	/** 
	 * This method is used to get requests.
	 * @return The requests which are called.
	 */
	public ArrayList<Request> getRequests() {
		return requests;
	}
	
	
	/** 
	 * This method allows the user to view history on a particular page. 
	 * @param user The user whose history is viewed.
	 * @param page The page which is viewed.
	 */
	public void viewHistory(User user, int page) {
		int pageSize = 5;
	    ArrayList<Request> reqs = user.getHistory();
	    int startIndex = (page - 1) * pageSize;
	    int endIndex = Math.min(startIndex + pageSize, reqs.size());
	    List<Request> currentPage = reqs.subList(startIndex, endIndex);
	    currentPage.forEach((Request)-> Request.printRequest());
	}

}
