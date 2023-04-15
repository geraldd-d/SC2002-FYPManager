package controllers;

import java.util.ArrayList;
import java.util.List;

import entities.Request;
import entities.*;

public class RequestManager {
	private static RequestManager rm = null;
	private ProjectManager pm;
	private StudentController sc;
	private FacultyController fc;
	private ArrayList<Request> requestList;
	private RequestManager(ArrayList<Request> requestList) {
		this.requestList = requestList;
		pm = ProjectManager.getInstance();
		sc = StudentController.getInstance();
		fc = FacultyController.getInstance();
	}

	public static RequestManager getInstance(ArrayList<Request> requestList) {
		if (rm == null) {
			rm = new RequestManager(requestList);
		}
		return rm;
	}
	public static RequestManager getInstance() {
		return rm;
	}
	public void addAllocRequest(Student s,Project p){
		FacultyController fc = FacultyController.getInstance();
		ProjectManager pm = ProjectManager.getInstance();
		Coordinator coord = fc.getCoord();
		RequestController rc = RequestController.getInstance();
		String id = String.valueOf(rc.getNewID());
		AllocRequest ar = new AllocRequest(id, s, coord, RequestStatus.Pending, p);
		this.requestList.add(ar);
		s.addHistory(ar);
		coord.addInbox(ar);
		pm.reserveProject(p);
	}
	public void addDeregRequest(Student s){
		FacultyController fc = FacultyController.getInstance();
		Coordinator coord = fc.getCoord();
		Project p = s.getRegisteredProject();
		RequestController rc = RequestController.getInstance();
		String id = String.valueOf(rc.getNewID());
		DeregRequest dr = new DeregRequest(id,s, coord, RequestStatus.Pending,p);
		this.requestList.add(dr);
		s.addHistory(dr);
		coord.addInbox(dr);
	}
	public void addTransferRequest(Faculty f, Project p, String replacement){
		FacultyController fc = FacultyController.getInstance();
		Coordinator coord = fc.getCoord();
		RequestController rc = RequestController.getInstance();
		String id = String.valueOf(rc.getNewID());
		TransferRequest trr = new TransferRequest(id,f, coord, RequestStatus.Pending,p,fc.getFacultybyID(replacement));
		this.requestList.add(trr);
		f.addHistory(trr);
		coord.addInbox(trr);
	}
	public void addTitleRequest(Student s,String title){
		FacultyController fc = FacultyController.getInstance();
		Project p = s.getRegisteredProject();
		String supervisorid = p.getSupervisorID();
		Faculty supervisor = fc.getFacultybyID(supervisorid);
		RequestController rc = RequestController.getInstance();
		String id = String.valueOf(rc.getNewID());
		TitleRequest tr = new TitleRequest(id,s, supervisor, RequestStatus.Pending, p, title);
		this.requestList.add(tr);
		s.addHistory(tr);
		supervisor.addInbox(tr);
	}
	public void rejectRequest(Request r) {
		r.setStatus(RequestStatus.Rejected);
		return;
	}
	public void approveAllocation(Request r) {
		r.setStatus(RequestStatus.Approved);
		pm.allocateProject((Student)r.getRequestor(), r.getProject().getID());
	}
	public void approveDeregistration(Request r) {
		r.setStatus(RequestStatus.Approved);
		pm.deregisterProject((Student)r.getRequestor());

	}
	public void approveTransfer(Request r) {
		r.setStatus(RequestStatus.Approved);
		Faculty sub = fc.getFacultybyID(r.getChanges());
		pm.transferProject((Faculty) r.getRequestor(),sub, r.getProject());
	}	
	public void approveTitleChange(Request r) {
		r.setStatus(RequestStatus.Approved);
		pm.editProjectTitle(r.getProject(), r.getChanges());
	}
	protected ArrayList<Request> getRequests(){
		return this.requestList;
	}
	public void viewHistory(User user, int page) {
	    int pageSize = 5;
	    ArrayList<Request> reqs = user.getHistory();
	    int startIndex = (page - 1) * pageSize;
	    int endIndex = Math.min(startIndex + pageSize, reqs.size());
	    List<Request> currentPage = reqs.subList(startIndex, endIndex);
	    currentPage.forEach((Request)-> Request.printRequest());
	}
	public void viewInbox(Faculty user, int page) {
	    int pageSize = 5;
	    ArrayList<Request> reqs = user.getInbox();
	    int startIndex = (page - 1) * pageSize;
	    int endIndex = Math.min(startIndex + pageSize, reqs.size());
	    List<Request> currentPage = reqs.subList(startIndex, endIndex);
	    currentPage.forEach((Request)-> Request.printRequest());
	}
	protected ArrayList<Request> getPendingReqs(Faculty user){
		ArrayList<Request> reqs = new ArrayList<Request>();
		ArrayList<Request> inbox = user.getInbox();
		inbox.forEach((request)->{
			if (request.getStatus().equals(RequestStatus.Pending)) {
				reqs.add(request);
			}
		});
		return reqs;
	}
	public void viewPending(Faculty user, int page) {
	    int pageSize = 5;
	    ArrayList<Request> reqs = getPendingReqs(user);
	    int startIndex = (page - 1) * pageSize;
	    int endIndex = Math.min(startIndex + pageSize, reqs.size());
	    List<Request> currentPage = reqs.subList(startIndex, endIndex);
	    currentPage.forEach((Request)-> Request.printRequest());
	}

	public void saveChanges() {
		// TODO Auto-generated method stub
		RequestController rc = RequestController.getInstance();
	    rc.updateRequests(requestList);
	}
}
