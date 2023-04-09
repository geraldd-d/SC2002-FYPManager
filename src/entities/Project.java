package entities;

import java.util.ArrayList;
import java.util.UUID;

public class Project {
	private Integer projectID;
	private String title;
	private String supervisorID;
	private String studentID;
	private String status; 
	private Project() {};
	public Project(String title, String supervisorID, String studentID, String status, Integer projectID) {
		this.title = title;
		this.supervisorID = supervisorID;
		this.studentID = studentID;
		this.status = status;
		this.projectID = projectID;
	}

	// getter 
    public Integer getID() {
        return this.projectID;
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
	public void setID(Integer id) {
        this.projectID = id;
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

