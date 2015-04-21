package edu.ycp.cs482.Trivia.controller;

import edu.ycp.cs482.Trivia.pesist.Database;
import edu.ycp.cs482.Trivia.pesist.IDatabase;

public class changeAnswer3 {
	public void changeanswer3(int id, String answer3){
		IDatabase db = Database.getInstance();
		db.changeQuestionpt3(id, answer3);
	}
}
