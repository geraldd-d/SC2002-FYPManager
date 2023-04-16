package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entities.Faculty;
import entities.Project;
import entities.ProjectStatus;
import entities.Student;
import entities.User;

public class ProjectManager {
	private ArrayList<Project> projects;
	private static ProjectManager pm = null;
	private ProjectManager(ArrayList<Project> p) {
		this.projects = p;
		CoordProjectManager cpm = CoordProjectManager.getInstance(projects);
		FacultyProjectManager fpm = FacultyProjectManager.getInstance(projects);
		StudentProjectManager spm = StudentProjectManager.getInstance(projects);
		capProjects();
	};
	public static ProjectManager getInstance(ArrayList<Project> p) {
		if (pm == null) {
			pm = new ProjectManager(p);
		}
		return pm;
	}
	public static ProjectManager getInstance() {
		return pm;
	}
	public void capProjects() {
		FacultyController fc = FacultyController.getInstance();
		projects.forEach((p)->{
			Faculty f = fc.getFacultybyID(p.getSupervisorID());
			if (f.getActiveProjects() >= 2 && p.getStatus().equals(ProjectStatus.Available)) {
				p.setStatus(ProjectStatus.Unavailable);
			}
		});
	}
	
	public void saveChanges() {
	    ProjectsController pc = ProjectsController.getInstance();
	    pc.updateProjects(projects);
	}
	public Project getProjectByID(Integer projectID) {
		for (Project project : projects) {
			if (project.getID().equals(projectID)) {
				return project;
			}
		}
		return null;
	}
}
