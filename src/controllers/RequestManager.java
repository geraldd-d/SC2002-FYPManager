package controllers;

import java.util.ArrayList;
import java.util.List;

import entities.Request;
import entities.*;

public class RequestManager {
	private static RequestManager rm = null;
	private ArrayList<Request> requestList;
	private RequestManager(ArrayList<Request> requestList) {
		this.requestList = requestList;
		StudentController sc = StudentController.getInstance();
		FacultyController fc = FacultyController.getInstance();
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
		AllocRequest ar = new AllocRequest(s, coord, RequestStatus.Pending, p);
		this.requestList.add(ar);
		s.addHistory(ar);
		coord.addInbox(ar);
		pm.reserveProject(p);
	}
	public void addDeregRequest(Student s){
		FacultyController fc = FacultyController.getInstance();
		Coordinator coord = fc.getCoord();
		Project p = s.getRegisteredProject();
		DeregRequest dr = new DeregRequest(s, coord, RequestStatus.Pending,p);
		this.requestList.add(dr);
		s.addHistory(dr);
		coord.addInbox(dr);
	}
	public void addTransferRequest(Faculty f, Project p, String replacement){
		FacultyController fc = FacultyController.getInstance();
		Coordinator coord = fc.getCoord();
		TransferRequest trr = new TransferRequest(f, coord, RequestStatus.Pending,p,fc.getFacultybyID(replacement));
		this.requestList.add(trr);
		f.addHistory(trr);
		coord.addInbox(trr);
	}
	public void addTitleRequest(Student s,String title){
		FacultyController fc = FacultyController.getInstance();
		Project p = s.getRegisteredProject();
		String supervisorid = p.getSupervisorID();
		Faculty supervisor = fc.getFacultybyID(supervisorid);
		TitleRequest tr = new TitleRequest(s, supervisor, RequestStatus.Pending, p, title);
		this.requestList.add(tr);
		s.addHistory(tr);
		supervisor.addInbox(tr);
	}
	
	protected ArrayList<Request> getRequests(){
		return this.requestList;
	}
	public void viewRequests(User user, int page) {
	    int pageSize = 5;
	    ArrayList<Request> reqs = user.getHistory();
	    int startIndex = (page - 1) * pageSize;
	    int endIndex = Math.min(startIndex + pageSize, reqs.size());
	    List<Request> currentPage = reqs.subList(startIndex, endIndex);
	    currentPage.forEach((Request)-> Request.printRequest());
	}
}
