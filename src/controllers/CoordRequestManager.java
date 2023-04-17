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
	 * @param requests
	 * @return CoordRequestManager
	 */
	public static CoordRequestManager getInstance(ArrayList<Request> requests) {
		if (crsc == null) {
			crsc = new CoordRequestManager(requests);
		}
		return crsc;
	}
	
	/** 
	 * @return CoordRequestManager
	 */
	public static CoordRequestManager getInstance() {
		return crsc;
	}
	
	/** 
	 * @param r
	 */
	@Override
	public void rejectRequest(Request r) {
		r.setStatus(RequestStatus.Rejected);
	}

	
	/** 
	 * @param r
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

	
	/** 
	 * @param r
	 */
	@Override
	public void approveDeregistration(Request r) {
		CoordProjectManager cpm = CoordProjectManager.getInstance();
		r.setStatus(RequestStatus.Approved);
		Project p = r.getProject();
		Student requestor = (Student) r.getRequestor();
		cpm.deregisterProject(requestor, p);
	}

	
	/** 
	 * @param r
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

	@Override
	public void saveChanges() {
		RequestController rc = RequestController.getInstance();
		rc.updateRequests(requests);
	}

	
	/** 
	 * @param r
	 */
	@Override
	public void approveTitleChange(Request r) {
		r.setStatus(RequestStatus.Approved);
		Project p = r.getProject();
		String title = r.getChanges();
		p.setTitle(title);
	}

	
	/** 
	 * @param page
	 */
	public void viewAllRequests(int page) {
		int pageSize = 5;
		int startIndex = (page - 1) * pageSize;
		int endIndex = Math.min(startIndex + pageSize, requests.size());
		List<Request> currentPage = requests.subList(startIndex, endIndex);
		currentPage.forEach((request)->request.printRequest());
	}
	
	/** 
	 * @param coordinator
	 * @param page
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
	 * @param c
	 */
	@Override
	public void viewPending(Coordinator c) {
		ArrayList<Request> requests = getPendingReqs(c);
		requests.forEach((request)->request.printRequest());
	}

	
	/** 
	 * @param user
	 * @return ArrayList<Request>
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
	 * @param user
	 * @param id
	 * @return Request
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
	 * @return ArrayList<Request>
	 */
	public ArrayList<Request> getRequests() {
		return requests;
	}
	
	public void viewHistory(User user, int page) {
		int pageSize = 5;
	    ArrayList<Request> reqs = user.getHistory();
	    int startIndex = (page - 1) * pageSize;
	    int endIndex = Math.min(startIndex + pageSize, reqs.size());
	    List<Request> currentPage = reqs.subList(startIndex, endIndex);
	    currentPage.forEach((Request)-> Request.printRequest());
	}

}
