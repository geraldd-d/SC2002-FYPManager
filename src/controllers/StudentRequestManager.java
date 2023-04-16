package controllers;

import java.util.ArrayList;
import java.util.List;

import entities.AllocRequest;
import entities.Coordinator;
import entities.DeregRequest;
import entities.Faculty;
import entities.Project;
import entities.Request;
import entities.RequestStatus;
import entities.Student;
import entities.TitleRequest;

public class StudentRequestManager implements IStudentRequestManager{
	private static StudentRequestManager srm = null;
	private ArrayList<Request> requests;
	private StudentRequestManager(ArrayList<Request> requests) {
		this.requests = requests;
	}
	public static StudentRequestManager getInstance(ArrayList<Request> requests) {
		if (srm == null) {
			srm = new StudentRequestManager(requests);
		}
		return srm;
	}
	public static StudentRequestManager getInstance() {
		return srm;
	}
	@Override
	public void saveChanges() {
		RequestController rc = RequestController.getInstance();
		rc.updateRequests(requests);
	}

	@Override
	public void viewHistory(Student user, int page) {
		int pageSize = 5;
	    ArrayList<Request> reqs = user.getHistory();
	    int startIndex = (page - 1) * pageSize;
	    int endIndex = Math.min(startIndex + pageSize, reqs.size());
	    List<Request> currentPage = reqs.subList(startIndex, endIndex);
	    currentPage.forEach((Request)-> Request.printRequest());
	}

	@Override
	public void addAllocationRequest(Student s, Project p) {
		RequestController rc = RequestController.getInstance();
		FacultyController fc = FacultyController.getInstance();
		Integer id = rc.getNewID();
		Coordinator c = fc.getCoord();
		AllocRequest ar = new AllocRequest(id, s, c, RequestStatus.Pending, p);
		if (checkPending(s,ar)) {
			System.out.println("You already have a pending allocation request.");
			return;
		}
		requests.add(ar);
		s.addHistory(ar);
		c.addInbox(ar);
	}

	@Override
	public void addDeregistrationRequest(Student s, Project p) {
		RequestController rc = RequestController.getInstance();
		FacultyController fc = FacultyController.getInstance();
		Coordinator c = fc.getCoord();
		Integer id = rc.getNewID();
		DeregRequest dr = new DeregRequest(id, s, c, RequestStatus.Pending, p);
		if (checkPending(s,dr)) {
			System.out.println("You already have a pending allocation request.");
			return;
		}
		requests.add(dr);
		s.addHistory(dr);
		c.addInbox(dr);
	}

	@Override
	public void addTitleRequest(Student s, Project p, String title) {
		RequestController rc = RequestController.getInstance();
		FacultyController fc = FacultyController.getInstance();
		Faculty supervisor = fc.getFacultybyID(p.getSupervisorID());
		Integer id = rc.getNewID();
		TitleRequest tr = new TitleRequest(id, s, supervisor, RequestStatus.Pending, p, title);
		if (checkPending(s,tr)) {
			System.out.println("You already have a pending allocation request.");
			return;
		}
		requests.add(tr);
		s.addHistory(tr);
		supervisor.addInbox(tr);
	}
	private boolean checkPending(Student s, Request r) {
		ArrayList<Request> requests = s.getHistory();
		for (Request req : requests) {
			if (req.getType().equals(r.getType()) && req.getStatus().equals(RequestStatus.Pending)) {
				return true;
			}
		}
		return false;
	}
}
