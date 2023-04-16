package controllers;

import entities.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RequestController {
	private static RequestController rc = null;
	private ArrayList<Request> requestList;
    private RequestController() {
    	ArrayList<Request> requests = readRequests();
    	this.requestList = requests;
    	RequestManager rm = RequestManager.getInstance(requests);
    }
    
	/** 
	 * @return RequestController
	 */
	public static RequestController getInstance() {
        if (rc == null) {
            rc = new RequestController();
        }
        return rc;
    }
 
 /** 
  * @param readRequests(
  * @return ArrayList<Request>
  */
    
	private static String requestPath = System.getProperty("user.dir") + "//data//requestList.csv";
    private int last_index = 0;
	private static final String delimiter = ";";
	private ArrayList<Request> readRequests() {
		FacultyController fc = FacultyController.getInstance();
		StudentController sc = StudentController.getInstance();
		ProjectManager pm = ProjectManager.getInstance();
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
				Integer requestID = Integer.parseInt(ID);
				if (requestID > last_index) {
					last_index = requestID;
				}
				
				String requestor = fields[1];
				String requestee = fields[2];
				RequestStatus status = RequestStatus.valueOf(fields[3]);
				RequestType requestType = RequestType.valueOf(fields[4]);
				String projectID = fields[6];
				Coordinator coordinator;
				Student student;
				Faculty supervisor;
				switch (requestType) {
				case Title:
					supervisor = fc.getFacultybyID(requestee);
					student = sc.getStudentbyID(requestor);
					TitleRequest tr = new TitleRequest(requestID, student, supervisor, status, student.getRegisteredProject(), projectID);
					requests.add(tr);
					supervisor.addInbox(tr);
					student.addHistory(tr);
					break;
				case Allocation:
					coordinator = (Coordinator )fc.getFacultybyID(requestee);
					student = sc.getStudentbyID(requestor);
					AllocRequest ar = new AllocRequest(requestID, student, coordinator, status, pm.getProjectByID(Integer.parseInt(projectID)));
					requests.add(ar);
					coordinator.addInbox(ar);
					student.addHistory(ar);
					break;
				case Deregister:
					coordinator = (Coordinator)fc.getFacultybyID(requestee);
					student = sc.getStudentbyID(requestor);
					DeregRequest dr = new DeregRequest(requestID, student, coordinator, status, student.getRegisteredProject());
					requests.add(dr);
					coordinator.addInbox(dr);
					student.addHistory(dr);
					break;
				case Transfer:
					String replacement = fields[5];
					supervisor = fc.getFacultybyID(requestor);
					coordinator = (Coordinator) fc.getFacultybyID(requestee);
					TransferRequest r = new TransferRequest(requestID, supervisor, coordinator, status,pm.getProjectByID(Integer.parseInt(projectID)), fc.getFacultybyID(replacement));
					requests.add(r);
					coordinator.addInbox(r);
					supervisor.addHistory(r);
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
		return requests;
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
	            writer.append(String.valueOf(r.getRequestID()));
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
	
	/** 
	 * @return int
	 */
	
	public int getNewID() {
		int id = last_index + 1;
		last_index = id;
		return id;
	}
	
}
