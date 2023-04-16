package controllers;

import java.util.ArrayList;

import entities.Faculty;
import entities.Project;

public interface IStudentProjectService {
	public ArrayList<Project> getAllAvailableProjects();
	public void viewAllAvailableProjects(int page);
	public void reserveProject(Project p);
    public void saveChanges();
    public Project getProjectbyID(Integer id);
    public int getProjectID(Project p);
}
