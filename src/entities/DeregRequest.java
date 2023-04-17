package entities;

/**
 * This class is used to represent a deregistration request.
 */
public class DeregRequest extends Request {
	public DeregRequest(int requestID, User requestor, User requestee, RequestStatus status, Project project) {
		super(requestID, requestor, requestee, status, project);
	}
	public void printRequest() {
		System.out.println("Deregistration Request");
		System.out.println("----------------------");
		System.out.println("Requestor: "+ this.getRequestor().getName());
		System.out.println("Requestee: "+ this.getRequestee().getName());
		System.out.println("ProjectID: "+ this.getProject().getID());
		System.out.println("Status: "+ this.getStatus());
		System.out.println("Request ID: " + this.getRequestID());
	}
	
	/** 
	 * This method returns the type of request.
	 * @return The type of request
	 */
	@Override
	public RequestType getType() {
		return RequestType.Deregister;
	}
	
	/** 
	 * This method return space.
	 * @return The space between words.
	 */
	public String getChanges() {
		return "";
	}
}
