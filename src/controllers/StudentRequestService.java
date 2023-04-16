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

public class StudentRequestService implements IStudentRequestService {
	private final RequestDataController requestDataController;
	private final RequestRepository requestRepository;
	private final StudentService studentService;
	private final FacultyService facultyService;
	private final StudentProjectService studentProjectService;
	private static StudentRequestService srsc = null;
	private StudentRequestService(){
		this.facultyService = FacultyService.getInstance();
		this.studentService = StudentService.getInstance();
		this.requestDataController = RequestDataController.getInstance();
		this.requestRepository = RequestRepository.getInstance();
		this.studentProjectService = StudentProjectService.getInstance();
	};
	public static StudentRequestService getInstance() {
		if (srsc == null) {
			srsc = new StudentRequestService();
		}
		return srsc;
	}
	@Override
	public void saveChanges() {
		ArrayList<Request> requests = requestRepository.getRequests();
		requestDataController.updateRequests(requests);
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
	public boolean checkPending(Student s, Request r) {
		ArrayList<Request> requests = s.getHistory();
		for (Request req : requests) {
			if (req.getType().equals(r.getType()) && req.getStatus().equals(RequestStatus.Pending)) {
				return true;
			}
		}
		return false;
	}
	@Override
	public void addAllocationRequest(Student s, Project p) {
		Integer id = requestDataController.getNewID();
		Coordinator c = facultyService.getCoordinator();
		AllocRequest ar = requestRepository.createAllocationRequest(String.valueOf(id), s, c, RequestStatus.Pending, p);
		if (checkPending(s,ar)) {
			System.out.println("You already have a pending allocation request.");
			return;
		}
		studentService.addHistory(s, ar);
		facultyService.addInbox(c, ar);
	}

	@Override
	public void addDeregistrationRequest(Student s, Project p) {
		Integer id = requestDataController.getNewID();
		Coordinator c = facultyService.getCoordinator();
		DeregRequest dr = requestRepository.createDeregistrationRequest(String.valueOf(id), s, c, RequestStatus.Pending, p);
		if (checkPending(s,dr)) {
			System.out.println("You already have a pending deregistration request.");
			return;
		}
		studentService.addHistory(s, dr);
		facultyService.addInbox(c, dr);
	}

	@Override
	public void addTitleRequest(Student s, Project p, String title) {
		Integer id = requestDataController.getNewID();
		String facID = p.getSupervisorID();
		Faculty f = facultyService.getFacultybyID(facID);
		TitleRequest tr = requestRepository.createTitleRequest(String.valueOf(id), s, f, RequestStatus.Pending, p, title);
		if (checkPending(s,tr)) {
			System.out.println("You already have a pending title change request.");
			return;
		}
		studentService.addHistory(s, tr);
		facultyService.addInbox(f, tr);
	}

}
