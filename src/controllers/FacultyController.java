package controllers;

import java.util.HashMap;

import entities.Faculty;
import entities.User;

public class FacultyController {
	private static FacultyController fc = null;
	private HashMap<String,User> facultyData;
	private FacultyController(HashMap<String,User> facultyData) {
		this.facultyData = facultyData;
	};
	public static FacultyController getInstance(HashMap<String,User> facultyData) {
		if (fc == null) {
			fc = new FacultyController(facultyData);
		}
		return fc;
	}
	public FacultyController getInstance() {
		return fc;
	}
	public Faculty getFacultybyID(String id) {
		return (Faculty) facultyData.get(id);
	}
	
}