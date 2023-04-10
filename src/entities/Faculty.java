package entities;
import java.util.ArrayList;

public class Faculty extends User{
	private String supervisorID;
	private String supervisorName;
	private ArrayList<Project> projects = new ArrayList<Project>();
	

	// not sure if it should be in the controller or enitity 
	//private ArrayList<Request> incomingRequests = new ArrayList<Request>();
    //private ArrayList<Request> outgoingRequests = new ArrayList<Request>();

	public Faculty(String userID,String password, String name, String email){
		super(userID, password, name, email);
	}

	public String getSupervisorID( ){
		return this.supervisorID;
	}
	public String getSupervisorName( ){
		return this.supervisorName;
	}
	public ArrayList<Project> getProjects() {
        return projects;
    }
	public void setSupervisorID(String supervisorID) {
        this.supervisorID = supervisorID;
    }
	public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }
	public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;
    }
	public void addProject(Project p) {
		this.projects.add(p);
	}

	
}

