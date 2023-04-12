package controllers;

import entities.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class RequestController {
	private static RequestController rc = null;
	private ArrayList<Request> requestList;
    private RequestController() {
    	ArrayList<Request> requests = readRequests();
    	this.requestList = requests;
    }
    public static RequestController getInstance() {
        if (rc == null) {
            rc = new RequestController();
        }
        return rc;
    }
    
	private static String requestPath = System.getProperty("user.dir") + "//data//requestList.csv";

	private static final String delimiter = ";";
	private ArrayList<Request> readRequests() {
		FacultyController fc = FacultyController.getInstance();
		StudentController sc = StudentController.getInstance();
		ProjectsController pc = ProjectsController.getInstance();
		File file = new File(requestPath);
		ArrayList<Request> requests = new ArrayList<Request>();
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			int last_index = 0;
			String[] fields;
			while ((line = br.readLine()) != null) {
				
				fields = line.split(delimiter);
				String ID = fields[0];
				Integer requestID = Integer.parseInt(ID);
				if (requestID > last_index) {
					last_index = requestID;
				}
				if (ID.equals("ID") || ID.equals("sep=")) {
					continue;
				}
				String requestor = fields[1];
				String requestee = fields[2];
				RequestStatus status = RequestStatus.valueOf(fields[3]);
				RequestType requestType = RequestType.valueOf(fields[4]);
				RequestClass requestClass = RequestClass.valueOf(fields[5]);
				String changes = fields[6];
				Coordinator coordinator;
				Student student;
				Faculty supervisor;
				switch (requestClass) {
				case Supervisor:
					supervisor = fc.getFacultybyID(requestee);
					student = sc.getStudentbyID(requestor);
					if (requestType == RequestType.Title) {
						TitleRequest r = new TitleRequest(student, supervisor, status, student.getRegisteredProject(), changes);
						requests.add(r);
						supervisor.addInbox(r);
						student.addHistory(r);
					}
					
					break;

				case Coordinator:
					coordinator = (Coordinator )fc.getFacultybyID(requestee);
					student = sc.getStudentbyID(requestor);
					if (requestType == RequestType.Allocation) {
						AllocRequest r = new AllocRequest(student, coordinator, status, pc.getProjectByID(Integer.parseInt(changes)));
						requests.add(r);
						coordinator.addInbox(r);
						student.addHistory(r);
					}
					if (requestType == RequestType.Deregister) {
						DeregRequest r = new DeregRequest(student, coordinator, status, student.getRegisteredProject());
						requests.add(r);
						coordinator.addInbox(r);
						student.addHistory(r);
					}
					break;
				case SVCoord:
					String replacement = fields[7];
					supervisor = fc.getFacultybyID(requestor);
					coordinator = (Coordinator) fc.getFacultybyID(requestee);
					if (requestType == RequestType.Transfer) {
						TransferRequest r = new TransferRequest(supervisor, coordinator, status,pc.getProjectByID(Integer.parseInt(changes)), fc.getFacultybyID(replacement));
						requests.add(r);
						coordinator.addInbox(r);
						supervisor.addHistory(r);
					}
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
}
