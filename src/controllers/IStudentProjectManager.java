package controllers;

import java.util.ArrayList;

import entities.Project;

public interface IStudentProjectManager {
	public void viewAllAvailableProjects(int page);
	public void reserveProject(Project p);
    public void saveChanges();
}
