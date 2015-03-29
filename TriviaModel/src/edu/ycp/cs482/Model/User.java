package edu.ycp.cs482.Model;

import java.io.Serializable;


public class User implements Serializable {
	private int id;
	private String Username;
	private String Password;

	public User() {

	}

	public User(String _Username, String _Password) {
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
