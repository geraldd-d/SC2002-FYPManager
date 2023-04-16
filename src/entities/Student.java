package entities;

import java.util.ArrayList;
import controllers.*;

public class Student extends User{
	HashService hs = HashService.getInstance();
	private Project registeredProject;
	private ArrayList<Request> requestHistory;

	public Student(String userID, String password, String name, String email){
		super(userID, password, name, email);
		this.requestHistory = new ArrayList<Request>();
		//this.isAllocated = isAllocated;
	}

	
	/** 
	 * @return Project
	 */
	// getter 
	public Project getRegisteredProject(){
		return this.registeredProject;
	}
	
	/** 
	 * @return ArrayList<Request>
	 */
	public ArrayList<Request> getHistory(){
		return this.requestHistory;
	}
	
	/** 
	 * @return boolean
	 */
	public boolean getisAllocated(){
		return this.registeredProject != null;
	}

	
	/** 
	 * @param registeredProject
	 */
	// setter 
	public void setRegisteredProject(Project registeredProject){
		this.registeredProject = registeredProject;
	}
	
	/** 
	 * @param requestHistory
	 */
	public void setHistory(ArrayList<Request> requestHistory){
		this.requestHistory = requestHistory;
	}
	
	/** 
	 * @param r
	 */
	public void addHistory(Request r){
		this.requestHistory.add(r);
	}
}
