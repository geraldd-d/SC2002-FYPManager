package entities;

import java.util.ArrayList;
import controllers.*;

public class Student extends User{
	HashService hs = HashService.getInstance();
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
		this.userID = userID;
	}
	public void setName(String name){
		this.name = name;
	}
	public void setEmail(String email){
		this.email = email;
	}
	public void setRegisteredProject(Project registeredProject){
		this.registeredProject = registeredProject;
	}
	public void setHistory(ArrayList<Request> requestHistory){
		this.requestHistory = requestHistory;
	}
	

}
