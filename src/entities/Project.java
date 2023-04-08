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
	public ArrayList<Student> getStudent(){
		return this.students;
	}
	
}

