package edu.ycp.cs482.Trivia.controller;

import edu.ycp.cs482.Trivia.pesist.Database;
import edu.ycp.cs482.Trivia.pesist.IDatabase;

public class DeleteUser {
	public void deleteUser(String user){
		IDatabase db = Database.getInstance();
		db.deleteUser(user);
	}
}
