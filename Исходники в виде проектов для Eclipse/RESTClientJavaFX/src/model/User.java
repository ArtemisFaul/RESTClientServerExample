package model;

public class User {
	public User(long id, String user) {
		super();
		this.id = id;
		User = user;
	}

	private long id;

	private String User;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUser() {
		return User;
	}

	public void setUser(String user) {
		User = user;
	}

	@Override
	public String toString() {
		return User;
	}

}
