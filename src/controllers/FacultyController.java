package controllers;

import java.util.HashMap;

import entities.Faculty;
import entities.User;

public class FacultyController {
	private static FacultyController fc = null;
	private HashMap<String,User> facultyData;
	private HashMap<String,Faculty> facultyNames;
	private FacultyController(HashMap<String,User> facultyData) {
		this.facultyData = facultyData;
		this.facultyNames = getFacultyNames();
	};
	public static FacultyController getInstance(HashMap<String,User> facultyData) {
		if (fc == null) {
			fc = new FacultyController(facultyData);
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
	private HashMap<String,Faculty> getFacultyNames(){
		HashMap<String,Faculty> faculties = new HashMap<String,Faculty>();
		facultyData.forEach((key, value)-> {
			Faculty f = (Faculty) value;
			faculties.put(f.getName(),f);
		});
		return faculties;
	}
	public User authFaculty(String input) {
		return this.facultyData.containsKey(input) ? this.facultyData.get(input) : null;
	}
	
}