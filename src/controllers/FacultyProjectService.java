package controllers;

import java.util.ArrayList;

import entities.Faculty;
import entities.Project;
import entities.ProjectStatus;

public class FacultyProjectService implements IFacultyProjectService{
	private final ProjectDataController projectDataController;
	private final ProjectRepository projectRepository;
	private final StudentService studentService;
	private final FacultyService facultyService;
	private static FacultyProjectService fpsc = null;
	private FacultyProjectService(){
		this.facultyService = FacultyService.getInstance();
		this.studentService = StudentService.getInstance();
		this.projectDataController = ProjectDataController.getInstance();
		this.projectRepository = ProjectRepository.getInstance();
	};
	public static FacultyProjectService getInstance() {
		if (fpsc == null) {
			fpsc = new FacultyProjectService();
		}
		return fpsc;
	}
	public Project addProject(String title,String supervisorID,String supervisorName,String studentID,ProjectStatus status){
		Integer projectID = projectDataController.getNewID();
		Project p = projectRepository.createProject(title, supervisorID, supervisorName, studentID, status, projectID);
		return p;
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
		String id = user.getUserID();
		ArrayList<Project>projects = facultyService.getFacultyProjects(id);
		projects.forEach((p)-> p.printProject());
	}
	public void viewActiveProjects(Faculty user) {
		String id = user.getUserID();
		ArrayList<Project>projects = facultyService.getFacultyProjects(id);
		projects.forEach((p)-> p.printActiveProject());
	}

}
