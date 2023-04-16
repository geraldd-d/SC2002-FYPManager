package entities;

import java.util.ArrayList;
import java.util.UUID;

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
     * @return int
     */
    public int getRequestID(){
        return requestID;
    }
    
    /** 
     * @return User
     */
    public User getRequestor(){
        return requestor;
    }
    
    /** 
     * @return User
     */
    public User getRequestee(){
        return requestee;
    }
    public abstract String getChanges();
    
    /** 
     * @return RequestStatus
     */
    public RequestStatus getStatus(){
        return status;
    }
    
    /** 
     * @return Project
     */
    public Project getProject(){
        return project;
    }
    
    /** 
     * @param requestID
     */
    public void setRequestID(int requestID){
        this.requestID = requestID;
    }
    
    /** 
     * @param requestor
     */
    public void setRequestor(Student requestor){
        this.requestor = requestor;
    }
    
    /** 
     * @param requestee
     */
    public void setRequestee(User requestee){
        this.requestee = requestee;
    }
    
    /** 
     * @param status
     */
    public void setStatus(RequestStatus status){
        this.status = status;
    }
    
    /** 
     * @param project
     */
    public void setProject(Project project){
        this.project= project;
    }
    public abstract void printRequest();

	public abstract RequestType getType();

}
