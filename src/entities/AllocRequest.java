package entities;

public class AllocRequest extends Request {
	public AllocRequest(User requestor, User requestee, RequestStatus status, Project project) {
		super(requestor, requestee, status, project);
	}
}
