package controllers;

import java.util.*;

import entities.*;

public class StudentController {
	private static StudentController sc = null;
	private HashMap<String,User> studentData;
	private StudentController(HashMap<String,User> studentData) {
		this.studentData = studentData;
	};
	public static StudentController getInstance(HashMap<String,User> studentData) {
		if (sc == null) {
			sc = new StudentController(studentData);
		}
		return sc;
	}
	public static StudentController getInstance() {
		return sc;
	}
	public Student getStudentbyID(String id) {
		return (Student) studentData.get(id);
	}
	public User authStudent(String input) {
		return this.studentData.containsKey(input) ? this.studentData.get(input) : null;
	}

	//View all the available projects 
	public void ViewAllAvailableProjects(int page) {
		ProjectsController pc = ProjectsController.getInstance();
		ArrayList<Project>projects = pc.getAllAvailableProjects();
		int pageSize = 5;
	    int startIndex = (page - 1) * pageSize;
	    int endIndex = Math.min(startIndex + pageSize, projects.size());
	    List<Project> currentPage = projects.subList(startIndex, endIndex);
	    currentPage.forEach((project)->project.printAvailableProject());
	}
	
	// Requesting the supervisor to allocate the project 
	public boolean requestAlloc(Student user, int id) {
		ProjectsController pc = ProjectsController.getInstance();
		RequestManager rm = RequestManager.getInstance();
		Project p = pc.getProjectByID(id);
		if (p != null && p.getStatus().equals("Available")) {
			rm.addAllocRequest(user, p);
			return true;
		}
		return false;
	}
	
	// Reuqesting to change the title of the Project
	public boolean requestNewTitle(Student user){
		ProjectsController pc = ProjectsController.getInstance();
		RequestManager rm = RequestManager.getInstance();
		Project p = pc.getRegisteredProject(user);
		if(p!= null && p.getStatus().equals("Registered")){
			rm.addTitleRequest(user, p.getTitle());
			return true;
		}
		return false;
	}
	
	// temporary
	public boolean viewRegisteredProject(Student user){
		ProjectsController pc = ProjectsController.getInstance();
		Project p = pc.getRegisteredProject(user);
		if(p != null){
			p.printProject();
			return true;
		}
		return false;
	}

	// Deregister the registered project 
	public boolean DeregisterProject(Student user, Project project){
		ProjectsController pc = ProjectsController.getInstance();
		RequestManager rm = RequestManager.getInstance();
		project = pc.getRegisteredProject(user);
		if(project != null){
			rm.addDeregRequest(user);
			return true;
		}
		return false;
	}





}






    
    