package controllers;

import java.util.ArrayList;

import boundaries.CoordinatorMenu;
import entities.Coordinator;
import entities.Faculty;
import entities.Project;
import entities.Request;
import entities.RequestType;
	/*
	 * This class is the controller for the Faculty class.
	 */
public class CoordinatorController {
	private static CoordinatorController cc = null;
	private CoordinatorController() {};
	
	/** 
	 * @return CoordinatorController
	 */
	public static CoordinatorController getInstance() {
		if (cc == null) {
			cc = new CoordinatorController();
		}
		return cc;
	}
	
	/** 
	 * view pending requests to coordinator
	 * @param coordinator
	 */
	public void viewPending(Coordinator coordinator) {
		CoordRequestManager crm = CoordRequestManager.getInstance();
		CoordinatorMenu cm = CoordinatorMenu.getInstance();
		ArrayList<Request> pending = crm.getPendingReqs(coordinator);
		if (pending.size() == 0) {
			System.out.println("You have no pending requests.");
		}
		else {
			crm.viewPending(coordinator);
		}
	}
	
	/** 
	 * view coordinator inbox
	 * @param coordinator
	 * @param page
	 */
	public void viewInbox(Coordinator coordinator, int page) {
		CoordRequestManager crm = CoordRequestManager.getInstance();
		crm.viewInbox(coordinator, page);
	}
	
	/** 
	 * view all requests
	 * @param coordinator
	 * @param page
	 */
	public void viewAllRequests(Coordinator coordinator, int page) {
		CoordRequestManager crm = CoordRequestManager.getInstance();
		crm.viewAllRequests(page);
	}
	
	/**
	 * view available projects
	 * @param c
	 */
	public void viewAllAvailableProjects(Coordinator c) {
		CoordProjectManager cpm = CoordProjectManager.getInstance();
		ArrayList<Project>projects = cpm.getAllAvailableProjects();
		if (projects.size() == 0) {
			System.out.println("No available projects.");
			return;
		}
		projects.forEach((p)-> p.printProject());
	}
	
	/** 
	 * view unavailable projects
	 * @param c
	 */
	public void viewAllUnavailableProjects(Coordinator c) {
		CoordProjectManager cpm = CoordProjectManager.getInstance();
		ArrayList<Project>projects = cpm.getAllUnavailableProjects();
		if (projects.size() == 0) {
			System.out.println("No unavailable projects.");
			return;
		}
		projects.forEach((p)-> p.printProject());
	}
	
	/** 
	 * view reserved projects
	 * @param c
	 */
	public void viewAllReservedProjects(Coordinator c) {
		CoordProjectManager cpm = CoordProjectManager.getInstance();
		ArrayList<Project>projects = cpm.getAllReservedProjects();
		if (projects.size() == 0) {
			System.out.println("No reserved projects.");
			return;
		}
		projects.forEach((p)-> p.printProject());
	}
	
	/** 
	 * view allocated projects
	 * @param c
	 */
	public void viewAllAllocatedProjects(Coordinator c) {
		CoordProjectManager cpm = CoordProjectManager.getInstance();
		ArrayList<Project>projects = cpm.getAllAllocatedProjects();
		if (projects.size() == 0) {
			System.out.println("No allocated projects.");
			return;
		}
		projects.forEach((p)-> p.printProject());
	}
	
	/** 
	 * view coordinator projects
	 * @param c
	 */
	public void viewOwnProjects(Coordinator c) {
		CoordProjectManager cpm = CoordProjectManager.getInstance();
		if (c.getProjects().size() == 0) {
			System.out.println("You do not have any projects.");
		}
		else {
			cpm.viewOwnProjects(c);
		}
	}
	
	/** 
	 * approve request by coordinator
	 * @param c
	 * @param r
	 */
	public void approveRequest(Coordinator c, Request r) {
		CoordRequestManager crm = CoordRequestManager.getInstance();
		RequestType rt = r.getType();
		switch (rt) {
		case Allocation:
			crm.approveAllocation(r);
			break;
		case Deregister:
			crm.approveDeregistration(r);
			break;
		case Title:
			crm.approveTitleChange(r);
			break;
		case Transfer:
			crm.approveTransfer(r);
			break;
		}
	}
	
	/** 
	 * reject request
	 * @param coordinator
	 * @param r
	 */
	public void rejectRequest(Coordinator coordinator, Request r) {
		CoordRequestManager crm = CoordRequestManager.getInstance();
		crm.rejectRequest(r);
	}
	
	/** 
	 * request transfer of project p from coordinator to replacement
	 * @param coordinator
	 * @param p
	 * @param replacementID
	 */
	public void requestTransfer(Coordinator coordinator, Project p, String replacementID) {
		FacultyController fc = FacultyController.getInstance();
		CoordProjectManager cpm = CoordProjectManager.getInstance();
		Faculty f = fc.getFacultybyID(replacementID);
		if (f.getActiveProjects() < 2) {
			cpm.transferProject(coordinator, f.getUserID(), f.getName(), p);
		} else {
			System.out.println("Replacement Supervisor is already at maximum project capacity");
		}
	}
	
	/** 
	 * get pending request by its id
	 * @param c
	 * @param id
	 * @return Request
	 */
	public Request getPendingRequestbyID(Coordinator c, int id) {
		CoordRequestManager crm = CoordRequestManager.getInstance();
		return crm.getPendingRequestbyID(c,id);
	}

	/** 
	 * view coordinator history
	 * @param c
	 * @return Request
	 */
	public void viewHist(Coordinator coordinator) {
		ArrayList<Request> hist = coordinator.getHistory();
		if (hist.size() == 0) {
			System.out.println("You have not sent any requests.");
		} else {
			for (Request r : hist) {
				r.printRequest();
			}
		}
	}
}
