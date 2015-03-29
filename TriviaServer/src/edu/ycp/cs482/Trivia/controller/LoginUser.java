package edu.ycp.cs482.Trivia.controller;

import edu.ycp.cs482.Trivia.pesist.Database;
import edu.ycp.cs482.Trivia.pesist.IDatabase;

public class LoginUser {
	public Boolean login(String User, String Pass){
		IDatabase db = Database.getInstance();
		return db.login(User, Pass);
	}
}
