package controllers;

import java.util.ArrayList;
import java.util.List;

import entities.Project;
import entities.ProjectStatus;

public class StudentProjectManager implements IStudentProjectManager{
	private static StudentProjectManager spm = null;
	private ArrayList<Project> projects;
	private StudentProjectManager(ArrayList<Project> projects) {
		this.projects = projects;
	}
	public static StudentProjectManager getInstance(ArrayList<Project> projects) {
		if (spm == null) {
			spm = new StudentProjectManager(projects);
		}
		return spm;
	}
	public static StudentProjectManager getInstance() {
		return spm;
	}
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

	@Override
	public void viewAllAvailableProjects(int page) {
		ArrayList<Project>projects = getAllAvailableProjects();
		int pageSize = 5;
		int startIndex = (page - 1) * pageSize;
		int endIndex = Math.min(startIndex + pageSize, projects.size());
		List<Project> currentPage = projects.subList(startIndex, endIndex);
		currentPage.forEach((project)->project.printAvailableProject());
	}

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
