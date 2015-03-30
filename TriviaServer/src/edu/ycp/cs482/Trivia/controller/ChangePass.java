package edu.ycp.cs482.Trivia.controller;

import edu.ycp.cs482.Model.User;
import edu.ycp.cs482.Trivia.pesist.Database;
import edu.ycp.cs482.Trivia.pesist.IDatabase;

public class ChangePass {
	public User changePass(String oldUser, String newPass){
		IDatabase db = Database.getInstance();
		return db.chgPass(oldUser, newPass);
	}
}
