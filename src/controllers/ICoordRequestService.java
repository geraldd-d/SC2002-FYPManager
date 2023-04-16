package controllers;

import entities.Coordinator;
import entities.Faculty;
import entities.Request;

public interface ICoordRequestService {
	public void rejectRequest(Request r);
	public void approveAllocation(Request r);
	public void approveDeregistration(Request r);
	public void approveTransfer(Request r);
	public void viewPending(Coordinator c);
}
