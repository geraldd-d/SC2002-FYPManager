package controllers;

import java.util.ArrayList;

import entities.Faculty;
import entities.Project;

public class ProjectService {
	private final ProjectDataController projectDataController;
	private final ProjectRepository projectRepository;
	private final StudentService studentService;
	private final FacultyService facultyService;
	private static ProjectService psc = null;
	private ProjectService() {
		this.facultyService = FacultyService.getInstance();
		this.studentService = StudentService.getInstance();
		this.projectDataController = ProjectDataController.getInstance();
		this.projectRepository = ProjectRepository.getInstance();
		capProjects();
	};
	public static ProjectService getInstance() {
		if (psc == null) {
			psc = new ProjectService();
		}
		return psc;
	}
	public void createProject(String title,String supervisorID,String supervisorName,String studentID,String status,Integer projectID) {
		Project p = projectRepository.createProject(title, supervisorID, supervisorName, studentID, status, projectID);
		Faculty f = facultyService.getFacultybyID(supervisorID);
		facultyService.addFacultyProject(f, p);
		if (!studentID.equals("")) {
			studentService.setStudentProject(studentID,p);
		}
	}
	public void capProjects() {
		ArrayList<Project> projects = projectRepository.getAllProjects();
		projects.forEach((p)->{
			String id = projectRepository.getSupervisorID(p);
			Faculty f = facultyService.getFacultybyID(id);
			if (f.getActiveProjects() >= 2 && !p.getStatus().equals("Allocated")) {
				projectRepository.setProjectUnavailable(p);
			}
		});
	}
	public void lockProjects(String id) {
		ArrayList<Project> facProjects = facultyService.getFacultyProjects(id);
		if (facultyService.getActiveProjects(id) >= 2){
			for (Project p : facProjects) {
				if (!p.getStatus().equals("Allocated")) {
					projectRepository.setProjectUnavailable(p);
				}
			}
		}
	}
	public void unlockProjects(String id) {
		ArrayList<Project> facProjects = facultyService.getFacultyProjects(id);
		if (facultyService.getActiveProjects(id) < 2){
			for (Project p : facProjects) {
				if (p.getStatus().equals("Unavailable")) {
					projectRepository.setProjectAvailable(p);
				}
			}
		}
	}
	public void allocateProject(String studentID, Project p) {
		projectRepository.allocateProject(studentID, p);
		studentService.setStudentProject(studentID, p);
		String id = projectRepository.getSupervisorID(p);
		lockProjects(id);
	}
	public void deregisterProject(String studentID, Project p ) {
		projectRepository.deregisterProject(p);
		studentService.setStudentProject(studentID, null);
		String id = projectRepository.getSupervisorID(p);
		unlockProjects(id);
	}
	public void transferProject(String currentID, String replacementID, String replacementName, Project p) {
		projectRepository.transferProject(replacementID, replacementName, p);
		facultyService.removeProject(currentID, p);
		unlockProjects(currentID);
		lockProjects(replacementID);
	}
	public void editProjectTitle(Project p, String title) {
		projectRepository.updateProjectTitle(p, title);
	}
	public void saveChanges() {
		ArrayList<Project> projects = projectRepository.getAllProjects();
		projectDataController.updateProjects(projects);
	}
	public Project getProjectbyID(Integer id){
		return projectRepository.getProjectByID(id);
	}
	public int getProjectID(Project p){
		return projectRepository.getProjectID(p);
	}
}
