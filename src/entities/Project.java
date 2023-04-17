package entities;

import controllers.StudentController;

/*
 * This class represents a FYP of the system.
 */
public class Project {
	private Integer projectID;
	private String title;
	private String supervisorID;
	private String supervisorName;
	private String studentID;
	private ProjectStatus status; 
	public Project() {};
	public Project(String title, String supervisorID, String supervisorName, String studentID, ProjectStatus status, Integer projectID) {
		this.title = title;
		this.supervisorID = supervisorID;
		this.studentID = studentID;
		this.status = status;
		this.projectID = projectID;
		this.supervisorName = supervisorName;
		
	}

	
	/** 
	 * This returns the ID.
	 * @return Integer
	 */
	// getter 
    public Integer getID() {
        return this.projectID;
    }
	
	/** 
	 * This returns the title.
	 * @return String
	 */
	public String getTitle() {
        return this.title;
    }
    
	/** 
	 * This returns the supervisorID.
	 * @return String
	 */
	public String getSupervisorID() {
        return this.supervisorID;
    }
	
	/** 
	 * This returns the supervisorName.
	 * @return String
	 */
	public String getSupervisorName() {
        return this.supervisorName;
    }
	
	/**
	 * This returns the project status.
	 * @return ProjectStatus
	 */
	public ProjectStatus getStatus() {
		return this.status;
	}
	
	/** 
	 * This returns the StudentID.
	 * @return String
	 */
	public String getStudentID(){
		return this.studentID;
	}
	
	/** 
	 * This method allows to set the ID as current ID.
	 * @param id
	 */
	// setter
	public void setID(Integer id) {
        this.projectID = id;
    }
	
	/** 
	 * This method allows to set the title as current title.
	 * @param title
	 */
	public void setTitle(String title) {
        this.title = title;
    }
	
	/** 
	 * This method allows to set the ID as current ID.
	 * @param supervisorID
	 */
	public void setSupervisorID(String supervisorID) {
        this.supervisorID = supervisorID;
    }
	
	/** 
	 * This method allows to set the supervisorName as current supervisorName.
	 * @param supervisorName
	 */
	public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }
	
	/** 
	 * This method allows to set the status as current status.
	 * @param status
	 */
	public void setStatus(ProjectStatus status) {
        this.status = status;
    }
	
	/** 
	 * This method allows to set the studentID as current stduentID.
	 * @param studentID
	 */
	public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

	/**
	 * This method allows us to print project and its details.
	 */
	public void printProject() {
		StudentController sc = StudentController.getInstance();
		Student s = sc.getStudentbyID(this.getStudentID());
		System.out.println("Project ID: "+this.projectID);
		System.out.println("Project Name: "+ this.title);
		System.out.println("Supervisor: "+this.supervisorName);
		System.out.println("Status: "+this.status);
		if (s != null) {
			System.out.println("Student Name: "+s.getName());
		}
		System.out.println();
	}
}
