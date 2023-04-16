package controllers;

import java.util.ArrayList;

import entities.Coordinator;
import entities.Faculty;
import entities.Project;
import entities.ProjectStatus;
import entities.Request;
import entities.Student;

public class FacultyController {
	private static FacultyController fc = null;
	private final FacultyService facultyService;
	private final FacultyProjectService facultyProjectService;
	private final FacultyRequestService facultyRequestService;
	private FacultyController() {
		this.facultyService = FacultyService.getInstance();
		this.facultyProjectService = FacultyProjectService.getInstance();
		this.facultyRequestService = FacultyRequestService.getInstance();
	};
	public static FacultyController getInstance() {
		if (fc == null) {
			fc = new FacultyController();
		}
		return fc;
	}
	
	public void viewOwnProjects(Faculty user) {
		if (user.getProjects().size()>0) {
			facultyProjectService.viewOwnProjects(user);
		} else {
			System.out.println("You currently have no projects.");
		}
	}
	public void createProject(String title,Faculty supervisor){
		String id = supervisor.getUserID();
		int numActive = facultyService.getActiveProjects(id);
		ProjectStatus status;
		if (numActive >= 2) {
			status = ProjectStatus.Unavailable;
		} else {
			status = ProjectStatus.Available;
		}
		String studentid = "";
		facultyProjectService.addProject(title, id, supervisor.getName(), studentid, status);
	}
	public void changeTitle(Project p,String s) {
		facultyProjectService.editProjectTitle(p, s);
	}
	public void requestTransfer(Faculty user, Project p, String replacementID) {
		Faculty f = facultyService.getFacultybyID(replacementID);
		Coordinator c = facultyService.getCoordinator();
		facultyRequestService.addTransferRequest(user, c, p, f);
	}

	public void viewPendingReqs(Faculty f, int page) {
		facultyRequestService.viewPending(f, page);
	}
	public ArrayList<Request> getPendingReqs(Faculty user){
		return facultyRequestService.getPendingReqs(user);
	}
	public void saveChanges() {
		facultyRequestService.saveChanges();
		facultyProjectService.saveChanges();
	}
}
