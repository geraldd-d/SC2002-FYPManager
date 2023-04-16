package controllers;

import java.util.ArrayList;
import java.util.List;

import entities.*;

public class RequestManager {
	private static RequestManager rm = null;
	private ArrayList<Request> requestList;
	private RequestManager(ArrayList<Request> requestList) {
		this.requestList = requestList;
		StudentController sc = StudentController.getInstance();
		FacultyController fc = FacultyController.getInstance();
		CoordRequestManager cpm = CoordRequestManager.getInstance(requestList);
		FacultyRequestManager fpm = FacultyRequestManager.getInstance(requestList);
		StudentRequestManager spm = StudentRequestManager.getInstance(requestList);
	}

	public static RequestManager getInstance(ArrayList<Request> requestList) {
		if (rm == null) {
			rm = new RequestManager(requestList);
		}
		return rm;
	}
	public static RequestManager getInstance() {
		return rm;
	}

	public void viewHistory(User user, int page) {
		int pageSize = 5;
	    ArrayList<Request> reqs = user.getHistory();
	    int startIndex = (page - 1) * pageSize;
	    int endIndex = Math.min(startIndex + pageSize, reqs.size());
	    List<Request> currentPage = reqs.subList(startIndex, endIndex);
	    currentPage.forEach((Request)-> Request.printRequest());
	}
}
