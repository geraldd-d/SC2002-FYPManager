package entities;

public class TitleRequest extends Request {
	private String title;
	private RequestType type = RequestType.Title;

	public TitleRequest(String id, User requestor, User requestee, RequestStatus status, Project project, String title) {
		super(id, requestor, requestee, status, project);
		this.title = title;
	}
	public String getTitle() {
		return this.title;
	}
	public void printRequest() {
		System.out.println("Title Change Request");
		System.out.println("--------------------");
		System.out.println("Requestor: "+ this.getRequestor().getName());
		System.out.println("Requestee: "+ this.getRequestee().getName());
		System.out.println("ProjectID: "+ this.getProject().getID());
		System.out.println("New Title: "+ this.getTitle());
		System.out.println("Status: "+ this.getStatus());
	}
	@Override
	public RequestType getType() {
		// TODO Auto-generated method stub
		return this.type;
	}
	@Override
	public String getChanges() {
		return this.title;
	}
}
