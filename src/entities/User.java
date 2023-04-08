package entities;

import controllers.HashService;

public abstract class User {
	HashService hs = HashService.getInstance();
	private String userID;
	private String hashedPassword;
	private String name;
	private String email;
	private byte[] salt;
	
	public User(String userID, String password, String name, String email){
		this.salt = hs.generateSalt();
		this.userID = userID;
		this.name = name;
		this.email = email;
		this.hashedPassword = hs.hashPassword(password,salt);
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
		return hs.hashPassword(password, salt).equals(this.hashedPassword);
	}

	// setter 
	public void setUserID(String userID) {
		userID = this.userID;
	}
	public void setName(String name) {
		name = this.name;
	}
	public void setEmail(String email) {
		email = this.email;
	}

}
