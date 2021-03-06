package edu.ycp.cs482.Model;

import java.io.Serializable;


public class User implements Serializable {
	private int id;
	private String Username;
	private String Password;
	private int streak;
	private int retry;
	private int coins;

	public User() {

	}

	public User(String Username, String Password) {
		this.Username = Username;
		this.Password = Password;
		this.streak = 0;
		this.retry = 0;
		this.coins = 0;
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
		this.Username = username;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		this.Password = password;
	}
	
	public int getStreak() {
		return streak;
	}

	public void setStreak(int streak) {
		this.streak = streak;
	}
	
	public int getRetry() {
		return retry;
	}
	
	public void setRetry(int retry) {
		this.retry = retry;
	}
	
	public int getCoins() {
		return coins;
	}
	
	public void setCoins(int coins) {
		this.coins = coins;
	}
}
