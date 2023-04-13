package entities;

public class TransferRequest extends Request {
	private Faculty replacement;
	private String replacementID;
	public TransferRequest(User requestor, User requestee, RequestStatus status, Project project, Faculty replacement) {
		super(requestor, requestee, status, project);
		this.replacement = replacement;
		this.replacementID = replacement.getSupervisorID();
	}
	public String getReplacementID() {
		return this.getReplacementID();
	}
	public void printRequest() {
		System.out.println("Transfer Request");
		System.out.println("----------------");
		System.out.println("Requestor: "+ this.getRequestor());
		System.out.println("Requestee: "+ this.getRequestor());
		System.out.println("ProjectID: "+ this.getProject().getID());
		System.out.println("Replacement: "+ this.getReplacementID());
		System.out.println("Status: "+ this.getStatus());
	}
}
