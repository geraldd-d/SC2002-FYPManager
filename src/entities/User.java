package entities;
import java.security.NoSuchAlgorithmException;

import controllers.HashService;

public abstract class User {
	HashService hs = HashService.getInstance();
	private String userID;
	private String hashedPassword;
	private String name;
	private String email;
	private byte[] salt;
	
	public User(String userID, String password, String name, String email) throws NoSuchAlgorithmException {
		this.salt = hs.generateSalt();
		this.userID = userID;
		this.name = name;
		this.email = email;
		this.hashedPassword = hs.hashPassword(password,salt);
	}
	public String getuserID() {
		return this.userID;
	}
	public String getname() {
		return this.name;
	}
	public String getEmail() {
		return this.email;
	}
	public boolean checkPassword(String password) throws NoSuchAlgorithmException {
		return hs.hashPassword(password, salt) == this.hashedPassword;
	}
}
