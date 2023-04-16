package entities;

import java.util.ArrayList;
import java.util.UUID;

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

    public int getRequestID(){
        return requestID;
    }
    public User getRequestor(){
        return requestor;
    }
    public User getRequestee(){
        return requestee;
    }
    public abstract String getChanges();
    public RequestStatus getStatus(){
        return status;
    }
    public Project getProject(){
        return project;
    }
    public void setRequestID(int requestID){
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

	public abstract RequestType getType();

}
