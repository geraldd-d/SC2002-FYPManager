package controllers;

import java.util.ArrayList;
import java.util.List;

import entities.Coordinator;
import entities.Faculty;
import entities.Project;
import entities.Request;
import entities.RequestStatus;
import entities.Student;
import entities.TransferRequest;
import entities.User;

public class CoordRequestService implements ICoordRequestService{
	private final RequestDataController requestDataController;
	private final RequestRepository requestRepository;
	private final StudentService studentService;
	private final FacultyService facultyService;
	private final CoordProjectService coordProjectService;
	private static CoordRequestService crsc = null;
	private CoordRequestService(){
		ServiceController svc = ServiceController.getInstance();
		this.facultyService = svc.getFacultyService();
		this.studentService = svc.getStudentService();
		this.requestDataController = svc.getRequestDataController();
		this.requestRepository = svc.getRequestRepository();
		this.coordProjectService = CoordProjectService.getInstance();
	};
	public static CoordRequestService getInstance() {
		if (crsc == null) {
			crsc = new CoordRequestService();
		}
		return crsc;
	}
	public ArrayList<Request> getInbox(Coordinator c){
		return c.getInbox();
	}

	public ArrayList<Request> getPendingReqs(Faculty user){
		ArrayList<Request> reqs = new ArrayList<Request>();
		ArrayList<Request> inbox = user.getInbox();
		inbox.forEach((request)->{
			if (request.getStatus().equals(RequestStatus.Pending)) {
				reqs.add(request);
			}
		});
		return reqs;
	}
	public void saveChanges() {
		ArrayList<Request> requests = requestRepository.getRequests();
		requestDataController.updateRequests(requests);
	}

	@Override
	public void approveTitleChange(Request r) {
		requestRepository.approveRequest(r);
		Project p = requestRepository.getProject(r);
		String title = r.getChanges();
		coordProjectService.editProjectTitle(p, title);
	}

	@Override
	public TransferRequest addTransferRequest(Faculty requestor, Coordinator requestee, Project p, Faculty replacement) {
		Integer id = requestDataController.getNewID();
		TransferRequest tr = requestRepository.createTransferRequest(String.valueOf(id), requestor, requestee, RequestStatus.Pending, p, replacement);
		facultyService.addHistory(requestor, tr);
		facultyService.addInbox(requestee, tr);
		return tr;
	}

	@Override
	public void rejectRequest(Request r) {
		requestRepository.rejectRequest(r);
	}

	@Override
	public void approveAllocation(Request r) {
		Project p = requestRepository.getProject(r);
		String id = p.getSupervisorID();
		Faculty supervisor = facultyService.getFacultybyID(id);
		if (supervisor.getActiveProjects() >= 2) {
			System.out.println("Supervisor already has the maximum number of projects.");
			return;
		}
		requestRepository.approveRequest(r);
		Student requestor = (Student) r.getRequestor();
		String studentID = studentService.getStudentID(requestor);
		coordProjectService.allocateProject(studentID, p);
	}

	@Override
	public void approveDeregistration(Request r) {
		requestRepository.approveRequest(r);
		Project p = requestRepository.getProject(r);
		Student requestor = (Student) r.getRequestor();
		String id = studentService.getStudentID(requestor);
		coordProjectService.deregisterProject(id, p);
	}

	@Override
	public void approveTransfer(Request r) {
		Project p = requestRepository.getProject(r);
		Faculty requestor = (Faculty) r.getRequestor();
		String requestorID = facultyService.getFacultyID(requestor);
		String replacementID = r.getChanges();
		Faculty replacement = facultyService.getFacultybyID(replacementID);
		String replacementName = facultyService.getFacultyName(replacement);
		if (replacement.getActiveProjects() >= 2) {
			System.out.println("Supervisor already has the maximum number of projects.");
			return;
		}
		requestRepository.approveRequest(r);
		coordProjectService.transferProject(requestorID, replacementID, replacementName, p);
	}
	public void viewAllRequests(int page) {
		ArrayList<Request> requests = requestRepository.getRequests();
		int pageSize = 5;
		int startIndex = (page - 1) * pageSize;
		int endIndex = Math.min(startIndex + pageSize, requests.size());
		List<Request> currentPage = requests.subList(startIndex, endIndex);
		currentPage.forEach((request)->request.printRequest());
	}
	public void viewPending(Coordinator c) {
		ArrayList<Request> requests = getPendingReqs(c);
		requests.forEach((request)->request.printRequest());
	}
	public Request getPendingRequestbyID(int requestID) {
		Request pending = requestRepository.getPendingRequestbyID(requestID);
		return pending;
	}
	public ArrayList<Request> getRequests(){
		return requestRepository.getRequests();
	}
	public void viewInbox(Coordinator coordinator, int page) {
		ArrayList<Request> requests = getInbox(coordinator);
		int pageSize = 5;
		int startIndex = (page - 1) * pageSize;
		int endIndex = Math.min(startIndex + pageSize, requests.size());
		List<Request> currentPage = requests.subList(startIndex, endIndex);
		currentPage.forEach((request)->request.printRequest());
	}
}
