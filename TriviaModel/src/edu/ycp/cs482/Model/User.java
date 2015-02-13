package edu.ycp.cs482.Model;

public class User {
	private int id;
	private String Username;
	private String Password;

	public User() {

	}

	public User(int _id, String _Username, String _Password) {
		this.id = _id;
		this.Username = _Username;
		this.Password = _Password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}
}
