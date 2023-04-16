package controllers;

import java.util.ArrayList;
import java.util.List;

import entities.Coordinator;
import entities.Faculty;
import entities.Project;
import entities.ProjectStatus;

public class CoordProjectService implements ICoordProjectService, IFacultyProjectService{
	private final ProjectDataController projectDataController;
	private final ProjectRepository projectRepository;
	private final StudentService studentService;
	private final FacultyService facultyService;
	private static CoordProjectService cpsc = null;
	private CoordProjectService(){
		this.facultyService = FacultyService.getInstance();
		this.studentService = StudentService.getInstance();
		this.projectDataController = ProjectDataController.getInstance();
		this.projectRepository = ProjectRepository.getInstance();
	};
	public static CoordProjectService getInstance() {
		if (cpsc == null) {
			cpsc = new CoordProjectService();
		}
		return cpsc;
	}
	public void createProject(String title,String supervisorID,String supervisorName,String studentID,ProjectStatus status,Integer projectID) {
		Project p = projectRepository.createProject(title, supervisorID, supervisorName, studentID, status, projectID);
		Faculty f = facultyService.getFacultybyID(supervisorID);
		facultyService.addFacultyProject(f, p);
		if (!studentID.equals("")) {
			studentService.setStudentProject(studentID,p);
		}
	}
	public void lockProjects(String id) {
		ArrayList<Project> facProjects = facultyService.getFacultyProjects(id);
		if (facultyService.getActiveProjects(id) >= 2){
			for (Project p : facProjects) {
				if (p.getStatus().equals(ProjectStatus.Available)) {
					projectRepository.setProjectUnavailable(p);
				}
			}
		}
	}
	public void unlockProjects(String id) {
		ArrayList<Project> facProjects = facultyService.getFacultyProjects(id);
		if (facultyService.getActiveProjects(id) < 2){
			for (Project p : facProjects) {
				if (p.getStatus().equals(ProjectStatus.Unavailable)) {
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
	public void viewOwnProjects(Faculty user) {
		ArrayList<Project>projects = user.getProjects();
		projects.forEach((p)-> p.printProject());
	}
	public void viewActiveProjects(Faculty user) {
		ArrayList<Project>projects = user.getProjects();
		projects.forEach((p)-> p.printActiveProject());
	}
	public void viewAvailableProjects(Coordinator user) {
		ArrayList<Project>projects = projectRepository.getAllAvailableProjects();
		projects.forEach((p)-> p.printProject());
	}
	public void viewUnavailableProjects(Coordinator user) {
		ArrayList<Project>projects = projectRepository.getAllUnavailableProjects();
		projects.forEach((p)-> p.printProject());
	}
	public void viewReservedProjects(Coordinator user) {
		ArrayList<Project>projects = projectRepository.getAllReservedProjects();
		projects.forEach((p)-> p.printProject());
	}
	public void viewAllocatedProjects(Coordinator user) {
		ArrayList<Project>projects = projectRepository.getAllAllocatedProjects();
		projects.forEach((p)-> p.printProject());
	}
	public ArrayList<Project> getAvailableProjects(Coordinator user) {
		ArrayList<Project>projects = projectRepository.getAllAvailableProjects();
		return projects;
	}
	public ArrayList<Project> getUnavailableProjects(Coordinator user) {
		ArrayList<Project>projects = projectRepository.getAllUnavailableProjects();
		return projects;
	}
	public ArrayList<Project> getReservedProjects(Coordinator user) {
		ArrayList<Project>projects = projectRepository.getAllReservedProjects();
		return projects;
	}
	public ArrayList<Project> getAllocatedProjects(Coordinator user) {
		ArrayList<Project>projects = projectRepository.getAllAllocatedProjects();
		return projects;
	}
	
}
