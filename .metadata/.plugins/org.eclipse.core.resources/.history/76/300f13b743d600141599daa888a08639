package edu.ycp.cs482.Trivia.controller;

import edu.ycp.cs482.Model.User;
import edu.ycp.cs482.Trivia.pesist.Database;
import edu.ycp.cs482.Trivia.pesist.IDatabase;

public class ChangeUser {
	public User changeUser(String oldUser, String newUser){
		IDatabase db = Database.getInstance();
		return db.chgUser(oldUser, newUser);
	}
}
