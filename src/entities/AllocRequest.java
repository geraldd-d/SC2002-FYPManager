package entities;

public class AllocRequest extends Request {
	private RequestType type = RequestType.Allocation;
	public AllocRequest(String id, User requestor, User requestee, RequestStatus status, Project project) {
		super(id, requestor, requestee, status, project);
		
	}
	public void printRequest() {
		System.out.println("Allocation Request");
		System.out.println("------------------");
		System.out.println("Requestor: "+ this.getRequestor().getName());
		System.out.println("Requestee: "+ this.getRequestee().getName());
		System.out.println("ProjectID: "+ this.getProject().getID());
		System.out.println("Status: "+ this.getStatus());
		System.out.println();
	}
	@Override
	public RequestType getType() {
		return this.type;
	}
	@Override
	public String getChanges() {
		return "";
	}
}
