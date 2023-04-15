package controllers;

import java.util.ArrayList;
import java.util.HashMap;

import entities.Project;
import entities.Request;
import entities.Student;
import entities.User;

public class StudentService {
	private static StudentService ssc = null;
	private final StudentAccountController studentAccountController;
	private final StudentRepository studentRepository;
	private StudentService() {
		this.studentAccountController = StudentAccountController.getInstance();
		this.studentRepository = StudentRepository.getInstance();
	}
	public static StudentService getInstance() {
		if (ssc == null) {
			ssc = new StudentService();
		}
		return ssc;
	}
	public void createStudent(String userID, String password, String name, String email) {
		studentRepository.createStudent(userID, password, name, email);
	}
	public void updateStudent(String userID, String newPassword) {
		studentRepository.updateStudent(userID, newPassword);
		studentAccountController.updateSAccount(getStudentbyID(userID), newPassword);
	}
	public Student getStudentbyID(String userID) {
		return studentRepository.getStudentbyID(userID);
	}
	public ArrayList<Request> getStudentRequests(String userID) {
		return studentRepository.getStudentRequests(userID);
	}
	public Project getStudentProject(String userID) {
		return studentRepository.getStudentProject(userID);
	}
	public void setStudentProject(String userID, Project p) {
		studentRepository.setStudentProject(userID, p);
	}
	public HashMap<String, User> getStudentList() {
		return studentRepository.getStudentList();
	}
}

