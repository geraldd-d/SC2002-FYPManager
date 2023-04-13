package entities;

public class TitleRequest extends Request {
	private String title;
	public TitleRequest(User requestor, User requestee, RequestStatus status, Project project, String title) {
		super(requestor, requestee, status, project);
		this.title = title;
	}
	public String getTitle() {
		return this.title;
	}
	public void printRequest() {
		System.out.println("Title Change Request");
		System.out.println("--------------------");
		System.out.println("Requestor: "+ this.getRequestor());
		System.out.println("Requestee: "+ this.getRequestor());
		System.out.println("ProjectID: "+ this.getProject().getID());
		System.out.println("New Title: "+ this.getTitle());
		System.out.println("Status: "+ this.getStatus());
	}
}
