package edu.ycp.cs482.Trivia.controller;

import edu.ycp.cs482.Model.Question;
import edu.ycp.cs482.Trivia.pesist.Database;
import edu.ycp.cs482.Trivia.pesist.IDatabase;

public class AddQuestion {
	public void addQuestion(Question question){
		IDatabase db = Database.getInstance();
		db.addQuestion(question);
	}
}
