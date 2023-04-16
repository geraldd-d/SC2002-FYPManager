package controllers;

import java.util.ArrayList;

import entities.Coordinator;
import entities.Faculty;
import entities.Project;
import entities.Request;
import entities.RequestType;

public class CoordinatorController {
	private static CoordinatorController cc = null;
	private CoordinatorController() {};
	public static CoordinatorController getInstance() {
		if (cc == null) {
			cc = new CoordinatorController();
		}
		return cc;
	}
	public void viewPending(Coordinator coordinator) {
		CoordRequestManager crm = CoordRequestManager.getInstance();
		ArrayList<Request> pending = crm.getPendingReqs(coordinator);
		if (pending.size() == 0) {
			System.out.println("You have no pending requests.");
		}
		else {
			crm.viewPending(coordinator);
		}
	}
	public void viewInbox(Coordinator coordinator, int page) {
		CoordRequestManager crm = CoordRequestManager.getInstance();
		crm.viewInbox(coordinator, page);
	}
	public void viewAllRequests(Coordinator coordinator, int page) {
		CoordRequestManager crm = CoordRequestManager.getInstance();
		crm.viewAllRequests(page);
	}
	public void viewAllAvailableProjects(Coordinator c) {
		CoordProjectManager cpm = CoordProjectManager.getInstance();
		ArrayList<Project>projects = cpm.getAllAvailableProjects();
		if (projects.size() == 0) {
			System.out.println("No available projects.");
			return;
		}
		projects.forEach((p)-> p.printProject());
	}
	public void viewAllUnavailableProjects(Coordinator c) {
		CoordProjectManager cpm = CoordProjectManager.getInstance();
		ArrayList<Project>projects = cpm.getAllUnavailableProjects();
		if (projects.size() == 0) {
			System.out.println("No unavailable projects.");
			return;
		}
		projects.forEach((p)-> p.printProject());
	}
	public void viewAllReservedProjects(Coordinator c) {
		CoordProjectManager cpm = CoordProjectManager.getInstance();
		ArrayList<Project>projects = cpm.getAllReservedProjects();
		if (projects.size() == 0) {
			System.out.println("No reserved projects.");
			return;
		}
		projects.forEach((p)-> p.printProject());
	}
	public void viewAllAllocatedProjects(Coordinator c) {
		CoordProjectManager cpm = CoordProjectManager.getInstance();
		ArrayList<Project>projects = cpm.getAllAllocatedProjects();
		if (projects.size() == 0) {
			System.out.println("No allocated projects.");
			return;
		}
		projects.forEach((p)-> p.printProject());
	}
	public void viewOwnProjects(Coordinator c) {
		CoordProjectManager cpm = CoordProjectManager.getInstance();
		cpm.viewOwnProjects(c);
	}
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
	public void rejectRequest(Coordinator coordinator, Request r) {
		CoordRequestManager crm = CoordRequestManager.getInstance();
		crm.rejectRequest(r);
	}
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
	public Request getPendingRequestbyID(Coordinator c, int id) {
		CoordRequestManager crm = CoordRequestManager.getInstance();
		return crm.getPendingRequestbyID(c,id);
	}
	public ArrayList<Request> getrequests() {
		CoordRequestManager crm = CoordRequestManager.getInstance();
		ArrayList<Request> requests = crm.getRequests();
		return requests;
	}
}
