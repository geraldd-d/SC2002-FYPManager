package entities;

import java.util.ArrayList;
import java.util.UUID;

public abstract class Request {
    private String requestID;
    private User requestor;
    private User requestee;
    private RequestStatus status;
    private Project project;
    public Request(String id, User requestor, User requestee, RequestStatus status, Project project){
        this.requestID = id;
        this.requestor = requestor;
        this.requestee = requestee;
        this.status = status;
        this.project = project;
    }

    public String getRequestID(){
        return requestID;
    }
    public User getRequestor(){
        return requestor;
    }
    public User getRequestee(){
        return requestee;
    }
 
    public RequestStatus getStatus(){
        return status;
    }
    public abstract RequestType getType();
    public abstract String getChanges();
    public Project getProject(){
        return project;
    }
    public void setRequestID(String requestID){
        this.requestID = requestID;
    }
    public void setRequestor(Student requestor){
        this.requestor = requestor;
    }
    public void setRequestee(User requestee){
        this.requestee = requestee;
    }
    public void setStatus(RequestStatus status){
        this.status = status;
    }
    public void setProject(Project project){
        this.project= project;
    }
    public abstract void printRequest();
}
