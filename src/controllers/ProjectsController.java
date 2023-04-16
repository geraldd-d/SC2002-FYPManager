package controllers;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import entities.*;

/**
 * This class is the controller for the Project class.
 */
public class ProjectsController {
	private static ProjectsController pcc = null;
	private static Integer last_index;
	private ArrayList<Project> projectList;
	private ProjectsController(){
		ArrayList<Project> projects = readProjects();
		this.projectList = projects;
		ProjectManager.getInstance(projects);
	}
	
	/**
	 * This method is used to get the instance of the ProjectsController. It is a singleton class.
	 * @return The instance of the ProjectsController class.
	 */
	public static ProjectsController getInstance() {
		if (pcc == null) {
			pcc = new ProjectsController();
		}
		return pcc;
	}
	
	private static String projectsPath = System.getProperty("user.dir") + "//data//projectsList.csv";
	
	private static final String delimiter = ";";

	/**
	 * This method is used to read the projects from the file.
	 * @return The list of projects in the system.
	 */
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
				ProjectStatus status = ProjectStatus.valueOf(fields[3]);
				Integer projectID = Integer.parseInt(fields[4]);
				if (projectID > last_index) {
					last_index = projectID;
				}
				Faculty supervisor = fc.getFacultybyName(supervisorName);
				if (supervisor == null) {
					System.out.println(supervisorName);
					System.out.println("Supervisor not found.");
				} else {
					Project p = new Project(title,supervisor.getUserID(),supervisorName,studentID,status,projectID);
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

	/**
	 * This method is used to update the projects in the file.
	 * @param projects The list of projects to be updated.
	 */
	protected void updateProjects(ArrayList<Project> projects) {
	    try {
	    	FacultyController fc = FacultyController.getInstance();
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
	            writer.append(p.getStatus().toString());
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
	
	/**
	 * This method is used to get the next ID for a new project.
	 * @return The next ID for a new request.
	 */
	public int getNewID() {
		int id = last_index + 1;
		last_index = id;
		return id;
	}
	
}