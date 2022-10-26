package pojo;

public class User {
	// all user attribute
	private String username;
	private String emailID;
	private String password;
	private String userType;

	// getter setter
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	// constructor
	public User(String username, String emailID, String password, String userType) {
		super();
		this.username = username;
		this.emailID = emailID;
		this.password = password;
		this.userType = userType;

	}

}
