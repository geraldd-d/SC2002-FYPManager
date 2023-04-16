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
import entities.TransferRequest;
import entities.User;

public class RequestService {
	private final RequestRepository requestRepository;
	private final StudentService studentService;
	private final FacultyService facultyService;
	private static RequestService rsc = null;
	private RequestService() {
		ServiceController svc = ServiceController.getInstance();
		this.facultyService = svc.getFacultyService();
		this.studentService = svc.getStudentService();
		this.requestRepository = svc.getRequestRepository();
	}
	public static RequestService getInstance() {
		if (rsc == null) {
			rsc = new RequestService();
		}
		return rsc;
	}
	
	public void createTitleRequest(String id, User requestor, User requestee, RequestStatus status, Project project, String title) {
		TitleRequest tr = requestRepository.createTitleRequest(id, requestor, requestee, status, project, title);
		facultyService.addInbox((Faculty)requestee, tr);
		studentService.addHistory((Student)requestor, tr);
	}
	public void createAllocationRequest(String id, User requestor, User requestee, RequestStatus status, Project project) {
		AllocRequest ar = requestRepository.createAllocationRequest(id, requestor, requestee, status, project);
		facultyService.addInbox((Faculty)requestee, ar);
		studentService.addHistory((Student)requestor, ar);
	}
	public void createDeregistrationRequest(String id, User requestor, User requestee, RequestStatus status, Project project) {
		DeregRequest dr = requestRepository.createDeregistrationRequest(id, requestor, requestee, status, project);
		facultyService.addInbox((Faculty)requestee, dr);
		studentService.addHistory((Student)requestor, dr);
	}
	public void createTransferRequest(String id, User requestor, User requestee, RequestStatus status, Project project, Faculty replacement) {
		TransferRequest trr = requestRepository.createTransferRequest(id, requestor, requestee, status, project, replacement);
		facultyService.addInbox((Faculty)requestee, trr);
		studentService.addHistory((Student)requestor, trr);
	}
	public void saveChanges() {
		RequestDataController requestDataController = RequestDataController.getInstance();
		ArrayList<Request> requests = requestRepository.getRequests();
		requestDataController.updateRequests(requests);
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
