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
	

	






}






    
    