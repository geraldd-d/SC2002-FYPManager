package controllers;

import java.util.ArrayList;

import entities.Faculty;
import entities.Project;
import entities.ProjectStatus;

public class ProjectService {
	private final ProjectRepository projectRepository;
	private final StudentService studentService;
	private final FacultyService facultyService;
	private static ProjectService psc = null;
	private ProjectService() {
		this.facultyService = FacultyService.getInstance();
		this.studentService = StudentService.getInstance();
		this.projectRepository = ProjectRepository.getInstance();
		capProjects();
	};
	public static ProjectService getInstance() {
		if (psc == null) {
			psc = new ProjectService();
		}
		return psc;
	}
	public void createProject(String title,String supervisorID,String supervisorName,String studentID,ProjectStatus status,Integer projectID) {
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
	public void saveChanges() {
		ProjectDataController projectDataController = ProjectDataController.getInstance();
		ArrayList<Project> projects = projectRepository.getAllProjects();
		projectDataController.updateProjects(projects);
	}
	public Project getProjectbyID(Integer id){
		return projectRepository.getProjectByID(id);
	}
	public int getProjectID(Project p){
		return projectRepository.getProjectID(p);
	}
	public ArrayList<Project> getAllAvailableProjects(){
		return projectRepository.getAllAvailableProjects();
	}
	public ArrayList<Project> getAllUnavailableProjects(){
		return projectRepository.getAllUnavailableProjects();
	}
	public ArrayList<Project> getAllReservedProjects(){
		return projectRepository.getAllReservedProjects();

	}
	public ArrayList<Project> getAllAllocatedProjects(){
		return projectRepository.getAllAllocatedProjects();
	}
}
