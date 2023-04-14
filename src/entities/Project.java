package entities;

import java.util.*;

public class Project {
	private Integer projectID;
	private String title;
	private String supervisorID;
	private String supervisorName;
	private String studentID;
	private String status; 
	public Project() {};
	public Project(String title, String supervisorID, String supervisorName, String studentID, String status, Integer projectID) {
		this.title = title;
		this.supervisorID = supervisorID;
		this.studentID = studentID;
		this.status = status;
		this.projectID = projectID;
		this.supervisorName = supervisorName;
		//this.code = "P" + String.format("%03d", projectCount);
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
	public String getSupervisorName() {
        return this.supervisorName;
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
	public void setSupervisorID(String supervisorID) {
        this.supervisorID = supervisorID;
    }
	public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }
	public void setStatus(String status) {
        this.status = status;
    }
	public void setStudent(String studentID) {
        this.studentID = studentID;
    }
	public void printAvailableProject() {
		System.out.println("Project ID: "+this.projectID);
		System.out.println("Supervisor: "+this.supervisorName);
		System.out.println("Title: "+this.title);
		System.out.println("Status: "+this.status);
		System.out.println();
	}
	public void printProject() {
		System.out.println("Project ID: "+this.projectID);
		System.out.println("Project Name: "+ this.title);
		System.out.println("Supervisor: "+this.supervisorName);
		System.out.println();
	}
	public void printActiveProject(){
		System.out.println("Project ID: "+this.projectID);
		System.out.println("Project Name: "+ this.title);
		System.out.println("Status: "+this.status);
		System.out.println();
	}

}

