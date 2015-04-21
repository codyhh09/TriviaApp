package edu.ycp.cs482.Trivia.controller;

import edu.ycp.cs482.Trivia.pesist.Database;
import edu.ycp.cs482.Trivia.pesist.IDatabase;

public class changeAnswer2 {
	public void changeanswer2(int id, String answer2){
		IDatabase db = Database.getInstance();
		db.changeQuestionpt2(id, answer2);
	}
}
