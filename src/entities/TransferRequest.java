package entities;

public class TransferRequest extends Request {
	private Faculty replacement;
	private String replacementID;
	public TransferRequest(int requestID, User requestor, User requestee, RequestStatus status, Project project, Faculty replacement) {
		super(requestID, requestor, requestee, status, project);
		this.replacement = replacement;
		this.replacementID = replacement.getUserID();
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
	@Override
	public RequestType getType() {
		return RequestType.Transfer;
	}
	public String getChanges() {
		return this.replacementID;
	}
}
