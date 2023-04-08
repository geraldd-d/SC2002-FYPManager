package entities;

import java.util.ArrayList;

public class Student extends User{
	private String userID;
	private String name;
	private String password;
	private String email;
	private Project registeredProject;
	private ArrayList<Request> requestHistory;

	public Student(String userID, String name, String email){
		super(userID, "password",name, email);
		this.password = "password";
	}	

	public Student(String userID, String password, String name, String email){
		super(userID, password, name, email);
		this.password = password;
	}

	public String getUserID(){
		return this.userID;
	}
	public String getName(){
		return this.name;
	}
	public String getPassword(){
		return this.password;
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

}
