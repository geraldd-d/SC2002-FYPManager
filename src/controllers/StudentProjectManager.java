package controllers;

import java.util.ArrayList;
import java.util.List;

import entities.Project;
import entities.ProjectStatus;

/**
 * This class is used to manage the projects of a student. It implements the IStudentProjectManager interface.
 */
public class StudentProjectManager implements IStudentProjectManager{
	private static StudentProjectManager spm = null;
	private ArrayList<Project> projects;
	private StudentProjectManager(ArrayList<Project> projects) {
		this.projects = projects;
	}
	
	/** 
	 * @param projects
	 * @return StudentProjectManager
	 */
	public static StudentProjectManager getInstance(ArrayList<Project> projects) {
		if (spm == null) {
			spm = new StudentProjectManager(projects);
		}
		return spm;
	}
	
	/** 
	 * @return StudentProjectManager
	 */
	public static StudentProjectManager getInstance() {
		return spm;
	}
	
	/** 
	 * @return ArrayList<Project>
	 */
	@Override
	public ArrayList<Project> getAllAvailableProjects(){
		ArrayList<Project> availableProjects = new ArrayList<Project>();
        for (Project project : projects) {
            if (project.getStatus().equals(ProjectStatus.Available)) {
                availableProjects.add(project);
            }
        }
        return availableProjects;
	}

	
	/** 
	 * @param page
	 */
	@Override
	public void viewAllAvailableProjects(int page) {
		ArrayList<Project>projects = getAllAvailableProjects();
		int pageSize = 5;
		int startIndex = (page - 1) * pageSize;
		int endIndex = Math.min(startIndex + pageSize, projects.size());
		List<Project> currentPage = projects.subList(startIndex, endIndex);
		currentPage.forEach((project)->project.printProject());
	}

	public void viewAvailable() {
		ArrayList<Project>projects = getAllAvailableProjects();
		projects.forEach((project)->project.printProject());
	}
	/** 
	 * @param p
	 */
	@Override
	public void reserveProject(Project p) {
		if (p.getStatus().equals(ProjectStatus.Available)) {
			p.setStatus(ProjectStatus.Reserved);
			System.out.println("Project reserved.");
		}
	}

	@Override
	public void saveChanges() {
		ProjectsController pc = ProjectsController.getInstance();
	    pc.updateProjects(projects);
	}
	
	
}
