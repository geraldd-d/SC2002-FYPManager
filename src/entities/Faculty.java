package entities;
import java.util.ArrayList;

public class Faculty extends User{
	private ArrayList<Project> projects = new ArrayList<Project>();
	private ArrayList<Request> requestHistory = new ArrayList<Request>();
	private ArrayList<Request> requestInbox = new ArrayList<Request>();

	public Faculty(String userID,String password, String name, String email){
		super(userID, password, name, email);
	}
	public int getActiveProjects() {
		int num = 0;
		for (Project p : this.projects) {
			if (p.getStatus().equals("Allocated")) {
				num++;
			}
		}
		return num;
	}
	public ArrayList<Project> getProjects() {
        return projects;
    }
	public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;
    }
	public void addProject(Project p) {
		this.projects.add(p);
	}
	public void addHistory(Request r){
		this.requestHistory.add(r);
	}
	public void addInbox(Request r){
		this.requestInbox.add(r);
	}
	public ArrayList<Request> getInbox() {
		return this.requestInbox;
	}
	public ArrayList<Request> getHistory() {
		return this.requestHistory;
	}
}

