package entities;

public class DeregRequest extends Request {
	public DeregRequest(User requestor, User requestee, RequestStatus status, Project project) {
		super(requestor, requestee, status, project);
	}
	public void printRequest() {
		System.out.println("Deregistration Request");
		System.out.println("----------------------");
		System.out.println("Requestor: "+ this.getRequestor());
		System.out.println("Requestee: "+ this.getRequestor());
		System.out.println("ProjectID: "+ this.getProject().getID());
		System.out.println("Status: "+ this.getStatus());
	}
}