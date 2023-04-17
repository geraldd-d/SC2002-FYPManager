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
	 * This method return the title.
	 * @return String
	 */
	public String getTitle() {
		return this.title;
	}
	/**
	 * This method prints the request and its details
	 */
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
	 * This method return the request type.
	 * @return ReuestType
	 */
	@Override
	public RequestType getType() {
		return RequestType.Title;
	}
	
	/** 
	 * This returns the changed title.
	 * @return The changed title
	 */
	public String getChanges() {
		return this.title;
	}
}
