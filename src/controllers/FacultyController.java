package controllers;

import java.util.ArrayList;
import java.util.HashMap;

import entities.*;


public class FacultyController {
	private static FacultyController fc = null;
	private static HashMap<String,User> facultyData;
	private HashMap<String,Faculty> facultyNames;
	private Coordinator coordinator;
	private FacultyController(HashMap<String,User> facultyData) {
		FacultyController.facultyData = facultyData;
		this.facultyNames = getFacultyNames();
	};
	
	/** 
	 * @param facultyList
	 * @return FacultyController
	 */
	public static FacultyController getInstance(HashMap<String,User> facultyList) {
		if (fc == null|| !facultyList.equals(facultyData)) {
			fc = new FacultyController(facultyList);
		}
		return fc;
	}
	
	/** 
	 * @return FacultyController
	 */
	public static FacultyController getInstance() {
		return fc;
	}
	
	/** 
	 * @param id
	 * @return Faculty
	 */
	public Faculty getFacultybyID(String id) {
		return (Faculty) facultyData.get(id);
	}
	
	/** 
	 * @param name
	 * @return Faculty
	 */
	public Faculty getFacultybyName(String name) {
		return (Faculty) facultyNames.get(name);
	}
	
	/** 
	 * @return HashMap<String, Faculty>
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
	 * @return Coordinator
	 */
	public Coordinator getCoord() {
		return this.coordinator;
	}
	
	/** 
	 * @param user
	 */
	public void viewOwnProjects(Faculty user) {
		FacultyProjectManager fpm = FacultyProjectManager.getInstance();
		if (user.getProjects().size()>0) {
			fpm.viewOwnProjects(user);
		} else {
			System.out.println("You currently have no projects.");
		}
	}
	
	/** 
	 * @param user
	 */
	public void viewActiveProjects(Faculty user) {
		FacultyProjectManager fpm = FacultyProjectManager.getInstance();
		if (user.getProjects().size()>0) {
			fpm.viewActiveProjects(user);
		} else {
			System.out.println("You currently have no projects.");
		}
	}

	
	/** 
	 * @param p
	 * @param s
	 */
	public void changeTitle(Project p,String s) {
		FacultyProjectManager fpm = FacultyProjectManager.getInstance();
		fpm.changeTitle(p, s);
	}
	
	/** 
	 * @param user
	 * @param p
	 * @param replacementID
	 */
	public void transferRequest(Faculty user, Project p, String replacementID) {
		FacultyRequestManager frm = FacultyRequestManager.getInstance();
		frm.addTransferRequest(user, p, replacementID);
	}
	
	/** 
	 * @param user
	 * @return ArrayList<Request>
	 */
	public ArrayList<Request> getPendingRequests(Faculty user) {
		FacultyRequestManager frm = FacultyRequestManager.getInstance();
		return frm.getPendingReqs(user);
	}
}

	