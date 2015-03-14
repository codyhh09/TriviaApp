package edu.ycp.cs482.Trivia.controller;

import edu.ycp.cs482.Model.*;
import edu.ycp.cs482.Trivia.pesist.Database;
import edu.ycp.cs482.Trivia.pesist.IDatabase;

public class AddUser {
	public void addUser(User user){
		IDatabase db = Database.getInstance();
		db.addUser(user);
	}
}
