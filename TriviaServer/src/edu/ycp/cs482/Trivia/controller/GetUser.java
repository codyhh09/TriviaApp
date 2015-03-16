package edu.ycp.cs482.Trivia.controller;

import edu.ycp.cs482.Trivia.pesist.Database;
import edu.ycp.cs482.Trivia.pesist.IDatabase;
import edu.ycp.cs482.Model.User;

public class GetUser {
	public User getUser(String username){
		IDatabase db = Database.getInstance();
		return db.getUser(username);
	}
}
