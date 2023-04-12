package entities;

import java.util.ArrayList;

public class Coordinator extends Faculty{
	private ArrayList<Request> requestInbox = new ArrayList<Request>();
	public Coordinator(String userID,String password, String name, String email){
		super(userID, password, name, email);
		//this.SupervisorID = SupervisorID;
	}
	public void addInbox(Request r) {
		this.requestInbox.add(r);
	}
}
