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
import entities.RequestType;
import entities.Student;
import entities.TitleRequest;

/**
 * This class manages the requests made by students. It implements the IStudentRequestManager interface.
 */
public class StudentRequestManager implements IStudentRequestManager{
	private static StudentRequestManager srm = null;
	private ArrayList<Request> requests;
	private StudentRequestManager(ArrayList<Request> requests) {
		this.requests = requests;
	}
	
	/** 
	 * This method is used to get the instance of the StudentRequestManager class. It is a singleton class.
	 * @param requests The list of requests
	 * @return The instance of the StudentRequestManager class.
	 */
	public static StudentRequestManager getInstance(ArrayList<Request> requests) {
		if (srm == null) {
			srm = new StudentRequestManager(requests);
		}
		return srm;
	}
	
	/** 
	 * This method is used to get the instance of the StudentRequestManager class. It is a singleton class.
	 * @return The instance of the StudentRequestManager class.
	 */
	public static StudentRequestManager getInstance() {
		return srm;
	}

	/**
	 * The method used to save changes
	 */
	@Override
	public void saveChanges() {
		RequestController rc = RequestController.getInstance();
		rc.updateRequests(requests);
	}
	
	
	/** 
	 * This method allows the user to view history on a particular page. 
	 * @param user The user whose history is viewed.
	 * @param page The page which is viewed.
	 */
	@Override
	public void viewHistory(Student user, int page) {
		int pageSize = 5;
	    ArrayList<Request> reqs = user.getHistory();
	    int startIndex = (page - 1) * pageSize;
	    int endIndex = Math.min(startIndex + pageSize, reqs.size());
	    List<Request> currentPage = reqs.subList(startIndex, endIndex);
	    currentPage.forEach((Request)-> Request.printRequest());
	}

	
	/** 
	 * This method is used to add allocation request in student request history.
	 * @param s The student whose request history would be updated.
	 * @param p The project which has been requested for allocation.
	 */
	@Override
	public void addAllocationRequest(Student s, Project p) {
		RequestController rc = RequestController.getInstance();
		FacultyController fc = FacultyController.getInstance();
		Integer id = rc.getNewID();
		Coordinator c = fc.getCoord();
		AllocRequest ar = new AllocRequest(id, s, c, RequestStatus.Pending, p);
		requests.add(ar);
		s.addHistory(ar);
		c.addInbox(ar);
	}

	
	/** 
	 * This method is used to add deregistration request in student request history.
	 * @param s The student whose request history would be updated.
	 * @param p The project which has been requested for deregistration.
	 */
	@Override
	public void addDeregistrationRequest(Student s, Project p) {
		RequestController rc = RequestController.getInstance();
		FacultyController fc = FacultyController.getInstance();
		Coordinator c = fc.getCoord();
		Integer id = rc.getNewID();
		DeregRequest dr = new DeregRequest(id, s, c, RequestStatus.Pending, p);
		requests.add(dr);
		s.addHistory(dr);
		c.addInbox(dr);
	}

	
	/** 
	 * This method is used to add title change request in student request history.
	 * @param s The student whose request history would be updated.
	 * @param p The project which has been requested for change of title.
	 * @param title The new title of the registered project.
	 */
	@Override
	public void addTitleRequest(Student s, Project p, String title) {
		RequestController rc = RequestController.getInstance();
		FacultyController fc = FacultyController.getInstance();
		Faculty supervisor = fc.getFacultybyID(p.getSupervisorID());
		Integer id = rc.getNewID();
		TitleRequest tr = new TitleRequest(id, s, supervisor, RequestStatus.Pending, p, title);
		requests.add(tr);
		s.addHistory(tr);
		supervisor.addInbox(tr);
	}
	
	/** 
	 * This method is used to check pending requests.
	 * @param s The student who is checking for pending requests.
	 * @param r The RequestType of request.
	 * @return Yes or no, if there is pending request.
	 */
	public boolean checkPending(Student s, RequestType rt) {
		ArrayList<Request> requests = s.getHistory();
		for (Request req : requests) {
			if (req.getType().equals(rt) && req.getStatus().equals(RequestStatus.Pending)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method is used to check for deregistration. 
	 * @param s The student who is checking for the deregistration of the registered project.
	 * @return yes or no, if the project is deregistered or not.
	 */
	public boolean checkDeregister(Student s) {
		ArrayList<Request> requests = s.getHistory();
		for (Request req : requests) {
			if (req.getType().equals(RequestType.Deregister) && req.getStatus().equals(RequestStatus.Approved)) {
				return true;
			}
		}
		return false;
	}
	
	/** 
	 * This method is used to get pending request.
	 * @param s The student to call pending request
	 * @param rt The requestType of the request
	 * @return The request made by student and if it is pending or not.
	 */
	public Request getPending(Student s, RequestType rt) {
		ArrayList<Request> requests = s.getHistory();
		for (Request req : requests) {
			if (req.getType().equals(rt) && req.getStatus().equals(RequestStatus.Pending)) {
				return req;
			}
		}
		return null;
	}
	
	
	/** 
	 * This method is used to bring request by ID.
	 * @param id The ID used to bring request.
	 * @return The request which is called by ID.
	 */
	public Request getRequestByID(int id) {
		for (Request req : requests) {
				if (req.getRequestID() == id) {
					return req;
				}
		}
		return null;
	}
}
