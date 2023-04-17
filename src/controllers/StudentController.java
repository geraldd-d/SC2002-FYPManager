package controllers;

import java.util.*;

import entities.*;

/**
 * This class is the controller for the Student class.
 */
public class StudentController {
	private static StudentController sc = null;
	private static HashMap<String,User> studentData;
	private StudentController(HashMap<String,User> studentData) {
		StudentController.studentData = studentData;
	};
	/**
	 * This method is used to get the instance of the StudentController class. It is a singleton class.
	 * @param studentsList The list of students in the system.
	 * @return The instance of the StudentController class.
	 */
	public static StudentController getInstance(HashMap<String,User> studentsList) {
		if (sc == null || !studentsList.equals(studentData)) {
			sc = new StudentController(studentsList);
		}
		return sc;
	}
	/**
	 * This method is used to get the instance of the StudentController class. It is a singleton class.
	 * @return The instance of the StudentController class.
	 */
	public static StudentController getInstance() {
		return sc;
	}
	/**
	 * This method is used to get the student with the given ID.
	 * @param id The ID of the student to be retrieved.
	 * @return The student with the given ID.
	 */
	public Student getStudentbyID(String id) {
		return (Student) studentData.get(id);
	}

	
	/**
	 * This method is used to request allocation of a project.
	 * @param user The student requesting the allocation.
	 * @param id The ID of the project to be allocated.
	 * @return True if the request was successful, false otherwise.
	 */
	public boolean requestAlloc(Student user, int id) {
		ProjectManager pm = ProjectManager.getInstance();
		StudentRequestManager srm = StudentRequestManager.getInstance();
		Project p = pm.getProjectByID(id);
		if (p != null && p.getStatus().equals(ProjectStatus.Available)) {
			srm.addAllocationRequest(user, p);
			p.setStatus(ProjectStatus.Reserved);
			return true;
		}
		return false;
	}
	
	/**
	 * This method is used to request a new title for the registered project.
	 * @param user The student requesting the new title.
	 * @param title New title to be implemented.
	 * @return True if the request was successful, false otherwise.
	 */
	public boolean requestNewTitle(Student user, String title){
		StudentRequestManager srm = StudentRequestManager.getInstance();
		Project p = user.getRegisteredProject();
		if(p!= null && p.getStatus().equals(ProjectStatus.Allocated)){
			srm.addTitleRequest(user, p, title);
			return true;
		}
		return false;
	}
	
	/**
	 * This method is used to view the registered project.
	 * @param user The student whose registered project is to be viewed.
	 * @return True if there is a registered project, false otherwise.
	 */
	public boolean viewRegisteredProject(Student user){
		Project p = user.getRegisteredProject();
		if(p != null){
			p.printProject();
			return true;
		}
		return false;
	}

	/**
	 * This method is to deregister the student from the project.
	 * @param user The student who is deregistering.
	 * @return True if the student was deregistered, false otherwise.
	 */
	public boolean DeregisterProject(Student user){
		StudentRequestManager srm = StudentRequestManager.getInstance();
		Project project = user.getRegisteredProject();
		if(project != null){
			srm.addDeregistrationRequest(user,project);
			return true;
		}
		return false;
	}
}






    
    