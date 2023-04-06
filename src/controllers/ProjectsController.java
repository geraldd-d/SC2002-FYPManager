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
	private static String projectsPath = System.getProperty("user.dir") + "\\data\\projectsList.csv";

	public static final String delimiter = ",";
	public ArrayList<Project> readProjects() {
		File file = new File(projectsPath);
		ArrayList<Project> projects = new ArrayList<Project>();
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			String[] fields;
			boolean firstLine = true;
			while ((line = br.readLine()) != null) {
				if (firstLine) {
					firstLine = false;
					continue;
				}
				fields = line.split(delimiter);
				String supervisorName = fields[0];
				String title = fields[1];
				Faculty supervisor = this.facultyNames.get(supervisorName);
				if (supervisor == null) {
					System.out.println(supervisorName);
					System.out.println("Supervisor not found.");
				} else {
					Project p = new Project(title,supervisor);
					projects.add(p);
					supervisor.addProject(p);
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
}
