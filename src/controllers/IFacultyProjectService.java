package controllers;

import java.util.ArrayList;

import entities.Faculty;
import entities.Project;

public interface IFacultyProjectService {
    public void editProjectTitle(Project p, String title);
    public void saveChanges();
    public Project getProjectbyID(Integer id);
    public int getProjectID(Project p);
    public void viewOwnProjects(Faculty user);
    public void viewActiveProjects(Faculty user);
}
