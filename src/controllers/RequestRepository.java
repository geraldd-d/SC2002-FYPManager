package controllers;

import java.util.ArrayList;

import entities.AllocRequest;
import entities.DeregRequest;
import entities.Faculty;
import entities.Project;
import entities.Request;
import entities.RequestStatus;
import entities.Student;
import entities.TitleRequest;
import entities.TransferRequest;
import entities.User;

public class RequestRepository {
	private static RequestRepository rrp = null;
	private ArrayList<Request> requestList;
	private RequestRepository() {
		this.requestList = new ArrayList<Request>();
	};
	public static RequestRepository getInstance() {
		if (rrp == null) {
			rrp = new RequestRepository();
		}
		return rrp;
	}
	public TitleRequest createTitleRequest(String id, User requestor, User requestee, RequestStatus status, Project project, String title) {
		TitleRequest tr = new TitleRequest(id, requestor, requestee, status, project, title);
		requestList.add(tr);
		return tr;
	}
	public AllocRequest createAllocationRequest(String id, User requestor, User requestee, RequestStatus status, Project project) {
		AllocRequest ar = new AllocRequest(id, requestor, requestee, status, project);
		requestList.add(ar);
		return ar;
	}
	public DeregRequest createDeregistrationRequest(String id, User requestor, User requestee, RequestStatus status, Project project) {
		DeregRequest dr = new DeregRequest(id, requestor, requestee, status, project);
		requestList.add(dr);
		return dr;
	}
	public TransferRequest createTransferRequest(String id, User requestor, User requestee, RequestStatus status, Project project, Faculty replacement) {
		TransferRequest trr = new TransferRequest(id, requestor, requestee, status, project, replacement);
		requestList.add(trr);
		return trr;
	}
	public void rejectRequest(Request r) {
		r.setStatus(RequestStatus.Rejected);
		return;
	}
	public void approveRequest(Request r) {
		r.setStatus(RequestStatus.Approved);
		return;
	}
	protected ArrayList<Request> getRequests(){
		return this.requestList;
	}
	public Project getProject(Request r) {
		return r.getProject();
	}
	public ArrayList<Request> getPendingRequests(){
		ArrayList<Request> pending = new ArrayList<Request>();
		requestList.forEach((request)->{
			if (request.getStatus().equals(RequestStatus.Pending)) {
				pending.add(request);
			}
		});
		return pending;
	}
	public Request getPendingRequestbyID(int requestID){
		ArrayList<Request> pending = getPendingRequests();
		for (Request r : pending) {
			if (r.getRequestID().equals(requestID)) {
				return r;
			}
		}
		return null;
	}
}
