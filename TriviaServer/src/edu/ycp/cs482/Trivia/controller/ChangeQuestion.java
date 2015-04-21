package edu.ycp.cs482.Trivia.controller;

import edu.ycp.cs482.Model.Question;
import edu.ycp.cs482.Trivia.pesist.Database;
import edu.ycp.cs482.Trivia.pesist.IDatabase;

public class ChangeQuestion {
	public void changeQuestion(int id, String question){
		IDatabase db = Database.getInstance();
		db.changeQuestion(id, question);
	}
}
