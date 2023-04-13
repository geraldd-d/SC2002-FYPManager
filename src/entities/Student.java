package entities;

import java.util.ArrayList;
import controllers.*;

public class Student extends User{
	HashService hs = HashService.getInstance();
	private Project registeredProject;
	private ArrayList<Request> requestHistory;
	private boolean isAllocated = false;

	public Student(String userID, String password, String name, String email){
		super(userID, password, name, email);
		this.requestHistory = new ArrayList<Request>();
		//this.isAllocated = isAllocated;
	}

	// getter 
	public Project getRegisteredProject(){
		return this.registeredProject;
	}
	public ArrayList<Request> getHistory(){
		return this.requestHistory;
	}
	public boolean getisAllocated(){
		return this.isAllocated;
	}

	// setter 
	public void setRegisteredProject(Project registeredProject){
		this.registeredProject = registeredProject;
	}
	public void setHistory(ArrayList<Request> requestHistory){
		this.requestHistory = requestHistory;
	}
	public void addHistory(Request r){
		this.requestHistory.add(r);
	}
	public void setIsAllocated(boolean isAllocated){
		this.isAllocated = isAllocated;
	}
}
