package controllers;

import entities.*;
import java.util.*;

public class RequestController {
	private static RequestController rc = null;
    private static ArrayList<Request> requestList = new ArrayList<>();

    public RequestController() {}

    public static RequestController getInstance() {
        if (rc == null) {
            rc = new RequestController();
        }
        return rc;
    }

    public static boolean addRequest(Request request) {
        return requestList.add(request);
    }

    public Request getLastRequest(ArrayList<Request> history) {
        if(history.size() > 0) {
            return history.get(history.size()-1);
        } else {
            return null;
        }
    }

    public void addToRequestHistory(RequestController requestController, ArrayList<Request> history) {
        Request request = requestController.getLastRequest(history);
        if (request == null) {
            //System.out.println("No requests found.");
            //history.add(request);
            return;
        }
        history.add(request);
    }
    

    public boolean requestProject(Student student, Project project) {
        if (student.getisAllocated()) {
			System.out.println("You are already registered for a project.");
            return false;
        }
        RequestController request = new RequestController();
        request.addToRequestHistory(request, student.getHistory());
		// add function where this is added in faculty pending requests for approval/rejection
		return true ;
    }

    public boolean requestNewTitle(Student student, Project project, String title) {
        if (!student.getisAllocated()) {
			System.out.println("You are not registered for any project.");
            return false;
        }
        RequestController request = new RequestController();
        request.addToRequestHistory(request, student.getHistory());
		// add function where this is added in faculty pending requests for approval/rejection
		return true ;
    }

    public boolean deregisterProject(Student student, Project project) {
        if (!student.getisAllocated()) {
			System.out.println("You are not registered for any project.");
            return false;
        }
        RequestController request = new RequestController();
        request.addToRequestHistory(request, student.getHistory());
		// add function where this is added in faculty pending requests for approval/rejection
		return true ;
    }


    
}
