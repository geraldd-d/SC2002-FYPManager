package controllers;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import entities.*;


public class ProjectsController {
	private static ProjectsController pcc = null;
	private HashMap<String,Faculty> facultyNames;
	public ArrayList<Project> projectList;
	private ProjectsController(){
		HashMap<String,Faculty> faculties = getFacultyNames();
		this.facultyNames = faculties;
		ArrayList<Project> projects = readProjects();
		this.projectList = projects;
	}
	
	public static ProjectsController getInstance(){
		if (pcc == null) {
			pcc = new ProjectsController();
		}
		return pcc;
	}
	private HashMap<String,Faculty> getFacultyNames(){
		HashMap<String,Faculty> faculties = new HashMap<String,Faculty>();
		AccountsController acc = AccountsController.getInstance();
		acc.facultyList.forEach((key, value)-> {
			Faculty f = (Faculty) value;
			faculties.put(f.getName(),f);
		});
		return faculties;
	}
	private static String projectsPath = System.getProperty("user.dir") + "//data//projectsList.csv";

	public static final String delimiter = ",";
	public ArrayList<Project> readProjects() {
		File file = new File(projectsPath);
		ArrayList<Project> projects = new ArrayList<Project>();
		AccountsController acc = AccountsController.getInstance();
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			String[] fields;
			while ((line = br.readLine()) != null) {
				
				fields = line.split(delimiter);
				String supervisorName = fields[0];
				if (supervisorName.equals("Supervisor")) {
					continue;
				}
				String title = fields[1];
				String studentID = fields[2];
				String status = fields[3];
				Faculty supervisor = this.facultyNames.get(supervisorName);
				if (supervisor == null) {
					System.out.println(supervisorName);
					System.out.println("Supervisor not found.");
				} else {
					Project p = new Project(title,supervisor.getSupervisorID(),studentID,status);
					projects.add(p);
					supervisor.addProject(p);
					if (!studentID.equals("")) {
						Student student = acc.getStudent(studentID);
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
}
