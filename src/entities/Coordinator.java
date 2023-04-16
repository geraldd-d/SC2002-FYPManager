package entities;

import java.util.ArrayList;

/**
 * This class represents a course coordinator.
 */
public class Coordinator extends Faculty{
	public Coordinator(String userID,String password, String name, String email){
		super(userID, password, name, email);
		//this.SupervisorID = SupervisorID;
	}
	
}
