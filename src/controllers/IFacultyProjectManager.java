package controllers;

import entities.Faculty;
import entities.Project;

public interface IFacultyProjectManager {
	public void addProject(Faculty user,String title);
	public void viewOwnProjects(Faculty user);
	public void viewActiveProjects(Faculty user);
	public void changeTitle(Project p, String t);
	public Project getProjectByID(Integer projectID);
	public void saveChanges();
}
