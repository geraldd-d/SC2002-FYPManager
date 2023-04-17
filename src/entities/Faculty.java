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
	 * The method return the active projects 
	 * @return The number of active projects.
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
	 * The method calls the projects
	 * @return The list of Projects.
	 */
	public ArrayList<Project> getProjects() {
        return projects;
    }
	
	/** 
	 * This method sets the current project to the above mentioned project.
	 * @param projects
	 */
	public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;
    }
	
	/** 
	 * The method add projects.
	 * @param p The project which is added.
	 */
	public void addProject(Project p) {
		this.projects.add(p);
	}
	
	/** 
	 * The method updates history with new requests
	 * @param r The request which are added to history
	 */
	public void addHistory(Request r){
		this.requestHistory.add(r);
	}
	
	/** 
	 * The method which adds requests to inbox.
	 * @param r The request which is added to the inbox.
	 */
	public void addInbox(Request r){
		this.requestInbox.add(r);
	}
	
	/** 
	 * The method calls the inbox
	 * @return the list of requests in the inbox.
	 */
	public ArrayList<Request> getInbox() {
		return this.requestInbox;
	}
	
	/** 
	 * This method calls the requests in history
	 * @return The list of reuquests in history.
	 */
	public ArrayList<Request> getHistory() {
		return this.requestHistory;
	}
	
}

