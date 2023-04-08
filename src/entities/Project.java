package entities;

import java.util.ArrayList;
import java.util.UUID;

public class Project {
	private String id;
	private String title;
	private String supervisorID;
	private String studentID;
	private String status; 
	private Project() {};
	public Project(String title, String supervisorID, String studentID, String status) {
		this.id = UUID.randomUUID().toString();
		this.title = title;
		this.supervisorID = supervisorID;
		this.studentID = studentID;
		this.status = status;
	}

	// getter 
    public String getID() {
        return this.id;
    }
	public String getTitle() {
        return this.title;
    }
    public String getSupervisorID() {
        return this.supervisorID;
    }
	public String getStatus() {
		return this.status;
	}
	public String getStudentID(){
		return this.studentID;
	}
	
	// setter
	public void setID(String id) {
        this.id = id;
    }
	public void setTitle(String title) {
        this.title = title;
    }
	public void setSupervisor(String supervisorID) {
        this.supervisorID = supervisorID;
    }
	public void setStatus(String status) {
        this.status = status;
    }
	public void setStudent(String studentID) {
        this.studentID = studentID;
    }



}

