package entities;

import java.security.NoSuchAlgorithmException;

public class Faculty extends User{
	public Faculty(String userID, String password, String name, String email) throws NoSuchAlgorithmException{
		super(userID, password, name, email);
	}
}
