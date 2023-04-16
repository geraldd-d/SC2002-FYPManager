package controllers;

import entities.Project;
import entities.Student;

public interface IStudentRequestManager {
	public void saveChanges();
	public void viewHistory(Student user, int page);
	void addAllocationRequest(Student s, Project p);
	void addDeregistrationRequest(Student s, Project p);
	void addTitleRequest(Student s, Project p, String title);
}
