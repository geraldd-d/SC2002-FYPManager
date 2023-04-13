package entities;

import java.util.ArrayList;

import controllers.HashService;

public abstract class User {
	HashService hs = HashService.getInstance();
	private String userID;
	private String hashedPassword;
	private String name;
	private String email;
	
	public User(String userID, String password, String name, String email){
		this.hashedPassword = password;
		this.userID = userID;
		this.name = name;
		this.email = email;
	}

	// getter
	public String getUserID() {
		return this.userID;
	}
	public String getName() {
		return this.name;
	}
	public String getEmail() {
		return this.email;
	}
	public boolean checkPassword(String password){
		return hs.hashPassword(password, userID).equals(this.hashedPassword);
	}
	public String getPassword() {
		return this.hashedPassword;
	}


	// setter 
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public abstract ArrayList<Request> getHistory();
	
}
