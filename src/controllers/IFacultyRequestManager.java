package controllers;

import java.util.ArrayList;

import entities.Faculty;
import entities.Project;
import entities.Request;
import entities.TitleRequest;
import entities.User;

public interface IFacultyRequestManager {
	public void addTransferRequest(Faculty f, Project p, String replacement);
	public void viewHistory(Faculty user, int page);
	public void viewInbox(Faculty user, int page);
	public void viewPending(Faculty user);
	public ArrayList<Request> getPendingReqs(Faculty user);
	public void saveChanges();
	public void approveTitleChange(Request r);
	public void rejectRequest(TitleRequest r);
}
