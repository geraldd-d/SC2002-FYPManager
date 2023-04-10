package controllers;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import entities.*;


public class ProjectsController {
	private static ProjectsController pcc = null;
	private HashMap<String,Faculty> facultyNames;
	private static Integer last_index;
	private ArrayList<Project> projectList;
	private ProjectsController(){
		ArrayList<Project> projects = readProjects();
		this.projectList = projects;
		ProjectAccessManager pam = ProjectAccessManager.getInstance(projects);
	}
	
	public static ProjectsController getInstance(HashMap<String, User> facultyList){
		if (pcc == null) {
			pcc = new ProjectsController();
		}
		return pcc;
	}
	
	public static ProjectsController getInstance() {
		return pcc;
	}
	
	private static String projectsPath = System.getProperty("user.dir") + "//data//projectsList.csv";

	private static final String delimiter = ";";
	private ArrayList<Project> readProjects() {
		FacultyController fc = FacultyController.getInstance();
		StudentController sc = StudentController.getInstance();
		last_index = 0;
		File file = new File(projectsPath);
		ArrayList<Project> projects = new ArrayList<Project>();
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			String[] fields;
			while ((line = br.readLine()) != null) {
				
				fields = line.split(delimiter);
				String supervisorName = fields[0];
				if (supervisorName.equals("Supervisor") || supervisorName.equals("sep=")) {
					continue;
				}
				String title = fields[1];
				String studentID = fields[2];
				String status = fields[3];
				Integer projectID = Integer.parseInt(fields[4]);
				if (projectID > last_index) {
					last_index = projectID;
				}
				Faculty supervisor = fc.getFacultybyName(supervisorName);
				if (supervisor == null) {
					System.out.println(supervisorName);
					System.out.println("Supervisor not found.");
				} else {
					Project p = new Project(title,supervisor.getSupervisorID(),supervisorName,studentID,status,projectID);
					projects.add(p);
					supervisor.addProject(p);
					if (!studentID.equals("")) {
						Student student = sc.getStudentbyID(studentID);
						student.setRegisteredProject(p);
					}
				}
			}
			br.close();
		}
		catch (FileNotFoundException fe) {
			fe.printStackTrace();
			System.exit(0);
		}
		catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		return projects;
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

	public Project getProjectByName(String projectName) {
		for (Project project : projectList) {
			if (project.getTitle().equals(projectName)) {
				return project;
			}
		}
		return null;
	}
	public Project getProjectByID(Integer projectID) {
		for (Project project : projectList) {
			if (project.getID().equals(projectID)) {
				return project;
			}
		}
		return null;
	}
	
	
}