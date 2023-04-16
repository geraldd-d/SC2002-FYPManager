package controllers;

import java.util.ArrayList;
import java.util.List;

import entities.*;

/**
 * This class is the manager class for the requests.
 */
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

	/**
	 * This method is used to get the instance of the RequestManager. It is a singleton class.
	 * @param requestList The list of requests in the system.
	 * @return The instance of the RequestManager class.
	 */
	public static RequestManager getInstance(ArrayList<Request> requestList) {
		if (rm == null) {
			rm = new RequestManager(requestList);
		}
		return rm;
	}
	/**
	 * This method is used to get the instance of the RequestManager. It is a singleton class.
	 * @return The instance of the RequestManager class.
	 */
	public static RequestManager getInstance() {
		return rm;
	}

	/**
	 * This method is used to view the history of the user
	 * @param user The user whose history is to be viewed.
	 * @param page The page number of the history to be viewed.
	 */
	public void viewHistory(User user, int page) {
		int pageSize = 5;
	    ArrayList<Request> reqs = user.getHistory();
	    int startIndex = (page - 1) * pageSize;
	    int endIndex = Math.min(startIndex + pageSize, reqs.size());
	    List<Request> currentPage = reqs.subList(startIndex, endIndex);
	    currentPage.forEach((Request)-> Request.printRequest());
	}
}
