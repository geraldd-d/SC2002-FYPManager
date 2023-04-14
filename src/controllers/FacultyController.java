package controllers;

import java.util.ArrayList;
import java.util.HashMap;

import entities.*;


public class FacultyController {
	private static FacultyController fc = null;
	private static HashMap<String,User> facultyData;
	private HashMap<String,Faculty> facultyNames;
	private Coordinator coordinator;
	private FacultyController(HashMap<String,User> facultyData) {
		FacultyController.facultyData = facultyData;
		this.facultyNames = getFacultyNames();
	};
	public static FacultyController getInstance(HashMap<String,User> facultyList) {
		if (fc == null|| !facultyList.equals(facultyData)) {
			fc = new FacultyController(facultyList);
		}
		return fc;
	}
	public static FacultyController getInstance() {
		return fc;
	}
	public Faculty getFacultybyID(String id) {
		return (Faculty) facultyData.get(id);
	}
	public Faculty getFacultybyName(String name) {
		return (Faculty) facultyNames.get(name);
	}
	public HashMap<String,Faculty> getFacultyNames(){
		HashMap<String,Faculty> faculties = new HashMap<String,Faculty>();
		facultyData.forEach((key, value)-> {
			if (value instanceof Coordinator) {
				this.coordinator = (Coordinator) value;
			}
			Faculty f = (Faculty) value;
			faculties.put(f.getName(),f);
		});
		return faculties;
	}
	public Coordinator getCoord() {
		return this.coordinator;
	}
	public void viewOwnProjects(Faculty user) {
		ProjectManager pm = ProjectManager.getInstance();
		if (user.getProjects().size()>0) {
			pm.viewActiveProjects(user);
		} else {
			System.out.println("You currently have no projects.");
		}
	}
	
	public void changeTitle(Project p,String s) {
		ProjectManager pm = ProjectManager.getInstance();
		pm.changeTitle(p, s);
	}
	public void transferRequest(Faculty user, Project p, String replacementID) {
		RequestManager rm = RequestManager.getInstance();
		rm.addTransferRequest(user, p, replacementID);
	}
	public ArrayList<Request> getPendingRequests(Faculty user) {
		RequestManager rm = RequestManager.getInstance();
		return rm.getPendingReqs(user);
	}
}

	