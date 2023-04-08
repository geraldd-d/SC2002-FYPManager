package entities;

import java.util.ArrayList;

public class Student extends User{
	private String userID;
	private String name;
	private String email;
	private Project registeredProject;
	private ArrayList<Request> requestHistory;

	public Student(String userID, String password, String name, String email){
		super(userID, password, name, email);
	}

	// getter 
	public String getUserID(){
		return this.userID;
	}
	public String getName(){
		return this.name;
	}
	public String getEmail(){
		return this.email;
	}
	public Project getRegisteredProject(){
		return this.registeredProject;
	}
	public ArrayList<Request> getHistory(){
		return this.requestHistory;
	}

	// setter 
	public void setUserID(String userID){
		userID = this.userID;
	}
	public void setName(String name){
		name = this.name;
	}
	public void setEmail(String email){
		email = this.email;
	}
	public void setRegisteredProject(Project registeredProject){
		registeredProject = this.registeredProject;
	}
	public void setHistory(ArrayList<Request> requestHistory){
		requestHistory = this.requestHistory;
	}

}
