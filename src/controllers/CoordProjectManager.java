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
	 * @param projects
	 * @return CoordProjectManager
	 */
	public static CoordProjectManager getInstance(ArrayList<Project> projects) {
		if (cpm == null) {
			cpm = new CoordProjectManager(projects);
		}
		return cpm;
	}
	
	/** 
	 * @return CoordProjectManager
	 */
	public static CoordProjectManager getInstance() {
		return cpm;
	}
	
	/** 
	 * @param student
	 * @param p
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
	 * @param student
	 * @param p
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
	 * @param current
	 * @param replacementID
	 * @param replacementName
	 * @param p
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
	 * @param user
	 * @param title
	 */
	@Override
	public void addProject(Faculty user, String title) {
		FacultyController fc = FacultyController.getInstance();
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
	 * @param user
	 */
	@Override
	public void viewOwnProjects(Faculty user) {
		ArrayList<Project>projects = user.getProjects();
		projects.forEach((p)-> p.printProject());
	}

	
	/** 
	 * @param user
	 */
	@Override
	public void viewActiveProjects(Faculty user) {
		ArrayList<Project>projects = user.getProjects();
		projects.forEach((p)-> p.printActiveProject());
	}

	
	/** 
	 * @param p
	 * @param t
	 */
	@Override
	public void changeTitle(Project p, String t) {
		p.setTitle(t);
	}

	
	/** 
	 * @param projectID
	 * @return Project
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

	@Override
	public void saveChanges() {
		ProjectsController pc = ProjectsController.getInstance();
	    pc.updateProjects(projects);
	}
	
	/** 
	 * @return ArrayList<Project>
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
	 * @return ArrayList<Project>
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
	 * @return ArrayList<Project>
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
	 * @return ArrayList<Project>
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
	 * @param user
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
	 * @param user
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
