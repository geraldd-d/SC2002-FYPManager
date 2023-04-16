package entities;

import java.util.ArrayList;

import controllers.HashService;

/**
 * This abstract class is the parent class of all users. It contains the attributes and methods that are common to all users.
 */
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

	
	/** 
	 * @return String
	 */
	// getter
	public String getUserID() {
		return this.userID;
	}
	
	/** 
	 * @return String
	 */
	public String getName() {
		return this.name;
	}
	
	/** 
	 * @return String
	 */
	public String getEmail() {
		return this.email;
	}
	
	/** 
	 * @param password
	 * @return boolean
	 */
	public boolean checkPassword(String password){
		return hs.hashPassword(password, userID).equals(this.hashedPassword);
	}
	
	/** 
	 * @return String
	 */
	public String getPassword() {
		return this.hashedPassword;
	}


	
	/** 
	 * @param userID
	 */
	// setter 
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	/** 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/** 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	public abstract ArrayList<Request> getHistory();
	
}
