package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entities.Faculty;
import entities.Project;
import entities.ProjectStatus;
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
	
	public Project createProject(String title,String supervisorID,String supervisorName,String studentID,ProjectStatus status,Integer projectID) {
		Project p = new Project(title, supervisorID, supervisorName, studentID, status, projectID);
		projectList.add(p);
		return p;
	}
	public void updateProjectTitle(Project p, String title) {
		p.setTitle(title);
	}
	public void setProjectUnavailable(Project p) {
		p.setStatus(ProjectStatus.Unavailable);
	}
	public void setProjectAvailable(Project p) {
		p.setStatus(ProjectStatus.Available);
	}
	public void reserveProject(Project p) {
		p.setStatus(ProjectStatus.Reserved);
	}

	public void allocateProject(String studentID, Project p) {
		p.setStatus(ProjectStatus.Allocated);
		p.setStudent(studentID);
	}
	public void deregisterProject(Project p) {
		p.setStatus(ProjectStatus.Available);
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
            if (project.getStatus().equals(ProjectStatus.Available)) {
                availableProjects.add(project);
                System.out.println("test");
            }
        }
        return availableProjects;
    }
	public ArrayList<Project> getAllUnavailableProjects() {
        ArrayList<Project> unavailableProjects = new ArrayList<Project>();
        for (Project project : projectList) {
            if (project.getStatus().equals(ProjectStatus.Unavailable)) {
                unavailableProjects.add(project);
            }
        }
        return unavailableProjects;
    }

	public ArrayList<Project> getAllReservedProjects() {
        ArrayList<Project> reservedProjects = new ArrayList<Project>();
        for (Project project : projectList) {
            if (project.getStatus().equals(ProjectStatus.Reserved)) {
            	reservedProjects.add(project);
            }
        }
        return reservedProjects;
    }

	public ArrayList<Project> getAllAllocatedProjects() {
        ArrayList<Project> allocatedProjects = new ArrayList<Project>();
        for (Project project : projectList) {
            if (project.getStatus().equals(ProjectStatus.Allocated)) {
            	allocatedProjects.add(project);
            }
        }
        return allocatedProjects;
    }


	public Project getProjectByID(Integer projectID) {
		for (Project project : projectList) {
			if (project.getID().equals(projectID)) {
				return project;
			}
		}
		return null;
	}
	public Integer getProjectID(Project p) {
		return p.getID();
	}
	protected ArrayList<Project> getAllProjects(){
		return this.projectList;
	}
	public String getSupervisorID(Project p) {
		return p.getSupervisorID();
	}
}
