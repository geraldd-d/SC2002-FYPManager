package entities;

public class DeregRequest extends Request {
	public DeregRequest(User requestor, User requestee, RequestStatus status, Project project) {
		super(requestor, requestee, status, project);
	}
}