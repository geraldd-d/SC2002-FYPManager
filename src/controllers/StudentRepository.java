package controllers;

import java.util.ArrayList;
import java.util.HashMap;

import entities.Project;
import entities.Request;
import entities.Student;
import entities.User;

public class StudentRepository {
	private static StudentRepository srp = null;
	private HashMap<String, User> studentList;
	private StudentRepository() {
		this.studentList = new HashMap<String,User>();
	}
	public static StudentRepository getInstance() {
		if(srp == null) {
			srp = new StudentRepository();
		}
		return srp;
	}
	public void createStudent(String userID, String password, String name, String email) {
		Student s = new Student(userID, password, name, email);
		studentList.put(userID, s);
	}
	public void updateStudent(String userID, String newPassword) {
		Student s = getStudentbyID(userID);
		s.updatePassword(newPassword);
	}
	public Student getStudentbyID(String s) {
		return (Student )studentList.get(s);
	}
	public ArrayList<Request> getStudentRequests(String userID) {
		Student s = getStudentbyID(userID);
		return s.getHistory();
	}
	public Project getStudentProject(String userID) {
		Student s = getStudentbyID(userID);
		return s.getRegisteredProject();
	}
	public void setStudentProject(String userID, Project p) {
		Student s = getStudentbyID(userID);
		s.setRegisteredProject(p);
	}
	protected HashMap<String, User> getStudentList() {
		return this.studentList;
	}
	public void addHistory(Student s, Request r) {
		s.addHistory(r);
	}
	public String getStudentID(Student s) {
		return s.getUserID();
	}
	public boolean isAllocated(Student s) {
		return s.getisAllocated();
	}
}