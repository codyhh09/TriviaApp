package edu.ycp.cs482.Trivia.controller;

import edu.ycp.cs482.Trivia.pesist.Database;
import edu.ycp.cs482.Trivia.pesist.IDatabase;

public class changeFinalAnswer {
	public void changefinalanswer(int id, String answerfinal){
		IDatabase db = Database.getInstance();
		db.changeQuestionpt5(id, answerfinal);
	}
}
