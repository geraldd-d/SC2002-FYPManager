package controllers;

import java.util.ArrayList;
import java.util.HashMap;

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
	public void updateFaculty(String userID, String newPassword) {
		facultyRepository.updateFaculty(userID, newPassword);
		facultyAccountController.updateFAccount(getFacultybyID(userID), newPassword);
	}
	public Faculty getFacultybyID(String userID) {
		return facultyRepository.getFacultybyID(userID);
	}
	public ArrayList<Request> getFacultyRequests(String userID) {
		return facultyRepository.getFacultyRequests(userID);
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
}

