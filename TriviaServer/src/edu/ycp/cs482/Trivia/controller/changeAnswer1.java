package edu.ycp.cs482.Trivia.controller;

import edu.ycp.cs482.Trivia.pesist.Database;
import edu.ycp.cs482.Trivia.pesist.IDatabase;

public class changeAnswer1 {
	public void changeanswer1(int id, String answer1){
		IDatabase db = Database.getInstance();
		db.changeQuestionpt1(id, answer1);
	}
}
