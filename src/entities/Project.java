package entities;

import java.util.ArrayList;

public class Project {
	public String title;
	public Faculty supervisor;
	public ArrayList<Student> students;
	public Project() {};
	public Project(String title, Faculty supervisor) {
		this.title = title;
		this.supervisor = supervisor;
		this.students = new ArrayList<Student>();
	}
}
