package entities;

/**
 * This class is a child class of Request. It is used to create Title Change Requests.
 */
public class TitleRequest extends Request {
	private String title;
	public TitleRequest(int requestID, User requestor, User requestee, RequestStatus status, Project project, String title) {
		super(requestID, requestor, requestee, status, project);
		this.title = title;
	}
	
	/** 
	 * @return String
	 */
	public String getTitle() {
		return this.title;
	}
	public void printRequest() {
		System.out.println("Title Change Request");
		System.out.println("--------------------");
		System.out.println("Requestor: "+ this.getRequestor().getName());
		System.out.println("Requestee: "+ this.getRequestee().getName());
		System.out.println("ProjectID: "+ this.getProject().getID());
		System.out.println("Current Title: " + this.getProject().getTitle());
		System.out.println("New Title: "+ this.getTitle());
		System.out.println("Status: "+ this.getStatus());
		System.out.println("Request ID: " + this.getRequestID());
	}
	
	/** 
	 * @return RequestType
	 */
	@Override
	public RequestType getType() {
		return RequestType.Title;
	}
	
	/** 
	 * @return String
	 */
	public String getChanges() {
		return this.title;
	}
}
