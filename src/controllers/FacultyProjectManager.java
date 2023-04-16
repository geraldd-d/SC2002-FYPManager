package controllers;

import java.util.ArrayList;

import entities.Faculty;
import entities.Project;
import entities.ProjectStatus;

public class FacultyProjectManager implements IFacultyProjectManager{
	private static FacultyProjectManager fpm = null;
	private ArrayList<Project> projects;
	private FacultyProjectManager(ArrayList<Project> projects) {
		this.projects = projects;
	}
	public static FacultyProjectManager getInstance(ArrayList<Project> projects) {
		if (fpm == null) {
			fpm = new FacultyProjectManager(projects);
		}
		return fpm;
	}
	public static FacultyProjectManager getInstance() {
		return fpm;
	}
	@Override
	public void addProject(Faculty user, String title) {
		FacultyController fc = FacultyController.getInstance();
		ProjectsController pc = ProjectsController.getInstance();
		ProjectStatus status;
		if (user.getActiveProjects() < 2) {
			status = ProjectStatus.Available;
		} else {
			status = ProjectStatus.Unavailable;
		}
		Project p = new Project(title, user.getUserID(), user.getName(), "", status, pc.getNewID());
		user.addProject(p);
		projects.add(p);
	}

	@Override
	public void viewOwnProjects(Faculty user) {
		ArrayList<Project>projects = user.getProjects();
		projects.forEach((p)-> p.printProject());
	}

	@Override
	public void viewActiveProjects(Faculty user) {
		ArrayList<Project>projects = user.getProjects();
		projects.forEach((p)-> p.printActiveProject());
	}

	@Override
	public void changeTitle(Project p, String t) {
		p.setTitle(t);
	}

	@Override
	public Project getProjectByID(Integer projectID) {
		for (Project project : projects) {
			if (project.getID().equals(projectID)) {
				return project;
			}
		}
		return null;
	}

	@Override
	public void saveChanges() {
		ProjectsController pc = ProjectsController.getInstance();
	    pc.updateProjects(projects);
	}



}
