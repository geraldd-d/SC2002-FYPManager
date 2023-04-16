package entities;

/**
 * This class is a child class of Request. It is used to create Transfer Requests.
 */
public class TransferRequest extends Request {
	private Faculty replacement;
	private String replacementID;
	public TransferRequest(int requestID, User requestor, User requestee, RequestStatus status, Project project, Faculty replacement) {
		super(requestID, requestor, requestee, status, project);
		this.replacement = replacement;
		this.replacementID = replacement.getUserID();
	}
	
	/** 
	 * @return String
	 */
	public String getReplacementID() {
		return this.replacementID;
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
	
	/** 
	 * @return RequestType
	 */
	@Override
	public RequestType getType() {
		return RequestType.Transfer;
	}
	
	/** 
	 * @return String
	 */
	public String getChanges() {
		return this.replacementID;
	}
}
