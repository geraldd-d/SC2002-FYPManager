package entities;

public class TransferRequest extends Request {
	private Faculty replacement;
	public TransferRequest(User requestor, User requestee, RequestStatus status, Project project, Faculty replacement) {
		super(requestor, requestee, status, project);
		this.replacement = replacement;
	}
}
