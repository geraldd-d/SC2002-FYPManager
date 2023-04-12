package controllers;

import java.util.*;

import entities.*;

public class StudentController {
	private static StudentController sc = null;
	private HashMap<String,User> studentData;
	private StudentController(HashMap<String,User> studentData) {
		this.studentData = studentData;
	};
	public static StudentController getInstance(HashMap<String,User> studentData) {
		if (sc == null) {
			sc = new StudentController(studentData);
		}
		return sc;
	}
	public static StudentController getInstance() {
		return sc;
	}
	public Student getStudentbyID(String id) {
		return (Student) studentData.get(id);
	}
	public User authStudent(String input) {
		return this.studentData.containsKey(input) ? this.studentData.get(input) : null;
	}

	//View all the available projects 
	public List<Project> ViewAllAvailableProjects() {
		ProjectsController pc = ProjectsController.getInstance();
		return pc.getAllAvailableProjects();
	}

	// Request the supervisor for the allocation of the names 
	public void requestProject(String userID, ArrayList<Request> requestHistory) {
		Scanner scanner = new Scanner(System.in);
	
		System.out.print("Enter ProjectID:");
		int projectID = scanner.nextInt();

		ProjectsController pc = ProjectsController.getInstance();
    	Project project = pc.getProjectByID(projectID);
		if (project == null) {
			System.out.println("Project not found!");
			return;
		}
		Student student = getStudentbyID(userID);
		if (student == null) {
			System.out.println("Student not found!");
			return;
		} 
		RequestController requestcontroller = new RequestController();
		boolean success = requestcontroller.requestProject(student, project);
		if (success) {
			requestcontroller.addToRequestHistory(requestcontroller, requestHistory);
			System.out.println("Your request has been submitted to the supervisor.");
		} else {
			System.out.println("Error submitting request.");
		}
	}

	// View the registered project 
	public Project viewRegisteredProject(Student student) {
		if (student.getRegisteredProject() == null) {
			System.out.println("You have not registered for any project.");
			return null;
		}
		return student.getRegisteredProject();
	}

	// Request the supervisor for change the title of the registered project
	public void requestNewTitle(Student student, ArrayList<Request> requestHistory) {
		Scanner scanner = new Scanner(System.in);
	
		// get the registered project of the student
		Project registeredProject = student.getRegisteredProject();
		if (registeredProject == null) {
			System.out.println("You have not registered for any project yet.");
			return;
		}
	
		// display the current project details
		System.out.println("Your current project details:");
		System.out.println("ProjectID: " + registeredProject.getID());
		System.out.println("Title: " + registeredProject.getTitle());
		System.out.println("Supervisor: Dr. " + registeredProject.getSupervisorName());
		System.out.println();
	
		// prompt the student to enter a new title
		System.out.print("Enter a new title for your project: ");
		String newTitle = scanner.nextLine();
	
		// create a request for the new title
		RequestController requestController = new RequestController();
		boolean success = requestController.requestNewTitle(student, registeredProject, newTitle);
		if (success) {
			requestController.addToRequestHistory(requestController, requestHistory);
			System.out.println("Your request for a new title has been submitted to the supervisor.");
		} else {
			System.out.println("Error submitting request.");
		}
	}

	public void deregisterProject(Student student, ArrayList<Request> requestHistory) {
		RequestController requestController = new RequestController();
		Project project = student.getRegisteredProject();
		if (project == null) {
			System.out.println("You are not registered for any project.");
			return;
		}
	
		boolean success = requestController.deregisterProject(student, project);
		if (success) {
			requestController.addToRequestHistory(requestController, requestHistory);
			System.out.println("You have been deregistered from the project.");
		} else {
			System.out.println("Error deregistering from the project.");
		}
	}

	public void viewRequestHistory(Student student) {
		ArrayList<Request> history = student.getHistory();
		if (history == null || history.isEmpty()) {
			System.out.println("No request history found.");
			return;
		}
		
		for (Request request : history) {
			if (request == null) {
				System.out.println("NULL!");
				continue;
			}
			System.out.println("Request History:");
			System.out.println("RequestID: " + request.getRequestID());
			System.out.println("ProjectID: " + request.getProject().getID());
			System.out.println("Requestor: " + request.getRequestor().getName());
			System.out.println("Requestee: " + request.getRequestee().getName());
			System.out.println("Type: " + request.getRequestType());
			System.out.println();
		}
	}
	

	






}






    
    