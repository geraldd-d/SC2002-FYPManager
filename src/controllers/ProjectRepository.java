package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entities.Faculty;
import entities.Project;
import entities.Student;
import entities.User;

public class ProjectRepository {
	private static ProjectRepository prp = null;
	private ArrayList<Project>projectList;
	private ProjectRepository() {
		this.projectList = new ArrayList<Project>();
	}
	public static ProjectRepository getInstance() {
		if(prp == null) {
			prp = new ProjectRepository();
		}
		return prp;
	}
	
	public Project createProject(String title,String supervisorID,String supervisorName,String studentID,String status,Integer projectID) {
		Project p = new Project(title, supervisorID, supervisorName, studentID, status, projectID);
		projectList.add(p);
		return p;
	}
	public void updateProjectTitle(Project p, String title) {
		p.setTitle(title);
	}
	public void setProjectUnavailable(Project p) {
		p.setStatus("Unavailable");
	}
	public void setProjectAvailable(Project p) {
		p.setStatus("Available");
	}
	public void reserveProject(Project p) {
		p.setStatus("Reserved");
	}

	public void allocateProject(String studentID, Project p) {
		p.setStatus("Allocated");
		p.setStudent(studentID);
	}
	public void deregisterProject(Project p) {
		p.setStatus("Available");
		p.setStudent("");
	}
	
	public void transferProject(String replacementID, String replacementName, Project p) {
		p.setSupervisorID(replacementID);
		p.setSupervisorName(replacementName);
	}
	
	public void editProjectTitle(Project p, String title) {
		p.setTitle(title);
		return;
	}
	public ArrayList<Project> getAllAvailableProjects() {
        ArrayList<Project> availableProjects = new ArrayList<Project>();
        for (Project project : projectList) {
            if (project.getStatus().equals("Available")) {
                availableProjects.add(project);
            }
        }
        return availableProjects;
    }
	
	//View all the available projects 
	public void viewAllAvailableProjects(int page) {
		ArrayList<Project>projects = getAllAvailableProjects();
		int pageSize = 5;
		int startIndex = (page - 1) * pageSize;
		int endIndex = Math.min(startIndex + pageSize, projects.size());
		List<Project> currentPage = projects.subList(startIndex, endIndex);
		currentPage.forEach((project)->project.printAvailableProject());
	}
	
	public void viewOwnProjects(Faculty user) {
		ArrayList<Project>projects = user.getProjects();
		projects.forEach((p)-> p.printProject());
	}
	public void viewActiveProjects(Faculty user) {
		ArrayList<Project>projects = user.getProjects();
		projects.forEach((p)-> p.printActiveProject());
	}

	public Project getProjectByID(Integer projectID) {
		for (Project project : projectList) {
			if (project.getID().equals(projectID)) {
				return project;
			}
		}
		return null;
	}
	protected ArrayList<Project> getAllProjects(){
		return this.projectList;
	}
	public String getSupervisorID(Project p) {
		return p.getSupervisorID();
	}
}
