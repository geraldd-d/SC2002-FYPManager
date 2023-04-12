package entities;

public class TitleRequest extends Request {
	private String title;
	public TitleRequest(User requestor, User requestee, RequestStatus status, Project project, String title) {
		super(requestor, requestee, status, project);
		this.title = title;
	}
}
