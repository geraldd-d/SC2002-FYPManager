package controllers;

import java.util.ArrayList;
import java.util.HashMap;

import entities.Coordinator;
import entities.Faculty;
import entities.Project;
import entities.Request;
import entities.Student;
import entities.User;

public class FacultyRepository {
	private static FacultyRepository frp = null;
	private HashMap<String, User> facultyList;
	private HashMap<String, User> facultyNames;
	private Coordinator coordinator;
	private FacultyRepository() {
		this.facultyList = new HashMap<String,User>();
		this.facultyNames = new HashMap<String,User>();
	}
	public static FacultyRepository getInstance() {
		if(frp == null) {
			frp = new FacultyRepository();
		}
		return frp;
	}
	public Faculty createFaculty(String userID, String password, String name, String email, String role) {
		if (role.equals("Coordinator")) {
			Coordinator c = new Coordinator(userID, password, name, email);
			facultyList.put(userID, c);
			return c;
		}
		Faculty f = new Faculty(userID, password, name, email);
		facultyList.put(userID, f);
		return f;
	}
	public void updateFaculty(String userID, String newPassword) {
		Faculty f = getFacultybyID(userID);
		f.updatePassword(newPassword);
	}
	public Faculty getFacultybyID(String userID) {
		return (Faculty)facultyList.get(userID);
	}
	public ArrayList<Request> getFacultyRequests(String userID) {
		Faculty f = getFacultybyID(userID);
		return f.getHistory();
	}
	protected HashMap<String, User> getFacultyList() {
		return this.facultyList;
	}
	public Faculty getFacultybyName(String name) {
		return (Faculty) facultyNames.get(name);
	}
	public void getFacultyNames(){
		facultyList.forEach((key, value)-> {
			if (value instanceof Coordinator) {
				this.coordinator = (Coordinator) value;
			}
			Faculty f = (Faculty) value;
			facultyNames.put(f.getName(),f);
		});
	}
	public String getFacultyID(Faculty f) {
		return f.getUserID();
	}
	public void addFacultyProject(Faculty f, Project p) {
		f.addProject(p);
	}
	public ArrayList<Project> getFacultyProjects(String userID){
		Faculty f = getFacultybyID(userID);
		return f.getProjects();
	}
	public int getActiveProjects(String userID) {
		Faculty f = getFacultybyID(userID);
		return f.getActiveProjects();
	}
	public void removeProject(String userID, Project p) {
		ArrayList<Project> projects = getFacultyProjects(userID);
		projects.remove(p);
	}
}