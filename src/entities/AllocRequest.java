package entities;

/**
 * This class is a child class of Request. It is used to create Allocation Requests.
 */
public class AllocRequest extends Request {
	public AllocRequest(int requestID, User requestor, User requestee, RequestStatus status, Project project) {
		super(requestID, requestor, requestee, status, project);
	}
	public void printRequest() {
		System.out.println("Allocation Request");
		System.out.println("------------------");
		System.out.println("Requestor: "+ this.getRequestor().getName());
		System.out.println("Requestee: "+ this.getRequestee().getName());
		System.out.println("ProjectID: "+ this.getProject().getID());
		System.out.println("Status: "+ this.getStatus());
		System.out.println("Request ID: " + this.getRequestID());
		System.out.println();
	}
	
	/** 
	 * @return RequestType
	 */
	@Override
	public RequestType getType() {
		return RequestType.Allocation;
	}
	
	/** 
	 * @return String
	 */
	public String getChanges() {
		return "";
	}
}
