package controllers;

import java.util.HashMap;

import entities.Student;
import entities.User;

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
}
