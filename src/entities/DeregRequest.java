package entities;

public class DeregRequest extends Request {
	private RequestType type = RequestType.Deregister;

	public DeregRequest(String id, User requestor, User requestee, RequestStatus status, Project project) {
		super(id, requestor, requestee, status, project);
	}
	public void printRequest() {
		System.out.println("Deregistration Request");
		System.out.println("----------------------");
		System.out.println("Requestor: "+ this.getRequestor().getName());
		System.out.println("Requestee: "+ this.getRequestee().getName());
		System.out.println("ProjectID: "+ this.getProject().getID());
		System.out.println("Status: "+ this.getStatus());
	}
	@Override
	public RequestType getType() {
		// TODO Auto-generated method stub
		return this.type;
	}
	@Override
	public String getChanges() {
		return "";
	}
}