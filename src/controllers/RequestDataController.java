package controllers;

import entities.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RequestDataController {
	private final RequestService requestService;
	private final StudentService studentService;
	private final FacultyService facultyService;
	private final ProjectService projectService;
	private static RequestDataController rdc = null;
    private RequestDataController() {
    	this.facultyService = FacultyService.getInstance();
		this.studentService = StudentService.getInstance();
		this.projectService = ProjectService.getInstance();
		this.requestService = RequestService.getInstance();
		readRequests();
    }
    public static RequestDataController getInstance() {
        if (rdc == null) {
            rdc = new RequestDataController();
        }
        return rdc;
    }

    private int last_index = 0;
	
	private static String requestPath = System.getProperty("user.dir") + "//data//requestList.csv";

	private static final String delimiter = ";";
	private void readRequests() {
		File file = new File(requestPath);
		ArrayList<Request> requests = new ArrayList<Request>();
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			String[] fields;
			while ((line = br.readLine()) != null) {
				
				fields = line.split(delimiter);
				String ID = fields[0];
				if (ID.equals("ID") || ID.equals("sep=")) {
					continue;
				}
				if (Integer.parseInt(ID) > last_index) {
					last_index = Integer.parseInt(ID);
				}
	
				String requestor = fields[1];
				String requestee = fields[2];
				RequestStatus status = RequestStatus.valueOf(fields[3]);
				RequestType requestType = RequestType.valueOf(fields[4]);
				String changes = fields[5];
				String id = fields[6];
				Integer projectID = Integer.parseInt(id);
				Project p = projectService.getProjectbyID(projectID);
				Coordinator coordinator;
				Student student;
				Faculty supervisor;
				switch (requestType) {
				case Title:
					supervisor = facultyService.getFacultybyID(requestee);
					student = studentService.getStudentbyID(requestor);
					requestService.createTitleRequest(ID, student, supervisor, status, p, changes);
					break;
				case Allocation:
					coordinator = (Coordinator) facultyService.getFacultybyID(requestee);
					student = studentService.getStudentbyID(requestor);
					requestService.createAllocationRequest(ID, student, coordinator, status, p);
					break;
				case Deregister:
					coordinator = (Coordinator) facultyService.getFacultybyID(requestee);
					student = studentService.getStudentbyID(requestor);
					requestService.createDeregistrationRequest(ID, student, coordinator, status, p);
					break;
				case Transfer:
					String replacement = fields[7];
					supervisor = facultyService.getFacultybyID(requestor);
					coordinator = (Coordinator) facultyService.getFacultybyID(requestee);
					Faculty subs = facultyService.getFacultybyID(replacement);
					requestService.createTransferRequest(ID, supervisor, coordinator, status, p, subs);
					break;
				default:
					System.out.println("Erroneous request type found.");
					break;
				}
			}
			br.close();
		}
		catch (FileNotFoundException fe) {
			fe.printStackTrace();
			System.exit(0);
		}
		catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	protected void updateRequests(ArrayList<Request> requests) {
	    try {
	    	String tempFilePath = requestPath + ".tmp";
	    	File tempFile = new File(tempFilePath);
	        FileWriter writer = new FileWriter(tempFilePath);
	        writer.append("sep=;");
	        writer.append("\n");
	        writer.append("ID");
	        writer.append(delimiter);
	        writer.append("From");
	        writer.append(delimiter);
	        writer.append("To");
	        writer.append(delimiter);
	        writer.append("Status");
	        writer.append(delimiter);
	        writer.append("RequestType");
	        writer.append(delimiter);
	        writer.append("Changes");
	        writer.append(delimiter);
	        writer.append("ProjectID");
	        writer.append("\n");
	        for (Request r : requests) {
	            writer.append(r.getRequestID());
	            writer.append(delimiter);
	            writer.append(r.getRequestor().getUserID());
	            writer.append(delimiter);
	            writer.append(r.getRequestee().getUserID());
	            writer.append(delimiter);
	            writer.append(String.valueOf(r.getStatus()));
	            writer.append(delimiter);
	            writer.append(String.valueOf(r.getType()));
	            writer.append(delimiter);
	            writer.append(String.valueOf(r.getChanges()));
	            writer.append(delimiter);
	            writer.append(String.valueOf(r.getProject().getID()));
	            writer.append("\n");
	        }
	        writer.flush();
	        writer.close();
	        File origFile = new File(requestPath);
	        origFile.delete();
	        tempFile.renameTo(origFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	public int getNewID() {
		int id = last_index + 1;
		last_index = id;
		return id;
	}
	
}
