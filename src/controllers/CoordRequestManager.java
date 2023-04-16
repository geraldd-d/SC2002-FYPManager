package controllers;

import java.util.ArrayList;
import java.util.List;

import entities.Coordinator;
import entities.Faculty;
import entities.Project;
import entities.Request;
import entities.RequestStatus;
import entities.Student;

public class CoordRequestManager implements ICoordRequestManager {
	private static CoordRequestManager crsc = null;
	private ArrayList<Request> requests;
	private CoordRequestManager(ArrayList<Request> requests) {
		this.requests = requests;
	}
	public static CoordRequestManager getInstance(ArrayList<Request> requests) {
		if (crsc == null) {
			crsc = new CoordRequestManager(requests);
		}
		return crsc;
	}
	public static CoordRequestManager getInstance() {
		return crsc;
	}
	@Override
	public void rejectRequest(Request r) {
		r.setStatus(RequestStatus.Rejected);
	}

	@Override
	public void approveAllocation(Request r) {
		FacultyController fc = FacultyController.getInstance();
		CoordProjectManager cpm = CoordProjectManager.getInstance();
		StudentController sc = StudentController.getInstance();
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

	@Override
	public void approveDeregistration(Request r) {
		CoordProjectManager cpm = CoordProjectManager.getInstance();
		r.setStatus(RequestStatus.Approved);
		Project p = r.getProject();
		Student requestor = (Student) r.getRequestor();
		cpm.deregisterProject(requestor, p);
	}

	@Override
	public void approveTransfer(Request r) {
		FacultyController fc = FacultyController.getInstance();
		CoordProjectManager cpm = CoordProjectManager.getInstance();
		StudentController sc = StudentController.getInstance();
		Project p = r.getProject();
		Faculty requestor = (Faculty) r.getRequestor();
		String replacementID = r.getChanges();
		Faculty replacement = fc.getFacultybyID(replacementID);
		String replacementName = replacement.getName();
		if (replacement.getActiveProjects() >= 2) {
			System.out.println("Supervisor already has the maximum number of projects.");
			return;
		}
		cpm.transferProject(replacement, replacementID, replacementName, p);
		r.setStatus(RequestStatus.Approved);
	}

	@Override
	public void saveChanges() {
		RequestController rc = RequestController.getInstance();
		rc.updateRequests(requests);
	}

	@Override
	public void approveTitleChange(Request r) {
		r.setStatus(RequestStatus.Approved);
		Project p = r.getProject();
		String title = r.getChanges();
		p.setTitle(title);
	}

	public void viewAllRequests(int page) {
		int pageSize = 5;
		int startIndex = (page - 1) * pageSize;
		int endIndex = Math.min(startIndex + pageSize, requests.size());
		List<Request> currentPage = requests.subList(startIndex, endIndex);
		currentPage.forEach((request)->request.printRequest());
	}
	public void viewInbox(Coordinator coordinator, int page) {
		ArrayList<Request> requests = coordinator.getInbox();
		int pageSize = 5;
		int startIndex = (page - 1) * pageSize;
		int endIndex = Math.min(startIndex + pageSize, requests.size());
		List<Request> currentPage = requests.subList(startIndex, endIndex);
		currentPage.forEach((request)->request.printRequest());
	}
	@Override
	public void viewPending(Coordinator c) {
		ArrayList<Request> requests = getPendingReqs(c);
		requests.forEach((request)->request.printRequest());
	}

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
	public Request getPendingRequestbyID(Faculty user, int id) {
		ArrayList<Request> pending = getPendingReqs(user);
		for (Request r : pending) {
			if (r.getRequestID() == id) {
				return r;
			}
		}
		return null;
	}
	public ArrayList<Request> getRequests() {
		return requests;
	}

}
