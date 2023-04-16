package controllers;

import entities.Faculty;
import entities.Project;
import entities.Student;

public interface ICoordProjectManager {
	public void allocateProject(Student student, Project p);
	public void deregisterProject(Student student, Project p );
	public void transferProject(Faculty current, String replacementID, String replacementName, Project p);
	public void addProject(Faculty user,String title);
	public void viewOwnProjects(Faculty user);
	public void viewActiveProjects(Faculty user);
	public Project getProjectByID(Integer projectID);
	public void saveChanges();
	void changeTitle(Project p, String t);
}
