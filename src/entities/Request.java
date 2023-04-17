package entities;

/**
 * This abstract class is the parent class of all requests. It contains the attributes and methods that are common to all requests.
 */
public abstract class Request {
    private int requestID;
    private User requestor;
    private User requestee;
    private RequestStatus status;
    private Project project;
    public Request(int ID, User requestor, User requestee, RequestStatus status, Project project){
        this.requestID = ID;
        this.requestor = requestor;
        this.requestee = requestee;
        this.status = status;
        this.project = project;
    }

    
    /** 
     * This method return requestID
     * @return int
     */
    public int getRequestID(){
        return requestID;
    }
    
    /** 
     * This method returns requestor or the user who is making a request.
     * @return User
     */
    public User getRequestor(){
        return requestor;
    }
    
    /** 
     * This returns the requestee or who the request has been made to.
     * @return User
     */
    public User getRequestee(){
        return requestee;
    }
    /**
     * This method gets the changes. 
     */
    public abstract String getChanges();
    
    /** 
     * This method return the status of the request.
     * @return RequestStatus
     */
    public RequestStatus getStatus(){
        return status;
    }
    
    /**
     * This methods returns the project
     * @return Project
     */
    public Project getProject(){
        return project;
    }
    
    /** 
     * This method return the requestID
     * @param requestID
     */
    public void setRequestID(int requestID){
        this.requestID = requestID;
    }
    
    /** 
     * This method sets requestor as the current requestor.
     * @param requestor
     */
    public void setRequestor(Student requestor){
        this.requestor = requestor;
    }
    
    /** 
     * This method sets requestee as the current requestee.
     * @param requestee
     */
    public void setRequestee(User requestee){
        this.requestee = requestee;
    }
    
    /** 
     * This method sets status as the current status.
     * @param status
     */
    public void setStatus(RequestStatus status){
        this.status = status;
    }
    
    /** 
     * This method sets project as the current project.
     * @param project
     */
    public void setProject(Project project){
        this.project= project;
    }
    /**
     * This method prints the request with its details
     */
    public abstract void printRequest();

    /**
     * This method calls the request type of the request.
     */
	public abstract RequestType getType();

}
