package controllers;

import java.util.ArrayList;

import entities.Coordinator;
import entities.Faculty;
import entities.Request;


public interface ICoordRequestManager {
	public void rejectRequest(Request r);
	public void approveAllocation(Request r);
	public void approveDeregistration(Request r);
	public void approveTransfer(Request r);
	public void viewPending(Coordinator c);
	public ArrayList<Request> getPendingReqs(Faculty user);
	public void saveChanges();
	public void approveTitleChange(Request r);
}
