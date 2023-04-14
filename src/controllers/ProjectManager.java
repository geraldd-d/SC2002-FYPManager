package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entities.Faculty;
import entities.Project;
import entities.Student;
import entities.User;

public class ProjectManager {
	private ArrayList<Project> projects;
	private static ProjectManager pm = null;
	private ProjectManager(ArrayList<Project> p) {
		this.projects = p;
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
			if (f.getActiveProjects() >= 2 && !p.getStatus().equals("Allocated")) {
				p.setStatus("Unavailable");
			}
		});
	}
	public void reserveProject(Project p) {
		ProjectsController pc = ProjectsController.getInstance();
		if (p.getStatus().equals("Available")) {
			p.setStatus("Reserved");
			System.out.println("Project reserved.");
		}
	}
	public void addProject(Faculty user,String title) {
		ProjectsController pc = ProjectsController.getInstance();
		if (user.getActiveProjects() < 2) {
			Project p = new Project(title,user.getUserID(),user.getName(),null,"Available",pc.getNewID());
			projects.add(p);
		}
		else {
			Project p = new Project(title,user.getUserID(),user.getName(),null,"Unavailable",pc.getNewID());
			projects.add(p);
		}
	}
	public void allocateProject(Student student, int projectID) {
		FacultyController fc = FacultyController.getInstance();
		StudentController sc = StudentController.getInstance();
		Project p = getProjectByID(projectID);
		p.setStatus("Allocated");
		p.setStudent(student.getUserID());
		sc.setRegisteredProject(student, p);
		setUnavailable(fc.getFacultybyID(p.getSupervisorID()));

	}
	public void deregisterProject(Student student) {
		FacultyController fc = FacultyController.getInstance();
		StudentController sc = StudentController.getInstance();
		Project p = student.getRegisteredProject();
		p.setStatus("Available");
		p.setStudent("");
		sc.setRegisteredProject(student, null);
		setAvailable(fc.getFacultybyID(p.getSupervisorID()));
	}
	
	public void transferProject(Faculty current, Faculty replacement, Project p) {
		FacultyController fc = FacultyController.getInstance();
		p.setSupervisorID(replacement.getUserID());
		p.setSupervisorName(replacement.getUserID());
		replacement.addProject(p);
		ArrayList<Project> projects = current.getProjects();
		projects.remove(p);
		fc.setProjects(current, projects);
		setAvailable(current);
		setUnavailable(replacement);
	}
	protected void setUnavailable(Faculty user) {
		ArrayList<Project> facProjects = user.getProjects();
		if (user.getActiveProjects() >= 2){
			for (Project p : facProjects) {
				if (!p.getStatus().equals("Allocated")) {
					p.setStatus("Unavailable");
				}
			}
		}
	}
	protected void setAvailable(Faculty user) {
		ArrayList<Project> facProjects = user.getProjects();
		if (user.getActiveProjects() <= 1){
			for (Project p : facProjects) {
				if (p.getStatus().equals("Unavailable")) {
					p.setStatus("Available");
				}
			}
		}
	}
	public ArrayList<Project> getAllAvailableProjects() {
        ArrayList<Project> availableProjects = new ArrayList<Project>();
        for (Project project : projects) {
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
	public void changeTitle(Project p, String t) {
		p.setTitle(t);
	}
	
	public Project getProjectByName(String projectName) {
		for (Project project : projects) {
			if (project.getTitle().equals(projectName)) {
				return project;
			}
		}
		return null;
	}
	public Project getProjectByID(Integer projectID) {
		for (Project project : projects) {
			if (project.getID().equals(projectID)) {
				return project;
			}
		}
		return null;
	}
	public void saveChanges() {
	    ProjectsController pc = ProjectsController.getInstance();
	    pc.updateProjects(projects);
	}
}