package entities;

import java.util.ArrayList;

/**
 * This class represents a student in the FYPManager.
 */
public class Student extends User{
	private Project registeredProject;
	private ArrayList<Request> requestHistory;

	public Student(String userID, String password, String name, String email){
		super(userID, password, name, email);
		this.requestHistory = new ArrayList<Request>();
		//this.isAllocated = isAllocated;
	}
	/** 
	 * This method return the registered project.
	 * @return Project
	 */
	// getter 
	public Project getRegisteredProject(){
		return this.registeredProject;
	}
	
	/** 
	 *  This method return the history.
	 * @return ArrayList<Request>
	 */
	public ArrayList<Request> getHistory(){
		return this.requestHistory;
	}
	
	/** 
	 *  This method return the allocation status.
	 * @return boolean
	 */
	public boolean getisAllocated(){
		return this.registeredProject != null;
	}

	
	/** 
	 * This method sets the registered project as the current registered project.
	 * @param registeredProject
	 */
	// setter 
	public void setRegisteredProject(Project registeredProject){
		this.registeredProject = registeredProject;
	}
	
	/** 
	 * This method sets the history as the current history.
	 * @param requestHistory
	 */
	public void setHistory(ArrayList<Request> requestHistory){
		this.requestHistory = requestHistory;
	}
	
	/** 
	 * This method adds the current history to request history.
	 * @param r
	 */
	public void addHistory(Request r){
		this.requestHistory.add(r);
	}
}
