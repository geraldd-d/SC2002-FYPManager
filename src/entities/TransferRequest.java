package entities;

public class TransferRequest extends Request {
	private RequestType type = RequestType.Transfer;
	private Faculty replacement;
	private String replacementID;
	public TransferRequest(String id, User requestor, User requestee, RequestStatus status, Project project, Faculty replacement) {
		super(id, requestor, requestee, status, project);
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
		// TODO Auto-generated method stub
		return this.type;
	}
	@Override
	public String getChanges() {
		return this.replacementID;
	}
}
