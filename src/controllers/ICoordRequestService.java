package controllers;

import java.util.ArrayList;

import entities.Coordinator;
import entities.Faculty;
import entities.Project;
import entities.Request;
import entities.TransferRequest;

public interface ICoordRequestService {
	public void rejectRequest(Request r);
	public void approveAllocation(Request r);
	public void approveDeregistration(Request r);
	public void approveTransfer(Request r);
	public void viewPending(Coordinator c);
	public ArrayList<Request> getPendingReqs(Faculty user);
	public void saveChanges();
	public void approveTitleChange(Request r);
	public TransferRequest addTransferRequest(Faculty requestor, Coordinator requestee, Project p, Faculty replacement);
}
