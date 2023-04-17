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
	 * @return Integer
	 */
	// getter 
    public Integer getID() {
        return this.projectID;
    }
	
	/** 
	 * @return String
	 */
	public String getTitle() {
        return this.title;
    }
    
	/** 
	 * @return String
	 */
	public String getSupervisorID() {
        return this.supervisorID;
    }
	
	/** 
	 * @return String
	 */
	public String getSupervisorName() {
        return this.supervisorName;
    }
	
	/** 
	 * @return ProjectStatus
	 */
	public ProjectStatus getStatus() {
		return this.status;
	}
	
	/** 
	 * @return String
	 */
	public String getStudentID(){
		return this.studentID;
	}
	
	/** 
	 * @param id
	 */
	// setter
	public void setID(Integer id) {
        this.projectID = id;
    }
	
	/** 
	 * @param title
	 */
	public void setTitle(String title) {
        this.title = title;
    }
	
	/** 
	 * @param supervisorID
	 */
	public void setSupervisorID(String supervisorID) {
        this.supervisorID = supervisorID;
    }
	
	/** 
	 * @param supervisorName
	 */
	public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }
	
	/** 
	 * @param status
	 */
	public void setStatus(ProjectStatus status) {
        this.status = status;
    }
	
	/** 
	 * @param studentID
	 */
	public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

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
