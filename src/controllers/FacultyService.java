package controllers;

import java.util.ArrayList;
import java.util.HashMap;

import entities.Coordinator;
import entities.Faculty;
import entities.Project;
import entities.Request;


public class FacultyService {
	private static FacultyService fsc = null;
	private final FacultyAccountController facultyAccountController;
	private final FacultyRepository facultyRepository;
	private FacultyService() {
		this.facultyAccountController = FacultyAccountController.getInstance();
		this.facultyRepository = FacultyRepository.getInstance();
	}
	public static FacultyService getInstance() {
		if (fsc == null) {
			fsc = new FacultyService();
		}
		return fsc;
	}
	public void createFaculty(String userID, String password, String name, String email, String role) {
		facultyRepository.createFaculty(userID, password, name, email, role);
	}
	public void updateFaculty(String userID, String newPassword) {
		facultyRepository.updateFaculty(userID, newPassword);
		facultyAccountController.updateFAccount(getFacultybyID(userID), newPassword);
	}
	public Faculty getFacultybyID(String userID) {
		return facultyRepository.getFacultybyID(userID);
	}
	public ArrayList<Request> getFacultyRequests(Faculty user) {
		return facultyRepository.getFacultyRequests(user);
	}
	public Faculty getFacultybyName(String name) {
		return facultyRepository.getFacultybyName(name);
	}
	public String getFacultyID(Faculty f) {
		return facultyRepository.getFacultyID(f);
	}
	public void addFacultyProject(Faculty f, Project p) {
		facultyRepository.addFacultyProject(f,p);
	}
	public ArrayList<Project> getFacultyProjects(String userID){
		return facultyRepository.getFacultyProjects(userID);
	}
	public int getActiveProjects(String userID) {
		return facultyRepository.getActiveProjects(userID);
	}
	public void removeProject(String userID, Project p) {
		facultyRepository.removeProject(userID,p);
	}
	public void addInbox(Faculty f, Request r) {
		facultyRepository.addInbox(f, r);
	}
	public void addHistory(Faculty f, Request r) {
		facultyRepository.addHistory(f, r);
	}
	public String getFacultyName(Faculty f) {
		return facultyRepository.getFacultyName(f);
	}
	public Coordinator getCoordinator() {
		return facultyRepository.getCoordinator();
	}
	public ArrayList<Request> getFacultyInbox(Faculty f){
		return facultyRepository.getFacultyInbox(f);
	}
}

