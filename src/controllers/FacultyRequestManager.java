package controllers;

import java.util.ArrayList;
import java.util.List;

import entities.Faculty;
import entities.Project;
import entities.Request;
import entities.RequestStatus;
import entities.Student;
import entities.TitleRequest;
import entities.TransferRequest;
import entities.User;

public class FacultyRequestManager implements IFacultyRequestManager {
	private static FacultyRequestManager frm = null;
	private ArrayList<Request> requests;
	private FacultyRequestManager(ArrayList<Request> requests) {
		this.requests = requests;
	}
	
	/** 
	 * @param requests
	 * @return FacultyRequestManager
	 */
	public static FacultyRequestManager getInstance(ArrayList<Request> requests) {
		if (frm == null) {
			frm = new FacultyRequestManager(requests);
		}
		return frm;
	}
	
	/** 
	 * @return FacultyRequestManager
	 */
	public static FacultyRequestManager getInstance() {
		return frm;
	}
	
	/** 
	 * @param f
	 * @param p
	 * @param replacement
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
	 * @param user
	 * @param page
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
	 * @param user
	 * @param page
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
	 * @param user
	 */
	@Override
	public void viewPending(Faculty user) {
		ArrayList<Request> requests = getPendingReqs(user);
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
	 * @param r
	 */
	@Override
	public void rejectRequest(TitleRequest r) {
		r.setStatus(RequestStatus.Rejected);
	}
	
	/** 
	 * @param f
	 * @param r
	 * @return boolean
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
