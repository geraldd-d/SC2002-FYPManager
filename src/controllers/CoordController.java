package controllers;

import java.util.ArrayList;
import java.util.List;

import entities.Coordinator;
import entities.Faculty;
import entities.Project;
import entities.Request;
import entities.RequestType;
import entities.TransferRequest;

public class CoordController {
	private static CoordController cc = null;
	private final FacultyService facultyService;
	private final CoordProjectService coordProjectService;
	private final CoordRequestService coordRequestService;
	private CoordController() {
		ServiceController svc = ServiceController.getInstance();
		this.facultyService = svc.getFacultyService();
		this.coordProjectService = CoordProjectService.getInstance();
		this.coordRequestService = CoordRequestService.getInstance();
	};
	public static CoordController getInstance() {
		if (cc == null) {
			cc = new CoordController();
		}
		return cc;
	}
	public void viewAllAvailableProjects(Coordinator c) {
		ArrayList<Project>projects = coordProjectService.getAvailableProjects(c);
		if (projects.size() == 0) {
			System.out.println("No available projects.");
			return;
		}
		coordProjectService.viewAvailableProjects(c);
	}
	public void viewAllUnavailableProjects(Coordinator c) {
		ArrayList<Project>projects = coordProjectService.getUnavailableProjects(c);
		if (projects.size() == 0) {
			System.out.println("No unavailable projects.");
			return;
		}
		coordProjectService.viewUnavailableProjects(c);
	}
	public void viewAllReservedProjects(Coordinator c) {
		ArrayList<Project>projects = coordProjectService.getReservedProjects(c);
		if (projects.size() == 0) {
			System.out.println("No reserved projects.");
			return;
		}
		coordProjectService.viewReservedProjects(c);
	}
	public void viewAllAllocatedProjects(Coordinator c) {
		ArrayList<Project>projects = coordProjectService.getAllocatedProjects(c);
		if (projects.size() == 0) {
			System.out.println("No allocated projects.");
			return;
		}
		coordProjectService.viewAllocatedProjects(c);
	}
	public void viewOwnProjects(Coordinator c) {
		if (c.getProjects().size()>0) {
			coordProjectService.viewOwnProjects(c);
		} else {
			System.out.println("You currently have no projects.");
		}
	}
	public ArrayList<Request> getrequests() {
		ArrayList<Request> requests = coordRequestService.getRequests();
		return requests;
	}
	public void approveRequest(Request r) {
		RequestType rt = r.getType();
		switch (rt) {
		case Allocation:
			coordRequestService.approveAllocation(r);
			break;
		case Deregister:
			coordRequestService.approveDeregistration(r);
			break;
		case Title:
			coordRequestService.approveTitleChange(r);
			break;
		case Transfer:
			coordRequestService.approveTransfer(r);
			break;
		}
	}
	public void rejectRequest(Request r) {
		coordRequestService.rejectRequest(r);
	}
	public void viewPending(Coordinator coordinator) {
		ArrayList<Request> pending = coordRequestService.getPendingReqs(coordinator);
		if (pending.size() == 0) {
			System.out.println("You have no pending requests.");
		}
		else {
			coordRequestService.viewPending(coordinator);
		}
	}
	public void requestTransfer(Coordinator coordinator, Project p, String replacement) {
		Faculty f = facultyService.getFacultybyID(replacement);
		if (f.getActiveProjects() < 2) {
			TransferRequest tr = coordRequestService.addTransferRequest(coordinator, coordinator, p, f);
			coordRequestService.approveTransfer(tr);
		} else {
			System.out.println("Replacement Supervisor is already at maximum project capacity");
		}
	}
	public void viewInbox(Coordinator coordinator, int page) {
		coordRequestService.viewInbox(coordinator, page);
	}
	public void viewAllRequests(Coordinator coordinator, int page) {
		coordRequestService.viewAllRequests(page);
	}
	public Request getPendingRequestbyID(int id) {
		return coordRequestService.getPendingRequestbyID(id);
	}
}
