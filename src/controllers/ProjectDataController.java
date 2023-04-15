package controllers;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import entities.*;


public class ProjectDataController {
	private static ProjectDataController pcc = null;
	private final ProjectService projectService;
	private final StudentService studentService;
	private final FacultyService facultyService;

	private static Integer last_index;
	private ProjectDataController(){
		this.projectService = ProjectService.getInstance();
		this.studentService = StudentService.getInstance();
		this.facultyService = FacultyService.getInstance();
		readProjects();
	}
	
	public static ProjectDataController getInstance() {
		if (pcc == null) {
			pcc = new ProjectDataController();
		}
		return pcc;
	}
	private static String projectsPath = System.getProperty("user.dir") + "//data//projectsList.csv";
	
	private static final String delimiter = ";";
	private void readProjects() {
		last_index = 0;
		File file = new File(projectsPath);
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
				Faculty supervisor = facultyService.getFacultybyName(supervisorName);
				if (supervisor == null) {
					System.out.println(supervisorName);
					System.out.println("Supervisor not found.");
				} else {
					String supervisorID = facultyService.getFacultyID(supervisor);
					projectService.createProject(title, supervisorID, supervisorName, studentID, status, projectID);
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
	}
	protected void updateProjects(ArrayList<Project> projects) {
	    try {
	    	String tempFilePath = projectsPath + ".tmp";
	    	File tempFile = new File(tempFilePath);
	        FileWriter writer = new FileWriter(tempFilePath);
	        writer.append("sep=;");
	        writer.append("\n");
	        writer.append("Supervisor");
	        writer.append(delimiter);
	        writer.append("Title");
	        writer.append(delimiter);
	        writer.append("StudentID");
	        writer.append(delimiter);
	        writer.append("Status");
	        writer.append(delimiter);
	        writer.append("ProjectID");
	        writer.append("\n");
	        for (Project p : projects) {
	            writer.append(p.getSupervisorName());
	            writer.append(delimiter);
	            writer.append(p.getTitle());
	            writer.append(delimiter);
	            writer.append(p.getStudentID());
	            writer.append(delimiter);
	            writer.append(p.getStatus());
	            writer.append(delimiter);
	            writer.append(String.valueOf(p.getID()));
	            writer.append("\n");
	        }
	        writer.flush();
	        writer.close();
	        File origFile = new File(projectsPath);
	        origFile.delete();
	        tempFile.renameTo(origFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public int getNewID() {
		int id = last_index + 1;
		last_index = id;
		return id;
	}
	
}