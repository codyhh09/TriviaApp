package edu.ycp.cs482.Trivia.controller;

import edu.ycp.cs482.Trivia.pesist.Database;
import edu.ycp.cs482.Trivia.pesist.IDatabase;

public class changeAnswer4 {
	public void changeanswer4(int id, String answer4){
		IDatabase db = Database.getInstance();
		db.changeQuestionpt4(id, answer4);
	}
}
