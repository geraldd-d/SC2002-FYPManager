package entities;


public class Coordinator extends Faculty{
	private String password;
	public Coordinator(String userID, String name, String email){
		super(userID, "password", name, email);
		this.password = "password";
	}
	public Coordinator(String userID,String password, String name, String email){
		super(userID, password, name, email);
	}
	public String getPassword(){
		return this.password;
	}
}
