package entities;

import java.util.ArrayList;
import java.util.UUID;

public class Project {
	private String id;
	private String title;
	private Faculty supervisor;
	private ArrayList<Student> students;
	private boolean isAllocated; 
	private Project() {};
	public Project(String title, Faculty supervisor) {
		this.id = UUID.randomUUID().toString();
		this.title = title;
		this.supervisor = supervisor;
		this.students = new ArrayList<Student>();
		this.isAllocated = false;
	}

	// getter 
    public String getID() {
        return this.id;
    }
	public String getTitle() {
        return this.title;
    }
    public Faculty getSupervisor() {
        return this.supervisor;
    }
	public boolean getisAllocated() {
		return isAllocated;
	}
	public ArrayList<Student> getStudents(){
		return this.students;
	}
	
	// setter
	public void setID(String id) {
        id = this.id;
    }
	public void setTitle(String title) {
        title = this.title;
    }
	public void setSupervisor(Faculty supervisor) {
        supervisor = this.supervisor;
    }
	public void setisAllocated(Boolean isAllocated) {
        isAllocated = this.isAllocated;
    }
	public void setStudents(ArrayList<Student> students) {
        students = this.students;
    }



}

