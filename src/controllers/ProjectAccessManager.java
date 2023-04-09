package controllers;

import java.util.ArrayList;

import entities.Project;
import entities.Student;

public class ProjectAccessManager {
	private ArrayList<Project> projects;
	private static ProjectAccessManager pam = null;
	private ProjectAccessManager(ArrayList<Project> p) {
		this.projects = p;
	};
	public static ProjectAccessManager getInstance(ArrayList<Project> p) {
		if (pam == null) {
			pam = new ProjectAccessManager(p);
		}
		return pam;
	}
	public static ProjectAccessManager getInstance() {
		return pam;
	}
	public void viewProjects(Student user) {
		
	}
}
