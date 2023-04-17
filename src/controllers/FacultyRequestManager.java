package controllers;

import java.util.ArrayList;
import java.util.List;

import entities.Faculty;
import entities.Project;
import entities.Request;
import entities.RequestStatus;
import entities.TitleRequest;
import entities.TransferRequest;

/**
 * This class is the manager for the faculty requests. It implements the IFacultyRequestManager interface.
 */
public class FacultyRequestManager implements IFacultyRequestManager {
	private static FacultyRequestManager frm = null;
	private ArrayList<Request> requests;
	private FacultyRequestManager(ArrayList<Request> requests) {
		this.requests = requests;
	}

	/** 
	 * This method is used to get the instance of the FacultyRequestManager class. It is a singleton class. 
	 * @param requests
	 * @return The instance of the FacultyRequestManager class.
	 */
	public static FacultyRequestManager getInstance(ArrayList<Request> requests) {
		if (frm == null) {
			frm = new FacultyRequestManager(requests);
		}
		return frm;
	}
	
	/** 
	 * This method is used to get the instance of the FacultyRequestManager class. It is a singleton class. 
	 * @return The instance of the FacultyRequestManager class.
	 */
	public static FacultyRequestManager getInstance() {
		return frm;
	}
	
	/** 
	 * This method is used to add transfer request in the request history.
	 * @param f The faculty whose has request history would be updated.
	 * @param p The project whose transfer has been requested 
	 * @param replacement The replacement supervisor who will replace the current supervisor.
	 */
	@Override
	public void addTransferRequest(Faculty f, Project p, String replacement) {
		RequestController rc = RequestController.getInstance();
		FacultyController fc = FacultyController.getInstance();
		Integer id = rc.getNewID();
		Faculty replacementSupervisor = fc.getFacultybyID(replacement);
		TransferRequest tr = new TransferRequest(id, f,fc.getCoord() , RequestStatus.Pending, p, replacementSupervisor);
		if (checkPending(f, tr)) {
			System.out.println("You already have a similar pending request.");
			return;
		}
		f.addHistory(tr);
		fc.getCoord().addInbox(tr);
		requests.add(tr);
	}

	
	/** 
	 * This method allows the user to view history on a particular page. 
	 * @param user The user whose history is viewed.
	 * @param page The page which is viewed.
	 */
	@Override
	public void viewHistory(Faculty user, int page) {
		ArrayList<Request> requests = user.getHistory();
		int pageSize = 5;
		int startIndex = (page - 1) * pageSize;
		int endIndex = Math.min(startIndex + pageSize, requests.size());
		List<Request> currentPage = requests.subList(startIndex, endIndex);
		currentPage.forEach((request)->request.printRequest());
	}

	
	/** 
	 * This method is used to view the inbox. 
	 * @param user The user who is going to view the inbox.
	 * @param page The page which is being viewed in the Inbox.
	 */
	@Override
	public void viewInbox(Faculty user,int page) {
		ArrayList<Request> requests = user.getInbox();
		int pageSize = 5;
		int startIndex = (page - 1) * pageSize;
		int endIndex = Math.min(startIndex + pageSize, requests.size());
		List<Request> currentPage = requests.subList(startIndex, endIndex);
		currentPage.forEach((request)->request.printRequest());
	}

	
	/** 
	 * The method used to view pending request.
	 * @param user The coordinator who is viewing the pending request.
	 */
	@Override
	public void viewPending(Faculty user) {
		ArrayList<Request> requests = getPendingReqs(user);
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
	 * This method is used to reject request.
	 * @param r The request which is going to be rejected.
	 */
	@Override
	public void rejectRequest(TitleRequest r) {
		r.setStatus(RequestStatus.Rejected);
	}

	/** 
	 * This method is used to get the pending request by their ID.
	 * @param user The user whose pending requests are called.
	 * @param id The projectID which will bring the project.
	 * @return The pending requests of user.
	 */
	@Override
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
	 * This method is used to check the pending request.
	 * @param f The supervisor who will the view the pending request.
	 * @param r The request which is pending
	 * @return Yes or No, if the request is pending.
	 */
	private boolean checkPending(Faculty f, Request r) {
		ArrayList<Request> requests = f.getHistory();
		for (Request req : requests) {
			if (req.getType().equals(r.getType()) && req.getStatus().equals(RequestStatus.Pending) && req.getChanges().equals(r.getChanges())) {
				return true;
			}
		}
		return false;
	}

}
