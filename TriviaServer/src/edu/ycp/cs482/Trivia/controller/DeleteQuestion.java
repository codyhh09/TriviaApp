package edu.ycp.cs482.Trivia.controller;

import edu.ycp.cs482.Trivia.pesist.Database;
import edu.ycp.cs482.Trivia.pesist.IDatabase;

public class DeleteQuestion {
	public void deleteQuestion(int id){
		IDatabase db = Database.getInstance();
		db.deleteQuestion(id);
	}
}
