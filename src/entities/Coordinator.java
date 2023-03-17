package entities;

import java.security.NoSuchAlgorithmException;

public class Coordinator extends User{
	public Coordinator(String userID, String password, String name, String email) throws NoSuchAlgorithmException{
		super(userID, password, name, email);
	}
}
