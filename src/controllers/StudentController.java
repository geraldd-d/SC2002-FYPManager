package controllers;

import java.util.ArrayList;

import entities.Project;
import entities.ProjectStatus;
import entities.Request;
import entities.RequestStatus;
import entities.Student;

public class StudentController {
	private static StudentController sc = null;
	private final StudentService studentService;
	private final StudentProjectService studentProjectService;
	private final StudentRequestService studentRequestService;
	private StudentController() {
		this.studentService = StudentService.getInstance();
		this.studentProjectService = StudentProjectService.getInstance();
		this.studentRequestService = StudentRequestService.getInstance();
	};
	public static StudentController getInstance() {
		if (sc == null) {
			sc = new StudentController();
		}
		return sc;
	}
	//View all the available projects 
	public void viewAllAvailableProjects(Student s,int page) {
		if (!studentService.isAllocated(s)) {
			studentProjectService.viewAllAvailableProjects(page);
		}
	}
	
	// Requesting the supervisor to allocate the project 
	public boolean requestAlloc(Student user, int id) {
		Project p = studentProjectService.getProjectbyID(id);
		if (p != null && p.getStatus().equals(ProjectStatus.Available)) {
			studentRequestService.addAllocationRequest(user, p);
			return true;
		}
		return false;
	}
	
	// Reuqesting to change the title of the Project
	public boolean requestNewTitle(Student user, String title){
		String id = studentService.getStudentID(user);
		Project p = studentService.getStudentProject(id);
		if(p!= null && p.getStatus().equals(ProjectStatus.Allocated)){
			studentRequestService.addTitleRequest(user, p, title);
			return true;
		}
		return false;
	}
	
	// temporary
	public boolean viewRegisteredProject(Student user){
		String id = studentService.getStudentID(user);
		Project p = studentService.getStudentProject(id);
		if(p != null){
			p.printProject();
			return true;
		}
		return false;
	}

	// Deregister the registered project 
	public boolean requestDeregister(Student user){
		String id = studentService.getStudentID(user);
		Project p = studentService.getStudentProject(id);
		if(p!= null && p.getStatus().equals(ProjectStatus.Allocated)){
			studentRequestService.addDeregistrationRequest(user, p);
			return true;
		}
		return false;
	}
}
