package controllers;

import java.util.*;

import entities.*;

public class StudentController {
	private static StudentController sc = null;
	private static HashMap<String,User> studentData;
	private StudentController(HashMap<String,User> studentData) {
		StudentController.studentData = studentData;
	};
	public static StudentController getInstance(HashMap<String,User> studentsList) {
		if (sc == null || !studentsList.equals(studentData)) {
			sc = new StudentController(studentsList);
		}
		return sc;
	}
	public static StudentController getInstance() {
		return sc;
	}
	public Student getStudentbyID(String id) {
		return (Student) studentData.get(id);
	}

	//View all the available projects 
	public void ViewAllAvailableProjects(int page) {
		ProjectManager pm = ProjectManager.getInstance();
		ArrayList<Project>projects = pm.getAllAvailableProjects();
		int pageSize = 5;
	    int startIndex = (page - 1) * pageSize;
	    int endIndex = Math.min(startIndex + pageSize, projects.size());
	    List<Project> currentPage = projects.subList(startIndex, endIndex);
	    currentPage.forEach((project)->project.printAvailableProject());
	}
	
	// Requesting the supervisor to allocate the project 
	public boolean requestAlloc(Student user, int id) {
		ProjectManager pm = ProjectManager.getInstance();
		RequestManager rm = RequestManager.getInstance();
		Project p = pm.getProjectByID(id);
		if (p != null && p.getStatus().equals("Available")) {
			rm.addAllocRequest(user, p);
			return true;
		}
		return false;
	}
	
	// Reuqesting to change the title of the Project
	public boolean requestNewTitle(Student user){
		ProjectManager pm = ProjectManager.getInstance();
		RequestManager rm = RequestManager.getInstance();
		Project p = user.getRegisteredProject();
		if(p!= null && p.getStatus().equals("Registered")){
			rm.addTitleRequest(user, p.getTitle());
			return true;
		}
		return false;
	}
	
	// temporary
	public boolean viewRegisteredProject(Student user){
		Project p = user.getRegisteredProject();
		if(p != null){
			p.printProject();
			return true;
		}
		return false;
	}

	// Deregister the registered project 
	public boolean DeregisterProject(Student user){
		ProjectManager pm = ProjectManager.getInstance();
		RequestManager rm = RequestManager.getInstance();
		Project project = user.getRegisteredProject();
		if(project != null){
			rm.addDeregRequest(user);
			return true;
		}
		return false;
	}
}






    
    