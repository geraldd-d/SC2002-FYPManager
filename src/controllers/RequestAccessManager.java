package controllers;

public class RequestAccessManager {
	private static RequestAccessManager ram = null;
	private RequestAccessManager() {};
	public static RequestAccessManager getInstance() {
		if (ram == null) {
			ram = new RequestAccessManager();
		}
		return ram;
	}
	
}
