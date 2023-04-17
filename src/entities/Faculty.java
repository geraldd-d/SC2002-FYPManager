package entities;
import java.util.ArrayList;

/**
 * This class represents a faculty member.
 */
public class Faculty extends User{
	private ArrayList<Project> projects = new ArrayList<Project>();
	private ArrayList<Request> requestHistory = new ArrayList<Request>();
	private ArrayList<Request> requestInbox = new ArrayList<Request>();

	public Faculty(String userID,String password, String name, String email){
		super(userID, password, name, email);
	}
	
	/** 
	 * @return Integer
	 */
	public Integer getActiveProjects() {
		int num = 0;
		for (Project p : this.projects) {
			if (p.getStatus().equals(ProjectStatus.Allocated)) {
				num++;
			}
		}
		return num;
	}
	
	/** 
	 * @return ArrayList<Project>
	 */
	public ArrayList<Project> getProjects() {
        return projects;
    }
	
	/** 
	 * @param projects
	 */
	public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;
    }
	
	/** 
	 * @param p
	 */
	public void addProject(Project p) {
		this.projects.add(p);
	}
	
	/** 
	 * @param r
	 */
	public void addHistory(Request r){
		this.requestHistory.add(r);
	}
	
	/** 
	 * @param r
	 */
	public void addInbox(Request r){
		this.requestInbox.add(r);
	}
	
	/** 
	 * @return ArrayList<Request>
	 */
	public ArrayList<Request> getInbox() {
		return this.requestInbox;
	}
	
	/** 
	 * @return ArrayList<Request>
	 */
	public ArrayList<Request> getHistory() {
		return this.requestHistory;
	}
	
}

