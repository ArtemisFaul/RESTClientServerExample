package model;

public class RequestUsers {
	public RequestUsers(String name, User[] user) {
		super();
		this.name = name;
		this.user = user;
	}

	private String name;
	private User[] user;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User[] getUser() {
		return user;
	}

	public void setUser(User[] user) {
		this.user = user;
	}

}
