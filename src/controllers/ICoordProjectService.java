package controllers;

import entities.Project;

public interface ICoordProjectService {
	public void lockProjects(String id);
	public void unlockProjects(String id);
	public void allocateProject(String studentID, Project p);
	public void deregisterProject(String studentID, Project p );
	public void transferProject(String currentID, String replacementID, String replacementName, Project p);
}
