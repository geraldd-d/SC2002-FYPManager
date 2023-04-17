package controllers;

import java.util.ArrayList;

import entities.Faculty;
import entities.Project;
import entities.ProjectStatus;
import entities.Student;

/**
 * This class is the manager for the coordinator projects. It implements the ICoordProjectManager interface.
 */
public class CoordProjectManager implements ICoordProjectManager{
	private static CoordProjectManager cpm = null;
	private ArrayList<Project> projects;
	private CoordProjectManager(ArrayList<Project> projects) {
		this.projects = projects;
	};

	/** 
	 * This method is used to get the instance of the CoordProjectManager class. It is a singleton class.
	 * @param projects The list of project in the system.
	 * @return The instance of the CoordProjectManager class.
	 */
	public static CoordProjectManager getInstance(ArrayList<Project> projects) {
		if (cpm == null) {
			cpm = new CoordProjectManager(projects);
		}
		return cpm;
	}
	
	/** 
	 * This method is used to get the instance of the CoordProjectManager class. It is a singleton class.
	 * @return The instance of the CoordProjectManager class.
	 */
	public static CoordProjectManager getInstance() {
		return cpm;
	}
	
	/** 
	 * This method is used to allocate projects to the students. 
	 * @param student The student who has requested for the project to be allocated.
	 * @param p The project that is requested to be allocated.
	 */
	@Override
	public void allocateProject(Student student, Project p) {
		FacultyController fc = FacultyController.getInstance();
		p.setStatus(ProjectStatus.Allocated);
		p.setStudentID(student.getUserID());
		student.setRegisteredProject(p);
		setUnavailable(fc.getFacultybyID(p.getSupervisorID()));
	}
	
	
	/** 
	 * This method is used to deregister students from the registered projects
	 * @param student The student who has requested to deregister the project.
	 * @param p The project which is deregistered. 
	 */
	@Override
	public void deregisterProject(Student student, Project p) {
		FacultyController fc = FacultyController.getInstance();
		p.setStatus(ProjectStatus.Available);
		p.setStudentID("");
		student.setRegisteredProject(null);
		setAvailable(fc.getFacultybyID(p.getSupervisorID()));
	}
	
	/** 
	 * This method is used to transfer a student with registered projects to replacement supervisor.
	 * @param current The current supervisor of the project and student who wants to transfer the student.
	 * @param replacementID The replacement supervisor's ID who would replace the current supervisor.
	 * @param replacementName The replacement supervisor's name who would replace the current supervisor.
	 * @param p The project which is going to be transferred.
	 */
	@Override
	public void transferProject(Faculty current, String replacementID, String replacementName, Project p) {
		FacultyController fc = FacultyController.getInstance();
		Faculty replacement = fc.getFacultybyID(replacementID);
		p.setSupervisorID(replacementID);
		p.setSupervisorName(replacement.getName());
		replacement.addProject(p);
		ArrayList<Project> projects = current.getProjects();
		projects.remove(p);
		current.setProjects(projects);
		setAvailable(current);
		setUnavailable(replacement);
	}
	
	/** 
	 * This method is used to add/create projects by coordinator/supervisor.
	 * @param user The supervisor who is going to create/add the project.
	 * @param title The title of the new project created/added. 
	 */
	@Override
	public void addProject(Faculty user, String title) {
		ProjectsController pc = ProjectsController.getInstance();
		ProjectStatus status;
		if (user.getActiveProjects() < 2) {
			status = ProjectStatus.Available;
		} else {
			status = ProjectStatus.Unavailable;
		}
		Project p = new Project(title, user.getUserID(), user.getName(), "", status, pc.getNewID());
		user.addProject(p);
		projects.add(p);
	}

	
	/** 
	 * This method is used to view the user's own projects.
	 * @param user The user whose projects are going to be viewed. 
	 */
	@Override
	public void viewOwnProjects(Faculty user) {
		ArrayList<Project>projects = user.getProjects();
		projects.forEach((p)-> p.printProject());
	}

	
	/** 
	 * This method is used to view the user's active projects.
	 * @param user The user whose active projects are going to be viewed.
	 */
	@Override
	public void viewActiveProjects(Faculty user) {
		ArrayList<Project>projects = user.getProjects();
		projects.forEach((p)-> p.printProject());
	}

	
	/** 
	 * This method is used to change the title of a particular project under the supervisor/coordinator.
	 * @param p The project whose title is going to be changed. 
	 * @param t The new title of the project.
	 */
	@Override
	public void changeTitle(Project p, String t) {
		p.setTitle(t);
	}

	
	/** 
	 * This method is used to get projects by their ID. 
	 * @param projectID The projectID which is used to get the project.
	 * @return Projec The project according to it's projectID.
	 */
	@Override
	public Project getProjectByID(Integer projectID) {
		for (Project project : projects) {
			if (project.getID().equals(projectID)) {
				return project;
			}
		}
		return null;
	}
	/** 
	* This method is used to saveChanges in a project. 
	*/

	@Override
	public void saveChanges() {
		ProjectsController pc = ProjectsController.getInstance();
	    pc.updateProjects(projects);
	}
	
	/** 
	 * This method is used to bring a list of available projects.
	 * @return ArrayList<Project> The list of projects which are available.
	 */
	@Override
	public ArrayList<Project> getAllAvailableProjects() {
        ArrayList<Project> availableProjects = new ArrayList<Project>();
        for (Project project : projects) {
            if (project.getStatus().equals(ProjectStatus.Available)) {
                availableProjects.add(project);
            }
        }
        return availableProjects;
    }
	
	/** 
	 * This method is used to bring a list of unavailable projects.
	 * @return ArrayList<Project> The list of projects which are unavailable
	 */
	@Override
	public ArrayList<Project> getAllUnavailableProjects() {
        ArrayList<Project> unavailableProjects = new ArrayList<Project>();
        for (Project project : projects) {
            if (project.getStatus().equals(ProjectStatus.Unavailable)) {
                unavailableProjects.add(project);
            }
        }
        return unavailableProjects;
    }
	
	/** 
	 * This method is used to bring a list of reserved projects.
	 * @return ArrayList<Project> The list of projects which are reserved.
	 */
	@Override
	public ArrayList<Project> getAllReservedProjects() {
        ArrayList<Project> reservedProjects = new ArrayList<Project>();
        for (Project project : projects) {
            if (project.getStatus().equals(ProjectStatus.Reserved)) {
            	reservedProjects.add(project);
            }
        }
        return reservedProjects;
    }
	
	/** 
	 * This method is used to bring a list of allocated projects.
	 * @return ArrayList<Project>  The list of projects which are allocated.
	 */
	@Override
	public ArrayList<Project> getAllAllocatedProjects() {
        ArrayList<Project> allocatedProjects = new ArrayList<Project>();
        for (Project project : projects) {
            if (project.getStatus().equals(ProjectStatus.Allocated)) {
            	allocatedProjects.add(project);
            }
        }
        return allocatedProjects;
    }
	
	
	/** 
	 * This method is used to set projects as unavailable.
	 * @param user The Supervisor/Coordinator whose projects are going to be available. 
	 */
	protected void setUnavailable(Faculty user) {
		ArrayList<Project> facProjects = user.getProjects();
		if (user.getActiveProjects() >= 2){
			for (Project p : facProjects) {
				if (p.getStatus().equals(ProjectStatus.Available)) {
					p.setStatus(ProjectStatus.Unavailable);
				}
			}
		}
	}
	
	/**
	 *  This method is used to set projects as available.
	 * @param user The Supervisor/Coordinator whose projects are going to be available.
	 */
	protected void setAvailable(Faculty user) {
		ArrayList<Project> facProjects = user.getProjects();
		if (user.getActiveProjects() <= 1){
			for (Project p : facProjects) {
				if (p.getStatus().equals(ProjectStatus.Unavailable)) {
					p.setStatus(ProjectStatus.Available);
				}
			}
		}
	}

}
