package edu.ycp.cs482.Trivia.controller;

import java.util.List;

import edu.ycp.cs482.Model.User;
import edu.ycp.cs482.Trivia.pesist.Database;
import edu.ycp.cs482.Trivia.pesist.IDatabase;

public class GetAllUsers {
	public List<User> getallUser(){
		IDatabase db = Database.getInstance();
		return db.getAllUser();
	}
}
