package entities;

import java.security.NoSuchAlgorithmException;

public class Student extends User{
	public Student(String userID, String password, String name, String email) throws NoSuchAlgorithmException{
		super(userID, password, name, email);
	}
}
