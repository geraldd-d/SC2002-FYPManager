package entities;
import java.util.ArrayList;

public class Faculty extends User{
	public Faculty(String userID, String password, String name, String email){
		super(userID, password, name, email);
	}
	private ArrayList<Project> projects = new ArrayList<Project>();
	public void addProject(Project p) {
		this.projects.add(p);
	}
	public void getProjects() {
		for (Project p: this.projects) {
			System.out.println(p.title);
		}
	}
}
