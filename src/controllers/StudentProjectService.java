package controllers;

import java.util.ArrayList;
import java.util.List;

import entities.Project;

public class StudentProjectService implements IStudentProjectService{
	private final ProjectDataController projectDataController;
	private final ProjectRepository projectRepository;
	private final StudentService studentService;
	private final FacultyService facultyService;
	private static StudentProjectService spsc = null;
	private StudentProjectService(){
		this.facultyService = FacultyService.getInstance();
		this.studentService = StudentService.getInstance();
		this.projectDataController = ProjectDataController.getInstance();
		this.projectRepository = ProjectRepository.getInstance();
	};
	public static StudentProjectService getInstance() {
		if (spsc == null) {
			spsc = new StudentProjectService();
		}
		return spsc;
	}

	@Override
	public ArrayList<Project> getAllAvailableProjects() {
		return projectRepository.getAllAvailableProjects();
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
		projectRepository.reserveProject(p);
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
