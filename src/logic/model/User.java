package logic.model;

public class User {
	
	private String username;
	private String password;
	private String firstName;
	private String secondName;
	private String email;
	private String photo;
	
	public User() {
		this.username = "";
		this.password = "";
		this.firstName = "";
		this.secondName = "";
		this.email = "";
		this.photo = "";
	}
	
	public User(String username) {
		this.username = username;
	}

	public User(String username, String photo, String email) {
		this.username = username;
		this.photo = photo;
		this.email = email;
	}
	
	public User(String firstName, String secondName, String username, String email, String password) {
		this.firstName = firstName;
		this.secondName = secondName;
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}	
	
}
