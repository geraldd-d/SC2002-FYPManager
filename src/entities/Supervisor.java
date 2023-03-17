package entities;

import java.security.NoSuchAlgorithmException;

public class Supervisor extends User{
	public Supervisor(String userID, String password, String name, String email) throws NoSuchAlgorithmException{
		super(userID, password, name, email);
	}
}
