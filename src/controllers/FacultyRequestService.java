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

public class FacultyRequestService implements IFacultyRequestService{
	private final RequestDataController requestDataController;
	private final RequestRepository requestRepository;
	private final StudentService studentService;
	private final FacultyService facultyService;
	private final FacultyProjectService facultyProjectService;
	private static FacultyRequestService frsc = null;
	private FacultyRequestService(){
		ServiceController svc = ServiceController.getInstance();
		this.facultyService = svc.getFacultyService();
		this.studentService = svc.getStudentService();
		this.requestDataController = svc.getRequestDataController();
		this.requestRepository = svc.getRequestRepository();
		this.facultyProjectService = FacultyProjectService.getInstance();
	};
	public static FacultyRequestService getInstance() {
		if (frsc == null) {
			frsc = new FacultyRequestService();
		}
		return frsc;
	}
	public void viewHistory(Faculty user, int page) {
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
	public void viewPending(Faculty user, int page) {
	    int pageSize = 5;
	    ArrayList<Request> reqs = getPendingReqs(user);
	    int startIndex = (page - 1) * pageSize;
	    int endIndex = Math.min(startIndex + pageSize, reqs.size());
	    List<Request> currentPage = reqs.subList(startIndex, endIndex);
	    currentPage.forEach((Request)-> Request.printRequest());
	}
	public boolean checkPending(Faculty f, Request r) {
		ArrayList<Request> requests = f.getHistory();
		for (Request req : requests) {
			if (req.getType().equals(r.getType()) && req.getStatus().equals(RequestStatus.Pending) && r.getProject().equals(req.getProject())) {
				return true;
			}
		}
		return false;
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
		facultyProjectService.editProjectTitle(p, title);
	}

	@Override
	public TransferRequest addTransferRequest(Faculty requestor, Coordinator requestee, Project p, Faculty replacement) {
		Integer id = requestDataController.getNewID();
		TransferRequest tr = requestRepository.createTransferRequest(String.valueOf(id), requestor, requestee, RequestStatus.Pending, p, replacement);
		if (checkPending(requestor,tr)) {
			System.out.println("You already have a pending transfer request for the same project.");
			return null;
		}
		facultyService.addHistory(requestor, tr);
		facultyService.addInbox(requestee, tr);
		return tr;
	}

	@Override
	public void rejectRequest(Request r) {
		requestRepository.rejectRequest(r);
	}

}
