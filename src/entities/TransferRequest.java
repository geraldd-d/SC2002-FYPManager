package entities;

/**
 * This class is a child class of Request. It is used to create Transfer Requests.
 */
public class TransferRequest extends Request {
	private String replacementID;
	public TransferRequest(int requestID, User requestor, User requestee, RequestStatus status, Project project, Faculty replacement) {
		super(requestID, requestor, requestee, status, project);
		this.replacementID = replacement.getUserID();
	}
	
	/** 
	 * This method return the replacement supervisor's ID.
	 * @return String
	 */
	public String getReplacementID() {
		return this.replacementID;
	}
	
	/** 
	 * This returns the replcament.
	 * @return Faculty
	 */
	public Faculty getReplacement() {
		return this.getReplacement();
	}
	/**
	 * This method prints the request details
	 */
	public void printRequest() {
		System.out.println("Transfer Request");
		System.out.println("----------------");
		System.out.println("Requestor: "+ this.getRequestor().getName());
		System.out.println("Requestee: "+ this.getRequestee().getName());
		System.out.println("ProjectID: "+ this.getProject().getID());
		System.out.println("Replacement: "+ this.getReplacementID());
		System.out.println("Status: "+ this.getStatus());
		System.out.println("Request ID: " + this.getRequestID());
	}
	
	/** 
	 * This method returns the requestType.
	 * @return RequestType
	 */
	@Override
	public RequestType getType() {
		return RequestType.Transfer;
	}
	
	/** 
	 * This returns the replacement ID.
	 * @return String
	 */
	public String getChanges() {
		return this.replacementID;
	}
}
