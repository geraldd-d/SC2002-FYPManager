package controllers;

import java.util.ArrayList;

import entities.Coordinator;
import entities.Faculty;
import entities.Project;
import entities.Request;
import entities.TransferRequest;

public interface IFacultyRequestService {
	public void viewInbox(Faculty user, int page);
	public void viewHistory(Faculty user, int page);
	public ArrayList<Request> getPendingReqs(Faculty user);
	public void viewPending(Faculty user, int page);
	public void saveChanges();
	public void approveTitleChange(Request r);
	public TransferRequest addTransferRequest(Faculty requestor, Coordinator requestee, Project p, Faculty replacement);
	public void rejectRequest(Request r);
}
