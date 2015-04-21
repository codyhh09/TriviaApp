package edu.ycp.cs482.Trivia.controller;

import edu.ycp.cs482.Trivia.pesist.Database;
import edu.ycp.cs482.Trivia.pesist.IDatabase;

public class ChangeStatus {
	public void changestatus(int id){
		IDatabase db = Database.getInstance();
		db.changeStatus(id);
	}
}
