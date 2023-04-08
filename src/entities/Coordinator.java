package entities;


public class Coordinator extends Faculty{
	private String SupervisorID;
	public Coordinator(String userID,String password, String name, String email){
		super(userID, password, name, email);
		//this.SupervisorID = SupervisorID;
	}
	public String getSupervisorID(){
		return SupervisorID;
	}
	public void setSupervisorID(String SupervisorID){
		this.SupervisorID = SupervisorID;
	}
}
