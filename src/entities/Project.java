package entities;

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
		//this.code = "P" + String.format("%03d", projectCount);
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

