package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entities.*;

/**
 * This class is the controller for the Faculty class.
 */
public class FacultyController {
	private static FacultyController fc = null;
	private static HashMap<String,User> facultyData;
	private HashMap<String,Faculty> facultyNames;
	private Coordinator coordinator;
	/**
	 * This is the constructor for the FacultyController class.
	 * @param facultyData The list of faculty in the system.
	 */
	private FacultyController(HashMap<String,User> facultyData) {
		FacultyController.facultyData = facultyData;
		this.facultyNames = getFacultyNames();
	};
	/**
	 * This method is used to get the instance of the FacultyController class. It is a singleton class.
	 * @param facultyList The list of faculty in the system.
	 * @return The instance of the FacultyController class.
	 */
	public static FacultyController getInstance(HashMap<String,User> facultyList) {
		if (fc == null|| !facultyList.equals(facultyData)) {
			fc = new FacultyController(facultyList);
		}
		return fc;
	}
	/**
	 * This method is used to get the instance of the FacultyController class. It is a singleton class.
	 * @return The instance of the FacultyController class.
	 */
	public static FacultyController getInstance() {
		return fc;
	}
	/**
	 * This method is used to get a faculty by their ID.
	 * @param id The ID of the faculty.
	 * @return The faculty with the given ID.
	 */
	public Faculty getFacultybyID(String id) {
		return (Faculty) facultyData.get(id);
	}
	/**
	 * This method is used to get a faculty by their name.
	 * @param name The name of the faculty.
	 * @return The faculty with the given name.
	 */
	public Faculty getFacultybyName(String name) {
		return (Faculty) facultyNames.get(name);
	}
	/**
	 * This method is used to get a hashmap of all the faculty in the system.
	 * @return A hashmap of all the faculty in the system.
	 */
	public HashMap<String,Faculty> getFacultyNames(){
		HashMap<String,Faculty> faculties = new HashMap<String,Faculty>();
		facultyData.forEach((key, value)-> {
			if (value instanceof Coordinator) {
				this.coordinator = (Coordinator) value;
			}
			Faculty f = (Faculty) value;
			faculties.put(f.getName(),f);
		});
		return faculties;
	}
	/**
	 * This method is used to get the coordinator of the system.
	 * @return The coordinator of the system.
	 */
	public Coordinator getCoord() {
		return this.coordinator;
	}
	/**
	 * This method is used to get all the owned projects of a faculty.
	 * @param user The faculty member.
	 */
	public void viewOwnProjects(Faculty user) {
		FacultyProjectManager fpm = FacultyProjectManager.getInstance();
		if (user.getProjects().size()>0) {
			System.out.println("\u001b[21mYour Projects\u001b[0m");
			fpm.viewOwnProjects(user);
		} else {
			System.err.println("You currently have no projects.");
		}
	}
	/**
	 * This method is used to get all the active projects of a faculty.
	 * @param user The faculty member.
	 */
	public void viewActiveProjects(Faculty user) {
		FacultyProjectManager fpm = FacultyProjectManager.getInstance();
		if (user.getActiveProjects()>0) {
			System.out.println("\u001b[21mYour Active Projects\u001b[0m");
			fpm.viewActiveProjects(user);
		} else {
			System.out.println("You currently have no active projects.");
		}
	}

	/**
	 * This method is used to change the title of a faculty project.
	 * @param p The project to be changed.
	 * @param s The new title.
	 */
	public void changeTitle(Project p,String s) {
		FacultyProjectManager fpm = FacultyProjectManager.getInstance();
		fpm.changeTitle(p, s);
	}
	/**
	 * This method is to create a new transfer request.
	 * @param user The faculty member.
	 * @param p The project to be transferred.
	 * @param replacementID The ID of the replacement faculty member.
	 */
	public void transferRequest(Faculty user, Project p, String replacementID) {
		FacultyRequestManager frm = FacultyRequestManager.getInstance();
		frm.addTransferRequest(user, p, replacementID);
	}
	
	
	/** 
	 * This method is used to view history of the user.
	 * @param user The user whose history needs to be viewed.
	 * @param page The page which has the history.
	 */
	public void viewHistory(User user, int page) {
		int pageSize = 5;
	    ArrayList<Request> reqs = user.getHistory();
	    int startIndex = (page - 1) * pageSize;
	    int endIndex = Math.min(startIndex + pageSize, reqs.size());
	    List<Request> currentPage = reqs.subList(startIndex, endIndex);
	    currentPage.forEach((Request)-> Request.printRequest());
	}
}

	