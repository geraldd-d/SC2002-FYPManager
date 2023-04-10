package entities;

import java.util.ArrayList;
import java.util.UUID;

public class Request {
    private String requestID;
    private Student requestor;
    private User requestee;
    private String description;
    private RequestStatus status;
    private RequestType requestType;
    private ArrayList<Request> history;
    private Project project;
    public Request(Student requestor, User requestee, RequestType requestType){
        this.requestID = UUID.randomUUID().toString();
        this.requestor = requestor;
        this.requestee = requestee;
        this.requestType = requestType;
        this.status = RequestStatus.Pending;
    }

    public String getRequestID(){
        return requestID;
    }
    public Student getRequestor(){
        return requestor;
    }
    public User getRequestee(){
        return requestee;
    }
    public String getDescription(){
        return description;
    }
    public RequestStatus getStatus(){
        return status;
    }
    public RequestType getRequestType(){
        return requestType;
    }
    public  Project getProject(){
        return project;
    }
    public ArrayList<Request> getHistory(){
        return history;
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
    public void setDescription(String description){
        this.description = description;
    }
    public void setStatus(RequestStatus status){
        this.status = status;
    }
    public void setRequestType(RequestType requestType){
        this.requestType = requestType;
    }
    public void setProject(Project project){
        this.project= project;
    }
    public void setHistory(ArrayList<Request> history){
        this.history = history;
    }

}

enum RequestStatus{
    Pending, 
    Approved, 
    Rejected
}
